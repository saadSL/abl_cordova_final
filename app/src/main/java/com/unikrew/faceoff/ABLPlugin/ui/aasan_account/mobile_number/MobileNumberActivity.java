package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.mobile_number;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.MobileNumberAvailabilityBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpResponse;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.cnic_upload.CnicUploadActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.otp_phase2.OtpVerification;
import com.unikrew.faceoff.Config;

public class MobileNumberActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    /* variables  */
    private MobileNumberViewModel viewModel;
    private ViewAppsGenerateOtpPostParams postParams;

    private Boolean isPortedMobileNetwork = false;
    private Boolean generateOtp = false;

    private MobileNumberAvailabilityBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setListeners();
        setViewModel();
        observeData();
    }

    private void setBinding() {
        binding = MobileNumberAvailabilityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


    private void observeData() {
        viewModel.responseLiveData.observe(this, new Observer<ViewAppsGenerateOtpResponse>() {
            @Override
            public void onChanged(ViewAppsGenerateOtpResponse viewAppsGenerateOtpResponse) {
                if ( viewAppsGenerateOtpResponse.getData().isAlreadyExist() ){
                    if (generateOtp){
                        openOtpVerificationActivity(viewAppsGenerateOtpResponse);
                    }else{
                        showCnic(viewAppsGenerateOtpResponse);
                    }
                }else{
                    openCnicUploadActivity();
                }
                loader.dismiss();
            }
        });

        viewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType,errMsg);
                loader.dismiss();
            }
        });

    }

    private void openOtpVerificationActivity(ViewAppsGenerateOtpResponse response) {
        Intent intent = new Intent(this, OtpVerification.class);

        intent.putExtra(Config.RESPONSE,response);

        startActivity(intent);
    }

    /* The method below will work when mobile number is not registered with cnic. */
    private void openCnicUploadActivity() {
        Intent intent = new Intent(this, CnicUploadActivity.class);

        intent.putExtra(Config.MOBILE_NUMBER, binding.etMobileNum.getText().toString());
        intent.putExtra(Config.PORTED_MOBILE_NETWORK, isPortedMobileNetwork);

        startActivity(intent);
    }

    /* The method below will work when mobile number is already registered with cnic. */
    private void showCnic(ViewAppsGenerateOtpResponse response) {
        binding.llCnic.setVisibility(View.VISIBLE);

        binding.etCnicNumber.setText(response.getData().getIdNumber().toString());
        generateOtp = true;

    }


    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(MobileNumberViewModel.class);
        postParams = new ViewAppsGenerateOtpPostParams();
    }


    private void setListeners() {
        /* Mobile Number Availability */
      binding.portedMobileNetworkSwitch.setOnCheckedChangeListener(this);

        binding.btnContainer.btnNext.setOnClickListener(this);
        binding.btnContainer.btnCancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                if (!validateMobileNumActivity()){
                    return;
                }else{
                    setPostParams();
                    viewAppsGenerateOtpPostData();
                }
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    private void viewAppsGenerateOtpPostData() {
        viewModel.viewAppsGenerateOtpPostData(postParams);
        showLoading();
        loader.show();
    }

    private void setPostParams() {
        postParams.getData().setCustomerTypeId(Config.CUSTOMER_TYPE_ID);
        postParams.getData().setMobileNo(binding.etMobileNum.getText().toString());
        postParams.getData().setGenerateOtp(generateOtp);

        if ( generateOtp ){
            postParams.getData().setIdNumber(binding.etCnicNumber.getText().toString());
            postParams.getData().setPortedMobileNetwork(isPortedMobileNetwork);
        }

    }

    private Boolean validateMobileNumActivity() {
        if ( isEmpty( binding.etMobileNum ) ){

            showAlert(Config.errorType,"Mobile Number Is Empty !!!");
            return false;

        }else if( binding.etMobileNum.getText().toString().length() < Config.MOBILE_NUMBER_LENGTH){

            showAlert(Config.errorType,"Mobile Number Length Not Valid !!!");
            return false;

        }else if( generateOtp && isEmpty(binding.etCnicNumber) ){

            showAlert(Config.errorType,"CNIC Number Is Empty !!!");
            return false;

        }else if ( generateOtp && binding.etCnicNumber.getText().toString().length() < Config.CNIC_LENGTH ){

            showAlert(Config.errorType,"CNIC Number Length Not Valid !!!");
            return false;

        }else if ( !wifiAvailable() ){

            showAlert(Config.errorType,"Network Is Not Available !!!");
            return false;

        }else{
            return true;
        }
    }


    /* Checking Ported network to true or false.  */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            showMobileInfoDialogue();
            isPortedMobileNetwork = true;
        } else {
            isPortedMobileNetwork = false;
        }
    }
}