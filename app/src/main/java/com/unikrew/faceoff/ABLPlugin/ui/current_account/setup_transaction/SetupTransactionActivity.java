package com.unikrew.faceoff.ABLPlugin.ui.current_account.setup_transaction;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.LayoutCurrentAccountSetupTransactionBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.ConsumerListItemResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.setup_transaction.SetupTransactionPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.setup_transaction.SetupTransactionResponse;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodePostParams;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponse;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponseData;
import com.unikrew.faceoff.ABLPlugin.ui.current_account.kin_details.KinDetailsActivity;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;
import java.util.List;

public class SetupTransactionActivity extends BaseActivity implements SelectCardInterface, CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private LayoutCurrentAccountSetupTransactionBinding setupTransactionBinding;
    private SetupTransactionPostParams setupTransactionPostParams;
    private SelectCardViewModel selectCardViewModel;


    private LookUpCodePostParams atmCardsPostParams;
    private ArrayList<LookUpCodeResponseData> atmCardsList;

    private int atmCardInd = 0;
    private int atmTypeId = 0;
    private int transactionAlertInd = 0;
    private int chequeBookReqInd = 0;
    private int transactionAlertId = 0;
    private int esoaReqInd = 0;

    private SelectCardAdapter adapter;
    private LookUpCodeResponseData selectedVisaCardReason = null;
    private LookUpCodeResponseData selectedCardDeliveryOption;
    private List<ConsumerListItemResponse> consumerList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setViewModel();
        checkLayouts();
        setClicks();
        getSharedPrefData();
        getAtmCards();
        getVisaCardReasons();
        getCardDeliveryOptions();
        observe();
    }

    private void getSharedPrefData() {

        //flow for new application
        if (getSerializableFromPref(Config.GET_CONSUMER_RESPONSE, GetConsumerAccountDetailsResponse.class) == null) {
            RegisterVerifyOtpResponse registerVerifyOtpResponse = (RegisterVerifyOtpResponse) getSerializableFromPref(Config.REG_OTP_RESPONSE, RegisterVerifyOtpResponse.class);
            consumerList = registerVerifyOtpResponse.getData().getConsumerList();
        }
        //flow for drafted application
        else {

            GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse = (GetConsumerAccountDetailsResponse) getSerializableFromPref(Config.GET_CONSUMER_RESPONSE, GetConsumerAccountDetailsResponse.class);
            consumerList = getConsumerAccountDetailsResponse.getData().getConsumerList();
        }

    }

    private void checkLayouts() {
        if (setupTransactionBinding.swTransactionAlerts.isChecked()) {
            setupTransactionBinding.btnSms.setEnabled(true);
            setupTransactionBinding.btnEmail.setEnabled(true);
        } else {
            setupTransactionBinding.btnSms.setEnabled(false);
            setupTransactionBinding.btnEmail.setEnabled(false);
        }
    }

    private void getCardDeliveryOptions() {
        selectCardViewModel.getCardDeliveryOptions(getCardDeliveryPostParams());
        showLoading();
    }

    private LookUpCodePostParams getCardDeliveryPostParams() {
        LookUpCodePostParams lookUpCodePostParams = new LookUpCodePostParams();
        lookUpCodePostParams.getData().setCodeTypeId(Config.DEBIT_CARD_DELIVERY_CODE);
        return lookUpCodePostParams;
    }

    private void getVisaCardReasons() {

        selectCardViewModel.getVisaCardReasons(getVisaCardPostParams());
        showLoading();
    }

    private LookUpCodePostParams getVisaCardPostParams() {
        LookUpCodePostParams lookUpCodePostParams = new LookUpCodePostParams();
        lookUpCodePostParams.getData().setCodeTypeId(Config.VISA_CARD_REASONS_CODE);
        return lookUpCodePostParams;
    }

    private void setClicks() {
        setupTransactionBinding.swDebitCard.setOnCheckedChangeListener(this);
        setupTransactionBinding.swChequeBook.setOnCheckedChangeListener(this);
        setupTransactionBinding.swTransactionAlerts.setOnCheckedChangeListener(this);
        setupTransactionBinding.btnSms.setOnClickListener(this);
        setupTransactionBinding.btnEmail.setOnClickListener(this);
        setupTransactionBinding.btnContainer.btnNext.setOnClickListener(this);
        setupTransactionBinding.btnContainer.btBack.setOnClickListener(this);
//        layoutSetupTransactionBinding.llClassicCard.setOnClickListener(this);
//        layoutSetupTransactionBinding.llVdcCard.setOnClickListener(this);
    }

    private void observe() {
        selectCardViewModel.setupTransactionResponseMutableLiveData.observe(this, new Observer<SetupTransactionResponse>() {
            @Override
            public void onChanged(SetupTransactionResponse setupTransactionResponse) {
                goToNext();
                dismissLoading();
            }
        });

        selectCardViewModel.setupTransactionErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                dismissLoading();
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

        selectCardViewModel.visaCardSuccessResponse.observe(this, new Observer<LookUpCodeResponse>() {
            @Override
            public void onChanged(LookUpCodeResponse lookUpCodeResponse) {
                dismissLoading();
                setVisaCardReasonsSpinner(lookUpCodeResponse.getData());
            }
        });

        selectCardViewModel.cardDeliverySuccessResponse.observe(this, new Observer<LookUpCodeResponse>() {
            @Override
            public void onChanged(LookUpCodeResponse lookUpCodeResponse) {
                dismissLoading();
                setCardDeliverySpinner(lookUpCodeResponse.getData());
            }
        });

        selectCardViewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                dismissLoading();
                showAlert(Config.errorType, errMsg);
            }
        });
    }

    private void goToNext() {
        openActivity(KinDetailsActivity.class);
    }

    private void setCardDeliverySpinner(ArrayList<LookUpCodeResponseData> lookUpCodeResponse) {
        ArrayList<LookUpCodeResponseData> _allReasons = new ArrayList<>();

        LookUpCodeResponseData data = new LookUpCodeResponseData();
        data.setDescription("");
        data.setId(0);
        data.setName("Choose Reasons");

        _allReasons.add(data);

        setupTransactionBinding.spDebitCardDelivery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    selectedCardDeliveryOption = (LookUpCodeResponseData) adapterView.getSelectedItem();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        for (int i = 0; i < lookUpCodeResponse.size(); i++) {
            _allReasons.add(lookUpCodeResponse.get(i));
        }

        ArrayAdapter<LookUpCodeResponseData> dataAdapter = new ArrayAdapter<LookUpCodeResponseData>(this, android.R.layout.simple_spinner_item, _allReasons) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setupTransactionBinding.spDebitCardDelivery.setAdapter(dataAdapter);
    }

    private void setVisaCardReasonsSpinner(ArrayList<LookUpCodeResponseData> lookUpCodeResponse) {
        ArrayList<LookUpCodeResponseData> _allReasons = new ArrayList<>();

        LookUpCodeResponseData data = new LookUpCodeResponseData();
        data.setDescription("");
        data.setId(0);
        data.setName("Choose Reasons");

        _allReasons.add(data);

        setupTransactionBinding.spVisaCardReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    selectedVisaCardReason = (LookUpCodeResponseData) adapterView.getSelectedItem();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        for (int i = 0; i < lookUpCodeResponse.size(); i++) {
            _allReasons.add(lookUpCodeResponse.get(i));
        }

        ArrayAdapter<LookUpCodeResponseData> dataAdapter = new ArrayAdapter<LookUpCodeResponseData>(this, android.R.layout.simple_spinner_item, _allReasons) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setupTransactionBinding.spVisaCardReason.setAdapter(dataAdapter);
    }

    private void setAtmCardAdapter() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_atm_card);
        adapter = new SelectCardAdapter(atmCardsList, SetupTransactionActivity.this, SetupTransactionActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(SetupTransactionActivity.this, LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setAdapter(adapter);
    }

    private void getAtmCards() {
        setAtmCardsPostParams();
        selectCardViewModel.getAtmCards(atmCardsPostParams);
        showLoading();
    }

    private void setViewModel() {
        selectCardViewModel = new ViewModelProvider(this).get(SelectCardViewModel.class);
        setupTransactionPostParams = new SetupTransactionPostParams();
        atmCardsPostParams = new LookUpCodePostParams();
    }

    private void setAtmCardsPostParams() {
        atmCardsPostParams.getData().setCodeTypeId(Config.ATM_CARDS_ID);
    }

    private void setBinding() {
        setupTransactionBinding = LayoutCurrentAccountSetupTransactionBinding.inflate(getLayoutInflater());
        setContentView(setupTransactionBinding.getRoot());
    }

    @Override
    public void setSelectionAt(int position) {
        if (atmCardInd == 1) {
            for (int i = 0; i < atmCardsList.size(); i++) {
                atmCardsList.get(i).setSelected(false);
            }
            atmCardsList.get(position).setSelected(true);
            atmTypeId = atmCardsList.get(position).getId();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.sw_debit_card:
                if (isChecked) {
                    atmCardInd = 1;
                    setupTransactionBinding.llVisaCardReason.setVisibility(View.VISIBLE);
                } else {
                    atmCardInd = 0;
                    setupTransactionBinding.llVisaCardReason.setVisibility(View.GONE);
                }
                break;
            case R.id.sw_transaction_alerts:
                if (isChecked) {
                    transactionAlertInd = 1;
                    setupTransactionBinding.btnSms.setEnabled(true);
                    setupTransactionBinding.btnEmail.setEnabled(true);
                } else {
                    transactionAlertInd = 0;
                    setupTransactionBinding.btnSms.setEnabled(false);
                    setupTransactionBinding.btnEmail.setEnabled(false);
                }
                break;
            case R.id.sw_cheque_book:
                if (isChecked) {
                    chequeBookReqInd = 1;
                } else {
                    chequeBookReqInd = 0;
                }
                break;

            case R.id.sw_electronic_soa:
                if (isChecked) {
                    esoaReqInd = 1;
                } else {
                    esoaReqInd = 0;
                }
                break;

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                registerTransactionDetails();
                break;
            case R.id.bt_back:
                finish();
                break;
            case R.id.btn_sms:
                transactionAlertId = Config.TRANSACTION_ALERT_SMS;
                setupTransactionBinding.btnEmail.setBackground(this.getDrawable(R.drawable.rounded_corner_selected));
                setupTransactionBinding.btnSms.setBackground(this.getDrawable(R.drawable.rounded_button));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setupTransactionBinding.btnSms.setTextColor(this.getColor(R.color.white));
                    setupTransactionBinding.btnEmail.setTextColor(this.getColor(R.color.custom_blue));
                }
                break;
            case R.id.btn_email:
                transactionAlertId = Config.TRANSACTION_ALERT_EMAIL;
                setupTransactionBinding.btnSms.setBackground(this.getDrawable(R.drawable.rounded_corner_selected));
                setupTransactionBinding.btnEmail.setBackground(this.getDrawable(R.drawable.rounded_button));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setupTransactionBinding.btnEmail.setTextColor(this.getColor(R.color.white));
                    setupTransactionBinding.btnSms.setTextColor(this.getColor(R.color.custom_blue));
                }
                break;
        }
    }

    private void registerTransactionDetails() {
        if (isValid()) {
            setTransactionDetailsPostParams();
            selectCardViewModel.registerTransactionDetails(setupTransactionPostParams, getStringFromPref(Config.ACCESS_TOKEN));
            showLoading();
        }
    }

    private boolean isValid() {
        if (atmCardInd == 1) {
            if (atmTypeId == 0) {
                showAlert(Config.errorType, "Please Select A Card !!!");
                return false;
            } else if (selectedVisaCardReason == null) {
                showAlert(Config.errorType, "Please Select A Reason For Requiring Visa Card !!!");
                return false;
            }
        }
        if (selectedCardDeliveryOption == null) {
            showAlert(Config.errorType, "Please Select Card Delivery Option !!!");
            return false;
        }
        if (transactionAlertInd == 1) {
            if (transactionAlertId == 0) {
                showAlert(Config.errorType, "Please Select Where To Send Transaction Alert !!!");
                return false;
            }
        }
        return true;
    }

    private void setTransactionDetailsPostParams() {


        setupTransactionPostParams.getData().setRdaCustomerAccInfoId(consumerList.get(0).getAccountInformation().getRdaCustomerAccInfoId());
        setupTransactionPostParams.getData().setRdaCustomerId(consumerList.get(0).getAccountInformation().getRdaCustomerId());

        setupTransactionPostParams.getData().setCustomerTypeId(Config.CUSTOMER_TYPE_ID);
        setupTransactionPostParams.getData().setAtmTypeId(atmTypeId);
        setupTransactionPostParams.getData().setReasonForVisaDebitCardRequestId(selectedVisaCardReason.getId());
        setupTransactionPostParams.getData().setMailingAddrPrefId(selectedCardDeliveryOption.getId());
        setupTransactionPostParams.getData().setChequeBookReqInd(chequeBookReqInd);
        setupTransactionPostParams.getData().setEsoaInd(esoaReqInd);
        setupTransactionPostParams.getData().setTransAlertInd(transactionAlertInd);
        setupTransactionPostParams.getData().setTransactionalAlertId(transactionAlertId);
    }
}
