package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account;


import static androidx.navigation.ViewKt.findNavController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivitySelectPreferredAccountBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseFragment;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponseAccountInformation;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.AccountTypePostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.AccountTypeResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AccountInformationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.Config;


public class SelectPreferredAccountFragment extends BaseFragment {

    private ActivitySelectPreferredAccountBinding preferredAccountBinding;
    private RegisterVerifyOtpResponse registerVerifyOtpResponse;
    private GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse;
    private SelectAccountTypeViewModel selectAccountTypeViewModel;
    private Integer ACCOUNT_VARIANT_ID;
    private Boolean IS_RESUMED;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        preferredAccountBinding = ActivitySelectPreferredAccountBinding.inflate(inflater, container, false);
        clicks();
        getSharedPrefData();
        setViewModel();
        setObservers();
        setLogoLayout(preferredAccountBinding.layoutLogo.tvDate);

        return preferredAccountBinding.getRoot();
    }

    private void setObservers() {
        selectAccountTypeViewModel.accountTypeResponseLiveData.observe(getViewLifecycleOwner(), new Observer<AccountTypeResponse>() {
            @Override
            public void onChanged(AccountTypeResponse accountTypeResponse) {
                Log.d("accountTypeResponse", "onChanged: " + accountTypeResponse.toString());
                dismissLoading();
                goToPersonalDetails();
            }
        });

        selectAccountTypeViewModel.errorLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                dismissLoading();
            }
        });
    }

    private void goToPersonalDetails() {
        findNavController(preferredAccountBinding.getRoot()).navigate(R.id.openPersonalDetailsOne);
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
        preferredAccountBinding.layoutBtn.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                finish();
            }
        });
        preferredAccountBinding.layoutBtn.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidations();
            }
        });
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
        selectAccountTypeViewModel.postAccountType(getAccountTypeParams(),  getStringFromPref(Config.ACCESS_TOKEN));
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



}