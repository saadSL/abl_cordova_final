package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_transaction;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.LayoutSetupTransactionBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AccountInformationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.setup_transaction.SetupTransactionPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.setup_transaction.SetupTransactionResponse;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodePostParams;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponse;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponseData;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.upload_document.UploadDocumentActivity;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;

public class SelectCardActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, SelectCardInterface, View.OnClickListener {
    LayoutSetupTransactionBinding layoutSetupTransactionBinding;

    private SetupTransactionPostParams setupTransactionPostParams;
    private SelectCardViewModel selectCardViewModel;

    private RegisterVerifyOtpResponse registerVerifyOtpResponse;
    private GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse;
    private LookUpCodePostParams atmCardsPostParams;
    private ArrayList<LookUpCodeResponseData> atmCardsList;

    private Boolean atmCardInd = false;
    private int atmTypeId = 0;
    private int transactionAlertInd = 1;
    private int chequeBookReqInd = 1;
    private int transactionAlertId = 0;
    private Boolean IS_RESUMED;

    private SelectCardAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setLayout();
        setViewModel();
        setClicks();
        getSharedPrefData();
        checkLayouts();
        observe();
        getAtmCards();

        setLogoLayout(layoutSetupTransactionBinding.logoToolbar.tvDate);

    }

    private void setAtmCardAdapter() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_atm_card);
        adapter = new SelectCardAdapter(atmCardsList, SelectCardActivity.this, SelectCardActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(SelectCardActivity.this, LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setAdapter(adapter);
    }

    private void getAtmCards() {
        setAtmCardsPostParams();
        selectCardViewModel.getAtmCards(atmCardsPostParams);
        showLoading();
    }

    private void setAtmCardsPostParams() {
        atmCardsPostParams.getData().setCodeTypeId(Config.ATM_CARDS_ID);
    }

    private void getSharedPrefData() {

        //flow for new application
        if (getSerializableFromPref(Config.GET_CONSUMER_RESPONSE, GetConsumerAccountDetailsResponse.class) == null) {
            IS_RESUMED = false;
            registerVerifyOtpResponse = (RegisterVerifyOtpResponse) getSerializableFromPref(Config.REG_OTP_RESPONSE, RegisterVerifyOtpResponse.class);
        }
        //flow for drafted application
        else {
            IS_RESUMED = true;
            getConsumerAccountDetailsResponse = (GetConsumerAccountDetailsResponse) getSerializableFromPref(Config.GET_CONSUMER_RESPONSE, GetConsumerAccountDetailsResponse.class);
        }

    }

    private void setLayout() {
        layoutSetupTransactionBinding.steps.screenHeader.stepsHeading1.setText("Setup");
        layoutSetupTransactionBinding.steps.screenHeader.stepsHeading2.setText("Transaction");
        layoutSetupTransactionBinding.steps.step1.setBackground(this.getDrawable(R.color.custom_blue));
        layoutSetupTransactionBinding.steps.step2.setBackground(this.getDrawable(R.color.custom_blue));
        layoutSetupTransactionBinding.steps.step3.setBackground(this.getDrawable(R.color.custom_blue));
    }

    private void observe() {
        selectCardViewModel.setupTransactionResponseMutableLiveData.observe(this, new Observer<SetupTransactionResponse>() {
            @Override
            public void onChanged(SetupTransactionResponse setupTransactionResponse) {
                openToNext();
                loader.dismiss();
            }
        });

        selectCardViewModel.setupTransactionErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                loader.dismiss();
            }
        });

        selectCardViewModel.atmCardsSuccessResponse.observe(this, new Observer<LookUpCodeResponse>() {
            @Override
            public void onChanged(LookUpCodeResponse atmCardsResponse) {
                dismissLoading();
                atmCardsList = atmCardsResponse.getData();
                setAtmCardAdapter();
            }
        });

        selectCardViewModel.atmCardsError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                dismissLoading();
            }
        });
    }

    private void openToNext() {
        openActivity(UploadDocumentActivity.class);
    }

    private void checkLayouts() {
//        if (layoutSetupTransactionBinding.debitCardSwitch.isChecked()) {
//            layoutSetupTransactionBinding.llClassicCard.setEnabled(true);
//            layoutSetupTransactionBinding.llVdcCard.setEnabled(true);
//        } else {
//            layoutSetupTransactionBinding.llClassicCard.setEnabled(false);
//            layoutSetupTransactionBinding.llVdcCard.setEnabled(false);
//        }

        if (layoutSetupTransactionBinding.transactionalAlertSwitch.isChecked()) {
            layoutSetupTransactionBinding.btnSms.setEnabled(true);
            layoutSetupTransactionBinding.btnEmail.setEnabled(true);
        } else {
            layoutSetupTransactionBinding.btnSms.setEnabled(false);
            layoutSetupTransactionBinding.btnEmail.setEnabled(false);
        }
    }

    private void setViewModel() {
        selectCardViewModel = new ViewModelProvider(this).get(SelectCardViewModel.class);
        setupTransactionPostParams = new SetupTransactionPostParams();
        atmCardsPostParams = new LookUpCodePostParams();
    }

    private void setClicks() {
        layoutSetupTransactionBinding.debitCardSwitch.setOnCheckedChangeListener(this);
        layoutSetupTransactionBinding.chequeBookSwitch.setOnCheckedChangeListener(this);
        layoutSetupTransactionBinding.transactionalAlertSwitch.setOnCheckedChangeListener(this);
        layoutSetupTransactionBinding.btnSms.setOnClickListener(this);
        layoutSetupTransactionBinding.btnEmail.setOnClickListener(this);
        layoutSetupTransactionBinding.btnContainer.btnNext.setOnClickListener(this);
        layoutSetupTransactionBinding.btnContainer.btBack.setOnClickListener(this);
//        layoutSetupTransactionBinding.llClassicCard.setOnClickListener(this);
//        layoutSetupTransactionBinding.llVdcCard.setOnClickListener(this);
    }

    private void setBinding() {
        layoutSetupTransactionBinding = LayoutSetupTransactionBinding.inflate(getLayoutInflater());
        setContentView(layoutSetupTransactionBinding.getRoot());
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.debit_card_switch:
                if (!isChecked) {
                    atmCardInd = false;

                } else {
                    atmCardInd = true;
                }
                break;
            case R.id.transactional_alert_switch:
                if (!isChecked) {
                    transactionAlertInd = 0;
                    layoutSetupTransactionBinding.btnSms.setEnabled(false);
                    layoutSetupTransactionBinding.btnEmail.setEnabled(false);
                } else {
                    transactionAlertInd = 1;
                    layoutSetupTransactionBinding.btnSms.setEnabled(true);
                    layoutSetupTransactionBinding.btnEmail.setEnabled(true);
                }
                break;
            case R.id.cheque_book_switch:
                if (!isChecked) {
                    chequeBookReqInd = 0;
                } else {
                    chequeBookReqInd = 1;
                }
                break;

        }
    }

    private void registerTransactionDetails() {
        if (!isValid()) {
            showAlert(Config.errorType, "Please select any one card !!!");
        } else {
            setTransactionDetailsPostParams();
            selectCardViewModel.registerTransactionDetails(setupTransactionPostParams, getStringFromPref(Config.ACCESS_TOKEN));
            showLoading();
        }
    }

    private boolean isValid() {
        if (!atmCardInd) {
            return false;
        } else if (atmTypeId == 0) {
            return false;
        }
        return true;
    }

    private void setTransactionDetailsPostParams() {
        if (IS_RESUMED) {
            //flow for drafted application
            AccountInformationResponse accountInformation = getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getAccountInformation();
            setupTransactionPostParams.getData().setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
            setupTransactionPostParams.getData().setRdaCustomerId(accountInformation.getRdaCustomerId());
        } else {
            //flow for new application
            AccountInformationResponse accountInformation = registerVerifyOtpResponse.getData().getConsumerList().get(0).getAccountInformation();
            setupTransactionPostParams.getData().setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
            setupTransactionPostParams.getData().setRdaCustomerId(accountInformation.getRdaCustomerId());
        }
        setupTransactionPostParams.getData().setCustomerTypeId(Config.CUSTOMER_TYPE_ID);
        setupTransactionPostParams.getData().setAtmTypeId(atmTypeId);
        setupTransactionPostParams.getData().setTransAlertInd(transactionAlertInd);
        setupTransactionPostParams.getData().setChequeBookReqInd(chequeBookReqInd);
        setupTransactionPostParams.getData().setTransactionalAlertId(transactionAlertId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sms:
                transactionAlertId = Config.TRANSACTION_ALERT_SMS;
                layoutSetupTransactionBinding.btnEmail.setBackground(this.getDrawable(R.drawable.rounded_corner_selected));
                layoutSetupTransactionBinding.btnSms.setBackground(this.getDrawable(R.drawable.rounded_button));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    layoutSetupTransactionBinding.btnSms.setTextColor(this.getColor(R.color.white));
                    layoutSetupTransactionBinding.btnEmail.setTextColor(this.getColor(R.color.custom_blue));
                }
                break;
            case R.id.btn_email:
                transactionAlertId = Config.TRANSACTION_ALERT_EMAIL;
                layoutSetupTransactionBinding.btnSms.setBackground(this.getDrawable(R.drawable.rounded_corner_selected));
                layoutSetupTransactionBinding.btnEmail.setBackground(this.getDrawable(R.drawable.rounded_button));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    layoutSetupTransactionBinding.btnEmail.setTextColor(this.getColor(R.color.white));
                    layoutSetupTransactionBinding.btnSms.setTextColor(this.getColor(R.color.custom_blue));
                }
                break;
            case R.id.btn_next:
                registerTransactionDetails();
                break;
            case R.id.bt_back:
                finish();
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setLayout();
    }

    @Override
    public void setSelectionAt(int position) {
        if (atmCardInd) {
            for (int i = 0; i < atmCardsList.size(); i++) {
                atmCardsList.get(i).setSelected(false);
            }
            atmCardsList.get(position).setSelected(true);
            atmTypeId = atmCardsList.get(position).getId();
        }
    }
}
