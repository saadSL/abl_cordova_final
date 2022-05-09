package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivitySelectPreferredAccountBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponseAccountInformation;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.AccountTypePostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.AccountTypeResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AccountInformationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details.PersonalDetailsOneActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details.TaxResidentActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.remitter_details.RemitterDetailsActivity;
import com.unikrew.faceoff.Config;


public class SelectPreferredAccountActivity extends BaseActivity {

    private ActivitySelectPreferredAccountBinding preferredAccountBinding;
    private RegisterVerifyOtpResponse registerVerifyOtpResponse;
    private GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse;
    private SelectAccountTypeViewModel selectAccountTypeViewModel;
    private Integer ACCOUNT_VARIANT_ID;
    private Boolean IS_RESUMED;
    private Integer currencyTypeId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        clicks();
        getSharedPrefData();
        setViewModel();
        setObservers();
        setLogoLayout(preferredAccountBinding.layoutLogo.tvDate);
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

        selectAccountTypeViewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                dismissLoading();
            }
        });
    }

    private void moveToNext() {
        saveIntInPref(Config.ACCOUNT_VARIANT_ID, ACCOUNT_VARIANT_ID);
        if (preferredAccountBinding.cbFreelancerDigitalAccount.isChecked() || preferredAccountBinding.cbAsaanRemittanceDigitalAccount.isChecked()) {
            openActivity(TaxResidentActivity.class);
        } else {
            openActivity(PersonalDetailsOneActivity.class);
        }
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
        preferredAccountBinding.cvAsaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAsaan();
            }
        });
        preferredAccountBinding.cvRemittance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRemittence();
            }
        });
        preferredAccountBinding.cvFreelance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFreeLance();
            }
        });
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

    private void selectDollar() {
        /* Selecting Dollar */
        preferredAccountBinding.btnDollar.setBackground(this.getDrawable(R.drawable.rounded_button));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            preferredAccountBinding.btnDollar.setTextColor(this.getColor(R.color.white));
        }
        currencyTypeId = Config.CURRENCY_DOLLAR;

        /* Deselecting Rupee */
        preferredAccountBinding.btnRupee.setBackground(this.getDrawable(R.drawable.rounded_corner_selected));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            preferredAccountBinding.btnRupee.setTextColor(this.getColor(R.color.custom_blue));
        }
    }

    private void selectRupee() {
        /* Selecting Rupee */
        preferredAccountBinding.btnRupee.setBackground(this.getDrawable(R.drawable.rounded_button));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            preferredAccountBinding.btnRupee.setTextColor(this.getColor(R.color.white));
        }
        currencyTypeId = Config.CURRENCY_RUPEE;

        /* Deselecting Dollar */
        preferredAccountBinding.btnDollar.setBackground(this.getDrawable(R.drawable.rounded_corner_selected));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            preferredAccountBinding.btnDollar.setTextColor(this.getColor(R.color.custom_blue));
        }
    }

    private void checkValidations() {
        if (preferredAccountBinding.cbAsaanDigitalAccount.isChecked() || preferredAccountBinding.cbFreelancerDigitalAccount.isChecked() || preferredAccountBinding.cbAsaanRemittanceDigitalAccount.isChecked()) {
            postPreferredAccount();
        } else {
            showAlert(Config.errorType, getString(R.string.select_preferred_error));
        }
    }

    private void postPreferredAccount() {
        showLoading();
        selectAccountTypeViewModel.postAccountType(getAccountTypeParams(), getStringFromPref(Config.ACCESS_TOKEN));
    }

    private AccountTypePostParams getAccountTypeParams() {
        AccountTypePostParams accountTypePostParams = new AccountTypePostParams();
        if (IS_RESUMED) {
            //flow for drafted application
            GetConsumerAccountDetailsResponseAccountInformation accountInformation = getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getAccountInformation();
            accountTypePostParams.getData().setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
            accountTypePostParams.getData().setRdaCustomerId(accountInformation.getRdaCustomerId());
            accountTypePostParams.getData().setBankingModeId(accountInformation.getBankingModeId());
            accountTypePostParams.getData().setCustomerAccountTypeId(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getCustomerTypeId());
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

        return accountTypePostParams;
    }

    private void selectFreeLance() {
        preferredAccountBinding.liCurrency.setVisibility(View.VISIBLE);
        ACCOUNT_VARIANT_ID = Config.FREELANCE_ACCOUNT;
        preferredAccountBinding.cbAsaanDigitalAccount.setChecked(false);
        preferredAccountBinding.cbAsaanRemittanceDigitalAccount.setChecked(false);
        preferredAccountBinding.cbFreelancerDigitalAccount.setChecked(true);
    }

    private void selectRemittence() {
        currencyTypeId = null;
        preferredAccountBinding.liCurrency.setVisibility(View.GONE);
        ACCOUNT_VARIANT_ID = Config.REMITTANCE_ACCOUNT;
        preferredAccountBinding.cbAsaanDigitalAccount.setChecked(false);
        preferredAccountBinding.cbAsaanRemittanceDigitalAccount.setChecked(true);
        preferredAccountBinding.cbFreelancerDigitalAccount.setChecked(false);
    }

    private void selectAsaan() {
        currencyTypeId = null;
        preferredAccountBinding.liCurrency.setVisibility(View.GONE);
        ACCOUNT_VARIANT_ID = Config.ASAAN_DIGITAL_ACCOUNT;
        preferredAccountBinding.cbAsaanDigitalAccount.setChecked(true);
        preferredAccountBinding.cbAsaanRemittanceDigitalAccount.setChecked(false);
        preferredAccountBinding.cbFreelancerDigitalAccount.setChecked(false);
    }

    private void setBinding() {
        preferredAccountBinding = ActivitySelectPreferredAccountBinding.inflate(getLayoutInflater());
        setContentView(preferredAccountBinding.getRoot());
    }

}