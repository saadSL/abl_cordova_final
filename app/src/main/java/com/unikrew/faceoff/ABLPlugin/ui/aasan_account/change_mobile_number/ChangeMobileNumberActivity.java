package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.change_mobile_number;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ChangeMobileNumberBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.change_mobile_number.ChangeMobileNumberPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.change_mobile_number.ChangeMobileNumberResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpResponse;
import com.unikrew.faceoff.Config;

public class ChangeMobileNumberActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ChangeMobileNumberBinding binding;
    private Boolean portedMobileNetwork = false;

    ChangeMobileNumberViewModel viewModel;
    ChangeMobileNumberPostParams postParams;

    ViewAppsGenerateOtpResponse res;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setListner();
        setViewModel();
        observe();
    }

    private void observe() {
        viewModel.changeMobileNumSuccess.observe(this, new Observer<ChangeMobileNumberResponse>() {
            @Override
            public void onChanged(ChangeMobileNumberResponse changeMobileNumberResponse) {
                showAlert(Config.successType,changeMobileNumberResponse.getMessage().getDescription());
                loader.dismiss();
            }
        });

        viewModel.changeMobileNumError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType,errMsg);
                loader.dismiss();
            }
        });
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(ChangeMobileNumberViewModel.class);
        postParams = new ChangeMobileNumberPostParams();
        res = (ViewAppsGenerateOtpResponse) getIntent().getSerializableExtra(Config.RESPONSE);
    }

    private void setListner() {
        binding.btnContainer.btnNext.setOnClickListener(this);
        binding.btnContainer.btnCancel.setOnClickListener(this);
        binding.portedMobileNetworkSwitch.setOnCheckedChangeListener(this);
    }

    private void setBinding() {
        binding = ChangeMobileNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                if (isValid()){
                    setChangeMobileNumberPostParams();
                    changeMobileNumber();
                }
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    private boolean isValid() {
        if(binding.etMobileNum.getText().length() < Config.MOBILE_NUMBER_LENGTH ){
            showAlert(Config.errorType,"Mobile Number Length Not Valid !!!");
            return false;
        }else if (!wifiAvailable()){
            showAlert(Config.errorType,"No Internet Connection !!!");
            return false;
        }
        return true;
    }

    private void changeMobileNumber() {
        viewModel.changeMobileNumber(postParams);
        showLoading();
        loader.show();
    }

    private void setChangeMobileNumberPostParams() {
        postParams.getData().setMobileNo(binding.etMobileNum.getText().toString());
        postParams.getData().setRdaCustomerProfileId(res.getData().getRdaCustomerProfileId());
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked){
            showMobileInfoDialogue();
            portedMobileNetwork = true;
        }else{
            portedMobileNetwork = false;
        }
    }


}
