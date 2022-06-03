package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account;


import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivitySelectPreferredAccountBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.AccountTypePostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.AccountTypeResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AccountInformationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodePostParams;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponse;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponseData;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details.PersonalDetailsOneActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details.TaxResidentActivity;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;


public class SelectPreferredAccountActivity extends BaseActivity implements AccountInterface{

    private ActivitySelectPreferredAccountBinding preferredAccountBinding;
    private RegisterVerifyOtpResponse registerVerifyOtpResponse;
    private GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse;
    private SelectAccountTypeViewModel selectAccountTypeViewModel;
    private Integer ACCOUNT_VARIANT_ID;
    private Boolean IS_RESUMED;
    private Integer currencyTypeId = null;
    private ArrayList<LookUpCodeResponseData> preferredAccountList;
    private ArrayList<LookUpCodeResponseData> preferredAccountListForIncomeProof = new ArrayList<>();
    private ArrayList<LookUpCodeResponseData> preferredAccountListForNoIncomeProof = new ArrayList<>();
    private AccountAdapter adapter;

    private Integer proofOfIncome = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        clicks();
        getSharedPrefData();
        setViewModel();
        getPreferredAccounts();
        setObservers();
        setLogoLayout(preferredAccountBinding.layoutLogo.tvDate);
    }

    private void getPreferredAccounts() {
        selectAccountTypeViewModel.getPreferredAccount(getPostParams());
        showLoading();
    }

    private LookUpCodePostParams getPostParams() {
        LookUpCodePostParams postParams = new LookUpCodePostParams();
        postParams.getData().setCodeTypeId(Config.PREFERRED_ACCOUNT_CODE);
        return postParams;
    }

    private void setObservers() {
        selectAccountTypeViewModel.accountTypeResponseLiveData.observe(this, new Observer<AccountTypeResponse>() {
            @Override
            public void onChanged(AccountTypeResponse accountTypeResponse) {
                Log.d("accountTypeResponse", "onChanged: " + accountTypeResponse.toString());
                dismissLoading();
                moveToNext();
            }
        });


        selectAccountTypeViewModel.preferredAccountSuccess.observe(this, new Observer<LookUpCodeResponse>() {
            @Override
            public void onChanged(LookUpCodeResponse lookUpCodeResponse) {
                dismissLoading();
                preferredAccountList = lookUpCodeResponse.getData();
                setPreferredAccountList();
                if (proofOfIncome == 0){
                    setAdapter(preferredAccountListForNoIncomeProof);
                }else{
                    setAdapter(preferredAccountListForIncomeProof);
                }

            }
        });

        selectAccountTypeViewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                dismissLoading();
            }
        });
    }

    private void setPreferredAccountList() {
        for (int i = 0 ; i < preferredAccountList.size() ; i++){
                if (preferredAccountList.get(i).getId() == Config.ASAAN_DIGITAL_ACCOUNT ||
                        preferredAccountList.get(i).getId() == Config.FREELANCE_ACCOUNT ||
                        preferredAccountList.get(i).getId() == Config.REMITTANCE_ACCOUNT)
                {
                    preferredAccountListForNoIncomeProof.add(preferredAccountList.get(i));
                }
                if (preferredAccountList.get(i).getId() == Config.CURRENT_DIGITAL_ACCOUNT)
                {
                    preferredAccountListForIncomeProof.add(preferredAccountList.get(i));
                }
        }
    }

    private void setAdapter(ArrayList<LookUpCodeResponseData> list) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_preferred_account);
        adapter = new AccountAdapter(list,this, (AccountInterface) this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setAdapter(adapter);
    }

    private void moveToNext() {
        saveIntInPref(Config.ACCOUNT_VARIANT_ID, ACCOUNT_VARIANT_ID);
        if (ACCOUNT_VARIANT_ID == Config.FREELANCE_ACCOUNT || ACCOUNT_VARIANT_ID == Config.REMITTANCE_ACCOUNT) {
            openActivity(TaxResidentActivity.class);
        } else {
            openActivity(PersonalDetailsOneActivity.class);
        }
        //        if (preferredAccountBinding.cbFreelancerDigitalAccount.isChecked() || preferredAccountBinding.cbAsaanRemittanceDigitalAccount.isChecked()) {
//            openActivity(TaxResidentActivity.class);
//        } else {
//            openActivity(PersonalDetailsOneActivity.class);
//        }
    }


    private void setViewModel() {
        selectAccountTypeViewModel = new ViewModelProvider(this).get(SelectAccountTypeViewModel.class);
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

    private void clicks() {
//        preferredAccountBinding.cvAsaan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectAsaan();
//            }
//        });
//        preferredAccountBinding.cvRemittance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectRemittence();
//            }
//        });
//        preferredAccountBinding.cvFreelance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectFreeLance();
//            }
//        });
//        preferredAccountBinding.cvCurrent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectCurrent();
//            }
//        });
        preferredAccountBinding.btnRupee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectRupee();
            }
        });
        preferredAccountBinding.btnDollar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDollar();
            }
        });
        preferredAccountBinding.btnYen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectYen();
            }
        });
        preferredAccountBinding.btnPound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPound();
            }
        });
        preferredAccountBinding.btnEuro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectEuro();
            }
        });

        preferredAccountBinding.btnYesIncomeProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proofOfIncome = 1;
                preferredAccountBinding.btnYesIncomeProof.setBackground(SelectPreferredAccountActivity.this.getDrawable(R.drawable.button_blue_shape));
                preferredAccountBinding.btnNoIncomeProof.setBackground(SelectPreferredAccountActivity.this.getDrawable(R.drawable.transparent_bg));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    preferredAccountBinding.btnYesIncomeProof.setTextColor(getColor(R.color.white));
                    preferredAccountBinding.btnNoIncomeProof.setTextColor(getColor(R.color.black));
                }
                adapter.setList(preferredAccountListForIncomeProof);
            }
        });

        preferredAccountBinding.btnNoIncomeProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proofOfIncome = 0;
                preferredAccountBinding.btnNoIncomeProof.setBackground(SelectPreferredAccountActivity.this.getDrawable(R.drawable.button_blue_shape));
                preferredAccountBinding.btnYesIncomeProof.setBackground(SelectPreferredAccountActivity.this.getDrawable(R.drawable.transparent_bg));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    preferredAccountBinding.btnNoIncomeProof.setTextColor(getColor(R.color.white));
                    preferredAccountBinding.btnYesIncomeProof.setTextColor(getColor(R.color.black));
                }
                adapter.setList(preferredAccountListForNoIncomeProof);
                deselectCurrent();
            }
        });
        preferredAccountBinding.layoutBtn.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        preferredAccountBinding.layoutBtn.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidations();
            }
        });
    }

    private void selectYen() {
        currencyTypeId = Config.CURRENCY_YEN;
        deselectCurrency(preferredAccountBinding.btnRupee);
        deselectCurrency(preferredAccountBinding.btnDollar);
        selectCurrency(preferredAccountBinding.btnYen);
        deselectCurrency(preferredAccountBinding.btnPound);
        deselectCurrency(preferredAccountBinding.btnEuro);
    }

    private void selectPound() {
        currencyTypeId = Config.CURRENCY_POUND;
        deselectCurrency(preferredAccountBinding.btnRupee);
        deselectCurrency(preferredAccountBinding.btnDollar);
        deselectCurrency(preferredAccountBinding.btnYen);
        selectCurrency(preferredAccountBinding.btnPound);
        deselectCurrency(preferredAccountBinding.btnEuro);
    }

    private void selectEuro() {
        currencyTypeId = Config.CURRENCY_EURO;
        deselectCurrency(preferredAccountBinding.btnRupee);
        deselectCurrency(preferredAccountBinding.btnDollar);
        deselectCurrency(preferredAccountBinding.btnYen);
        deselectCurrency(preferredAccountBinding.btnPound);
        selectCurrency(preferredAccountBinding.btnEuro);
    }


    private void selectDollar() {
        currencyTypeId = Config.CURRENCY_DOLLAR;
        deselectCurrency(preferredAccountBinding.btnRupee);
        selectCurrency(preferredAccountBinding.btnDollar);
        deselectCurrency(preferredAccountBinding.btnYen);
        deselectCurrency(preferredAccountBinding.btnPound);
        deselectCurrency(preferredAccountBinding.btnEuro);
    }

    private void selectRupee() {
        currencyTypeId = Config.CURRENCY_RUPEE;
        selectCurrency(preferredAccountBinding.btnRupee);
        deselectCurrency(preferredAccountBinding.btnDollar);
        deselectCurrency(preferredAccountBinding.btnYen);
        deselectCurrency(preferredAccountBinding.btnPound);
        deselectCurrency(preferredAccountBinding.btnEuro);
    }

    private void deselectCurrency(Button button) {
        button.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_corner_selected));
        button.setTextColor(ContextCompat.getColor(this, R.color.custom_blue));
    }

    private void selectCurrency(Button button) {
        button.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
        button.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    private void checkValidations() {
//        if (preferredAccountBinding.cbAsaanDigitalAccount.isChecked() || preferredAccountBinding.cbFreelancerDigitalAccount.isChecked() || preferredAccountBinding.cbAsaanRemittanceDigitalAccount.isChecked()|| preferredAccountBinding.cbCurrentAccount.isChecked()) {
//            postPreferredAccount();
//        }
        if (ACCOUNT_VARIANT_ID == null){
            showAlert(Config.errorType,getString(R.string.select_preferred_error));
        }else if (ACCOUNT_VARIANT_ID == Config.CURRENT_DIGITAL_ACCOUNT && currencyTypeId == null){
            showAlert(Config.errorType,getString(R.string.select_preferred_currency));
        }else{
            postPreferredAccount();
        }

//        else if ((preferredAccountBinding.cbCurrentAccount.isChecked() || preferredAccountBinding.cbFreelancerDigitalAccount.isChecked()) && currencyTypeId == null) {
//            showAlert(Config.errorType, getString(R.string.select_preferred_currency));
//        } else {
//            showAlert(Config.errorType, getString(R.string.select_preferred_error));
//        }
    }

    private void postPreferredAccount() {
        showLoading();
        selectAccountTypeViewModel.postAccountType(getAccountTypeParams(), getStringFromPref(Config.ACCESS_TOKEN));
    }

    private AccountTypePostParams getAccountTypeParams() {
        AccountTypePostParams accountTypePostParams = new AccountTypePostParams();
        if (IS_RESUMED) {
            //flow for drafted application
            AccountInformationResponse accountInformation = getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getAccountInformation();
            accountTypePostParams.getData().setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
            accountTypePostParams.getData().setRdaCustomerId(accountInformation.getRdaCustomerId());
            accountTypePostParams.getData().setBankingModeId(accountInformation.getBankingModeId());
            accountTypePostParams.getData().setCustomerAccountTypeId(accountInformation.getCustomerAccountTypeId());
            accountTypePostParams.getData().setCustomerBranch(accountInformation.getCustomerBranch());
            accountTypePostParams.getData().setPurposeOfAccountId(accountInformation.getPurposeOfAccountId());

        } else {
            //flow for new application
            AccountInformationResponse accountInformation = registerVerifyOtpResponse.getData().getConsumerList().get(0).getAccountInformation();
            accountTypePostParams.getData().setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
            accountTypePostParams.getData().setRdaCustomerId(accountInformation.getRdaCustomerId());
            accountTypePostParams.getData().setBankingModeId(accountInformation.getBankingModeId());
            accountTypePostParams.getData().setCustomerAccountTypeId(accountInformation.getCustomerAccountTypeId());
            accountTypePostParams.getData().setCustomerBranch(accountInformation.getCustomerBranch());
            accountTypePostParams.getData().setPurposeOfAccountId(accountInformation.getPurposeOfAccountId());
        }
        accountTypePostParams.getData().setCustomerTypeId(Config.CUSTOMER_TYPE_ID);
        accountTypePostParams.getData().setAccountVariantId(ACCOUNT_VARIANT_ID);
        accountTypePostParams.getData().setCurrencyTypeId(currencyTypeId);
        accountTypePostParams.getData().setProofOfIncomeInd(proofOfIncome);

        return accountTypePostParams;
    }

//    private void selectFreeLance() {
//        preferredAccountBinding.liCurrency.setVisibility(View.VISIBLE);
//        preferredAccountBinding.liCurrentCurrencies.setVisibility(View.GONE);
//        ACCOUNT_VARIANT_ID = Config.FREELANCE_ACCOUNT;
//        preferredAccountBinding.cbAsaanDigitalAccount.setChecked(false);
//        preferredAccountBinding.cbAsaanRemittanceDigitalAccount.setChecked(false);
//        preferredAccountBinding.cbCurrentAccount.setChecked(false);
//        preferredAccountBinding.cbFreelancerDigitalAccount.setChecked(true);
//    }
//
//    private void selectRemittence() {
//        currencyTypeId = null;
//        preferredAccountBinding.liCurrency.setVisibility(View.GONE);
//        preferredAccountBinding.liCurrentCurrencies.setVisibility(View.GONE);
//        ACCOUNT_VARIANT_ID = Config.REMITTANCE_ACCOUNT;
//        preferredAccountBinding.cbAsaanDigitalAccount.setChecked(false);
//        preferredAccountBinding.cbAsaanRemittanceDigitalAccount.setChecked(true);
//        preferredAccountBinding.cbFreelancerDigitalAccount.setChecked(false);
//        preferredAccountBinding.cbCurrentAccount.setChecked(false);
//    }
//
//    private void selectAsaan() {
//        currencyTypeId = null;
//        preferredAccountBinding.liCurrency.setVisibility(View.GONE);
//        preferredAccountBinding.liCurrentCurrencies.setVisibility(View.GONE);
//        ACCOUNT_VARIANT_ID = Config.ASAAN_DIGITAL_ACCOUNT;
//        preferredAccountBinding.cbAsaanDigitalAccount.setChecked(true);
//        preferredAccountBinding.cbAsaanRemittanceDigitalAccount.setChecked(false);
//        preferredAccountBinding.cbFreelancerDigitalAccount.setChecked(false);
//        preferredAccountBinding.cbCurrentAccount.setChecked(false);
//    }
//
    private void selectCurrent() {
        preferredAccountBinding.liCurrency.setVisibility(View.VISIBLE);
        preferredAccountBinding.liCurrentCurrencies.setVisibility(View.VISIBLE);
        ACCOUNT_VARIANT_ID = Config.CURRENT_DIGITAL_ACCOUNT;
//        preferredAccountBinding.cbAsaanDigitalAccount.setChecked(false);
//        preferredAccountBinding.cbAsaanRemittanceDigitalAccount.setChecked(false);
//        preferredAccountBinding.cbCurrentAccount.setChecked(true);
//        preferredAccountBinding.cbFreelancerDigitalAccount.setChecked(false);
    }


    private void setBinding() {
        preferredAccountBinding = ActivitySelectPreferredAccountBinding.inflate(getLayoutInflater());
        setContentView(preferredAccountBinding.getRoot());
    }

    @Override
    public void setSelectionAt(int position) {
        if (proofOfIncome == 0){
            for (int i = 0 ; i < preferredAccountListForNoIncomeProof.size() ; i++){
                preferredAccountListForNoIncomeProof.get(i).setSelected(false);
            }
            preferredAccountListForNoIncomeProof.get(position).setSelected(true);
            ACCOUNT_VARIANT_ID = preferredAccountListForNoIncomeProof.get(position).getId();
            adapter.setList(preferredAccountListForNoIncomeProof);
        }else{
            for (int i = 0 ; i < preferredAccountListForIncomeProof.size() ; i++){
                preferredAccountListForIncomeProof.get(i).setSelected(false);
            }
            preferredAccountListForIncomeProof.get(position).setSelected(true);
            ACCOUNT_VARIANT_ID = preferredAccountListForIncomeProof.get(position).getId();
            adapter.setList(preferredAccountListForIncomeProof);
        }
        if (ACCOUNT_VARIANT_ID == Config.CURRENT_DIGITAL_ACCOUNT){
            selectCurrent();
        }

    }

    private void deselectCurrent() {
        preferredAccountBinding.liCurrency.setVisibility(View.GONE);
        preferredAccountBinding.liCurrentCurrencies.setVisibility(View.GONE);
        ACCOUNT_VARIANT_ID = null;
    }
}