package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_transaction;

import static androidx.navigation.ViewKt.findNavController;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.LayoutSetupTransactionBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseFragment;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.atm_cards.AtmCardsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.atm_cards.AtmCardsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.atm_cards.AtmCardsResponseData;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponseAccountInformation;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AccountInformationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.setup_transaction.SetupTransactionPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.setup_transaction.SetupTransactionResponse;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;

public class SelectCardFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    LayoutSetupTransactionBinding layoutSetupTransactionBinding;

    private SetupTransactionPostParams setupTransactionPostParams;
    private SelectCardViewModel selectCardViewModel;

    private RegisterVerifyOtpResponse registerVerifyOtpResponse;
    private GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse;
    private AtmCardsPostParams atmCardsPostParams;
    private ArrayList<AtmCardsResponseData> atmCardsList;

    private int atmTypeId = 0;
    private int transactionAlertInd = 1;
    private int chequeBookReqInd = 1;
    private int transactionAlertId = 0;
    private Boolean IS_RESUMED;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutSetupTransactionBinding = LayoutSetupTransactionBinding.inflate(inflater, container, false);
        setViewModel();
        setClicks();
        getSharedPrefData();
        checkLayouts();
        observe();
        getAtmCards();
        setLogoLayout(layoutSetupTransactionBinding.logoToolbar.tvDate);

        return layoutSetupTransactionBinding.getRoot();
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
        layoutSetupTransactionBinding.steps.step1.setBackground(mContext.getDrawable(R.color.custom_blue));
        layoutSetupTransactionBinding.steps.step2.setBackground(mContext.getDrawable(R.color.custom_blue));
        layoutSetupTransactionBinding.steps.step3.setBackground(mContext.getDrawable(R.color.custom_blue));
    }

    private void observe() {
        selectCardViewModel.setupTransactionResponseMutableLiveData.observe(getViewLifecycleOwner(), new Observer<SetupTransactionResponse>() {
            @Override
            public void onChanged(SetupTransactionResponse setupTransactionResponse) {
                openUploadDocumentActivity(setupTransactionResponse);
                dismissLoading();
            }
        });

        selectCardViewModel.setupTransactionErrorLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                dismissLoading();
            }
        });

        selectCardViewModel.atmCardsSuccessResponse.observe(getViewLifecycleOwner(), new Observer<AtmCardsResponse>() {
            @Override
            public void onChanged(AtmCardsResponse atmCardsResponse) {
                atmCardsList = atmCardsResponse.getData();
                dismissLoading();
            }
        });

        selectCardViewModel.atmCardsError.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                dismissLoading();
            }
        });
    }

    private void openUploadDocumentActivity(SetupTransactionResponse setupTransactionResponse) {
        findNavController(layoutSetupTransactionBinding.getRoot()).navigate(R.id.openUploadDocument);
    }

    private void checkLayouts() {
        if (layoutSetupTransactionBinding.debitCardSwitch.isChecked()) {
            layoutSetupTransactionBinding.llClassicCard.setEnabled(true);
            layoutSetupTransactionBinding.llVdcCard.setEnabled(true);
        } else {
            layoutSetupTransactionBinding.llClassicCard.setEnabled(false);
            layoutSetupTransactionBinding.llVdcCard.setEnabled(false);
        }

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
        atmCardsPostParams = new AtmCardsPostParams();
    }

    private void setClicks() {
        layoutSetupTransactionBinding.debitCardSwitch.setOnCheckedChangeListener(this);
        layoutSetupTransactionBinding.chequeBookSwitch.setOnCheckedChangeListener(this);
        layoutSetupTransactionBinding.transactionalAlertSwitch.setOnCheckedChangeListener(this);
        layoutSetupTransactionBinding.btnSms.setOnClickListener(this);
        layoutSetupTransactionBinding.btnEmail.setOnClickListener(this);
        layoutSetupTransactionBinding.btnContainer.btnNext.setOnClickListener(this);
        layoutSetupTransactionBinding.btnContainer.btBack.setOnClickListener(this);
        layoutSetupTransactionBinding.llClassicCard.setOnClickListener(this);
        layoutSetupTransactionBinding.llVdcCard.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.debit_card_switch:
                if (!isChecked) {
                    layoutSetupTransactionBinding.llClassicCard.setEnabled(false);
                    layoutSetupTransactionBinding.llVdcCard.setEnabled(false);
                } else {
                    layoutSetupTransactionBinding.llClassicCard.setEnabled(true);
                    layoutSetupTransactionBinding.llVdcCard.setEnabled(true);
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
        setTransactionDetailsPostParams();
        selectCardViewModel.registerTransactionDetails(setupTransactionPostParams, getStringFromPref(Config.ACCESS_TOKEN));
        showLoading();
    }

    private void setTransactionDetailsPostParams() {
        if (IS_RESUMED) {
            //flow for drafted application
            GetConsumerAccountDetailsResponseAccountInformation accountInformation = getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getAccountInformation();
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
                layoutSetupTransactionBinding.btnEmail.setBackground(mContext.getDrawable(R.drawable.rounded_corner_selected));
                layoutSetupTransactionBinding.btnSms.setBackground(mContext.getDrawable(R.drawable.rounded_button));
                break;
            case R.id.btn_email:
                transactionAlertId = Config.TRANSACTION_ALERT_EMAIL;
                layoutSetupTransactionBinding.btnSms.setBackground(mContext.getDrawable(R.drawable.rounded_corner_selected));
                layoutSetupTransactionBinding.btnEmail.setBackground(mContext.getDrawable(R.drawable.rounded_button));
                break;
            case R.id.ll_classic_card:
                atmTypeId = Config.UPI;
                layoutSetupTransactionBinding.llClassicCard.setBackground(mContext.getDrawable(R.drawable.rounded_corner_selected));
                layoutSetupTransactionBinding.llVdcCard.setBackground(mContext.getDrawable(R.drawable.transparent_secondary_bg));
                break;
            case R.id.ll_vdc_card:
                atmTypeId = Config.VDC;
                layoutSetupTransactionBinding.llVdcCard.setBackground(mContext.getDrawable(R.drawable.rounded_corner_selected));
                layoutSetupTransactionBinding.llClassicCard.setBackground(mContext.getDrawable(R.drawable.transparent_secondary_bg));
                break;
            case R.id.btn_next:
                if (atmTypeId == Config.UPI || atmTypeId == Config.VDC) {
                    registerTransactionDetails();
                } else {
                    showAlert(Config.errorType, "Please select any one card !!!");
                }
                break;
            case R.id.bt_back:
//                finish();
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        setLayout();
    }
}
