package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivitySelectAccountTypeBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.AccountTypePostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.AccountTypeResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkResponseData;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AccountInformationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.ConsumerListItemResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;
import java.util.List;

public class SelectAccountTypeActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private ActivitySelectAccountTypeBinding accountTypeBinding;
    private SelectAccountTypeViewModel selectAccountTypeViewModel;
    private int CUSTOMER_ACCOUNT_TYPE_ID = 0;
    private RegisterVerifyOtpResponse registerVerifyOtpResponse;
    private String selectedPurposeOfAccount = "";
    private ArrayList<MobileNetworkResponseData> purposeArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setViewModel();
        getDataFromIntent();
        getPurposeOfAccount();
        setObservers();
        clicks();
    }

    private void getDataFromIntent() {
        registerVerifyOtpResponse = (RegisterVerifyOtpResponse) getIntent().getSerializableExtra("registerVerifyOtpResponse");
    }

    private void clicks() {
        accountTypeBinding.ivBackAccountType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        accountTypeBinding.llSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSavings();
            }
        });
        accountTypeBinding.llCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCurrent();
            }
        });
        accountTypeBinding.layoutBtn.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRegisterConsumerAccountInfo();
            }
        });
        accountTypeBinding.layoutBtn.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void postRegisterConsumerAccountInfo() {
        if (CUSTOMER_ACCOUNT_TYPE_ID == 0) {
            showAlert(Config.errorType, getString(R.string.select_account_type_error_msg));
        } else if (selectedPurposeOfAccount.equals("")) {
            showAlert(Config.errorType, getString(R.string.select_purpose_error_msg));
        } else {
            postAccountType();
        }
    }

    private void postAccountType() {
        showLoading();
        selectAccountTypeViewModel.postAccountType(getAccountTypeParams(),registerVerifyOtpResponse.getData().getConsumerList().get(0).getAccessToken());
    }

    private AccountTypePostParams getAccountTypeParams() {
        AccountInformationResponse accountInformation = registerVerifyOtpResponse.getData().getConsumerList().get(0).getAccountInformation();

        AccountTypePostParams accountTypePostParams = new AccountTypePostParams();
        accountTypePostParams.getData().setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
        accountTypePostParams.getData().setRdaCustomerId(accountInformation.getRdaCustomerId());
        accountTypePostParams.getData().setBankingModeId(accountInformation.getBankingModeId());
        accountTypePostParams.getData().setCustomerAccountTypeId(CUSTOMER_ACCOUNT_TYPE_ID);
        accountTypePostParams.getData().setCustomerBranch(accountInformation.getCustomerBranch());
        accountTypePostParams.getData().setCustomerTypeId(Config.CUSTOMER_TYPE_ID);
        accountTypePostParams.getData().setPurposeOfAccountId(purposeArray.get(accountTypeBinding.spinnerAllPurposes.getSelectedItemPosition()).getId());

        return accountTypePostParams;
    }

    private void selectCurrent() {
        accountTypeBinding.llSavings.setBackground(getDrawable(R.drawable.rounded_corner_white));
        accountTypeBinding.llCurrent.setBackground(getDrawable(R.drawable.rounded_corner_selected));
        CUSTOMER_ACCOUNT_TYPE_ID = Config.CURRENT_ACCOUNT;
    }

    private void selectSavings() {
        accountTypeBinding.llCurrent.setBackground(getDrawable(R.drawable.rounded_corner_white));
        accountTypeBinding.llSavings.setBackground(getDrawable(R.drawable.rounded_corner_selected));
        CUSTOMER_ACCOUNT_TYPE_ID = Config.SAVINGS_ACCOUNT;
    }

    private void setObservers() {
        selectAccountTypeViewModel.accountTypeResponseLiveData.observe(this, new Observer<AccountTypeResponse>() {
            @Override
            public void onChanged(AccountTypeResponse accountTypeResponse) {
                Log.d("accountTypeResponse", "onChanged: " + accountTypeResponse.toString());
                dismissLoading();
                moveToPreferredAccount(accountTypeResponse);
            }
        });
        selectAccountTypeViewModel.purposeOfAccountLiveData.observe(this, new Observer<MobileNetworkResponse>() {
            @Override
            public void onChanged(MobileNetworkResponse mobileNetworkResponse) {
                Log.d("branchesResponse", "onChanged: " + mobileNetworkResponse);
                dismissLoading();
                setSpinner(mobileNetworkResponse);
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

    private void moveToPreferredAccount(AccountTypeResponse accountTypeResponse) {
        Intent intent = new Intent(SelectAccountTypeActivity.this,SelectPreferredAccountActivity.class);
        intent.putExtra("accountTypeResponse",accountTypeResponse);
        intent.putExtra("accessToken",registerVerifyOtpResponse.getData().getConsumerList().get(0).getAccessToken());
        startActivity(intent);
    }

    private void setSpinner(MobileNetworkResponse mobileNetworkResponse) {
        purposeArray = mobileNetworkResponse.getData();
        List<String> _allPurposes = new ArrayList<>();


        if (purposeArray.size() > 0) {
            // Spinner click listener
            accountTypeBinding.spinnerAllPurposes.setOnItemSelectedListener(this);
            // Spinner Drop down elements
            for (int i = 0; i < purposeArray.size(); i++) {
                _allPurposes.add(purposeArray.get(i).getName());
            }

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, _allPurposes);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            accountTypeBinding.spinnerAllPurposes.setAdapter(dataAdapter);
        }
    }

    private void getPurposeOfAccount() {
        showLoading();
        MobileNetworkPostParams mobileNetworkPostParams = new MobileNetworkPostParams();
        mobileNetworkPostParams.getData().codeTypeId = Config.ACCOUNT_PURPOSE_CODE;
        selectAccountTypeViewModel.getPurposeOfAccount(mobileNetworkPostParams);
    }

    private void setBinding() {
        accountTypeBinding = ActivitySelectAccountTypeBinding.inflate(getLayoutInflater());
        setContentView(accountTypeBinding.getRoot());
    }

    private void setViewModel() {
        selectAccountTypeViewModel = new ViewModelProvider(this).get(SelectAccountTypeViewModel.class);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedPurposeOfAccount = accountTypeBinding.spinnerAllPurposes.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}