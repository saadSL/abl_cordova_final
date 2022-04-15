package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivitySelectPreferredAccountBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.AccountTypePostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.AccountTypeResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.AccountTypeResponseData;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details.PersonalDetailsOneActivity;
import com.unikrew.faceoff.Config;


public class SelectPreferredAccountActivity extends BaseActivity {

    private ActivitySelectPreferredAccountBinding preferredAccountBinding;
    private AccountTypeResponse accountTypeResponse;
    private String accessToken;
    private SelectAccountTypeViewModel selectAccountTypeViewModel;
    private Integer ACCOUNT_VARIANT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        clicks();
        getDataFromIntent();
        setViewModel();
        setObservers();
    }

    private void setObservers() {
        selectAccountTypeViewModel.accountTypeResponseLiveData.observe(this, new Observer<AccountTypeResponse>() {
            @Override
            public void onChanged(AccountTypeResponse accountTypeResponse) {
                Log.d("accountTypeResponse", "onChanged: " + accountTypeResponse.toString());
                dismissLoading();
                goToPersonalDetails();
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

    private void goToPersonalDetails() {
        Intent intent = new Intent(this, PersonalDetailsOneActivity.class);
        startActivity(intent);
    }

    private void setViewModel() {
        selectAccountTypeViewModel = new ViewModelProvider(this).get(SelectAccountTypeViewModel.class);
    }

    private void getDataFromIntent() {
        accountTypeResponse = (AccountTypeResponse) getIntent().getSerializableExtra("accountTypeResponse");
        accessToken = (String) getIntent().getStringExtra("accessToken");
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
        preferredAccountBinding.ivBackPreferred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                if (preferredAccountBinding.cbAsaanDigitalAccount.isChecked() || preferredAccountBinding.cbFreelancerDigitalAccount.isChecked() || preferredAccountBinding.cbAsaanRemittanceDigitalAccount.isChecked()) {
                    postPreferredAccount();
                } else {
                    showAlert(Config.errorType, getString(R.string.select_preferred_error));
                }
            }
        });
    }

    private void postPreferredAccount() {
        showLoading();
        selectAccountTypeViewModel.postAccountType(getAccountTypeParams(),accessToken);
    }

    private AccountTypePostParams getAccountTypeParams() {
        AccountTypeResponseData accountInformation = accountTypeResponse.getData();

        AccountTypePostParams accountTypePostParams = new AccountTypePostParams();
        accountTypePostParams.getData().setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
        accountTypePostParams.getData().setRdaCustomerId(accountInformation.getRdaCustomerId());
        accountTypePostParams.getData().setBankingModeId(accountInformation.getBankingModeId());
        accountTypePostParams.getData().setCustomerAccountTypeId(accountInformation.getCustomerAccountTypeId());
        accountTypePostParams.getData().setCustomerBranch(accountInformation.getCustomerBranch());
        accountTypePostParams.getData().setCustomerTypeId(Config.CUSTOMER_TYPE_ID);
        accountTypePostParams.getData().setPurposeOfAccountId(accountInformation.getPurposeOfAccountId());
        accountTypePostParams.getData().setAccountVariantId(ACCOUNT_VARIANT_ID);

        return accountTypePostParams;
    }

    private void selectFreeLance() {
        ACCOUNT_VARIANT_ID = Config.FREELANCE_ACCOUNT;
        preferredAccountBinding.cbAsaanDigitalAccount.setChecked(false);
        preferredAccountBinding.cbAsaanRemittanceDigitalAccount.setChecked(false);
        preferredAccountBinding.cbFreelancerDigitalAccount.setChecked(true);
    }

    private void selectRemittence() {
        ACCOUNT_VARIANT_ID = Config.REMITTANCE_ACCOUNT;
        preferredAccountBinding.cbAsaanDigitalAccount.setChecked(false);
        preferredAccountBinding.cbAsaanRemittanceDigitalAccount.setChecked(true);
        preferredAccountBinding.cbFreelancerDigitalAccount.setChecked(false);
    }

    private void selectAsaan() {
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