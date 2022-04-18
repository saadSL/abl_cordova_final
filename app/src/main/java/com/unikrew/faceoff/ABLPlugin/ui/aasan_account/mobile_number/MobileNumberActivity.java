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

    private MobileNumberAvailabilityBinding mobileNumberAvailabilityBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setLayout();
        setListeners();
        setViewModel();
        observeData();
    }

    private void setLayout() {
        mobileNumberAvailabilityBinding.screenHeader.stepsHeading1.setText("Let's");
        mobileNumberAvailabilityBinding.screenHeader.stepsHeading2.setText("Get Started");
        mobileNumberAvailabilityBinding.btnContainer.btBack.setVisibility(View.GONE);
    }

    private void setBinding() {
        mobileNumberAvailabilityBinding = MobileNumberAvailabilityBinding.inflate(getLayoutInflater());
        setContentView(mobileNumberAvailabilityBinding.getRoot());
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
        mobileNumberAvailabilityBinding.llCnicUploadFront.setVisibility(View.VISIBLE);
        mobileNumberAvailabilityBinding.llCnicUploadBack.setVisibility(View.VISIBLE);
//        Intent intent = new Intent(this, CnicUploadActivity.class);
//
//        intent.putExtra(Config.MOBILE_NUMBER, mobileNumberAvailabilityBinding.etMobileNum.getText().toString());
//        intent.putExtra(Config.PORTED_MOBILE_NETWORK, isPortedMobileNetwork);
//
//        startActivity(intent);
    }

    /* The method below will work when mobile number is already registered with cnic. */
    private void showCnic(ViewAppsGenerateOtpResponse response) {
        mobileNumberAvailabilityBinding.llCnic.setVisibility(View.VISIBLE);

        mobileNumberAvailabilityBinding.etCnicNumber.setText(response.getData().getIdNumber().toString());
        generateOtp = true;

    }


    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(MobileNumberViewModel.class);
        postParams = new ViewAppsGenerateOtpPostParams();
    }


    private void setListeners() {
        /* Mobile Number Availability */
        mobileNumberAvailabilityBinding.portedMobileNetworkSwitch.setOnCheckedChangeListener(this);
        mobileNumberAvailabilityBinding.btnContainer.btnNext.setOnClickListener(this);
        mobileNumberAvailabilityBinding.btnContainer.btBack.setOnClickListener(this);
        mobileNumberAvailabilityBinding.llCnicUploadFront.setOnClickListener(this);
        mobileNumberAvailabilityBinding.llCnicUploadBack.setOnClickListener(this);
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
            case R.id.ll_cnic_upload_front:
                getCnicFrontImage();
                break;
            case R.id.ll_cnic_upload_back:
                getCnicBackImage();
                break;
        }
    }

    private void getCnicBackImage() {
        mobileNumberAvailabilityBinding.imgCnicBackSmall.setVisibility(View.VISIBLE);
        mobileNumberAvailabilityBinding.llCnicBack.setVisibility(View.VISIBLE);
    }

    private void getCnicFrontImage() {
        mobileNumberAvailabilityBinding.imgCnicFrontSmall.setVisibility(View.VISIBLE);
        mobileNumberAvailabilityBinding.llCnicFront.setVisibility(View.VISIBLE);
    }

    private void viewAppsGenerateOtpPostData() {
        viewModel.viewAppsGenerateOtpPostData(postParams);
        showLoading();
    }

    private void setPostParams() {
        postParams.getData().setCustomerTypeId(Config.CUSTOMER_TYPE_ID);
        postParams.getData().setMobileNo(mobileNumberAvailabilityBinding.etMobileNum.getText().toString());
        postParams.getData().setGenerateOtp(generateOtp);

        if ( generateOtp ){
            postParams.getData().setIdNumber(mobileNumberAvailabilityBinding.etCnicNumber.getText().toString());
            postParams.getData().setPortedMobileNetwork(isPortedMobileNetwork);
        }

    }

    private Boolean validateMobileNumActivity() {
        if ( isEmpty( mobileNumberAvailabilityBinding.etMobileNum ) ){

            showAlert(Config.errorType,"Mobile Number Is Empty !!!");
            return false;

        }else if( mobileNumberAvailabilityBinding.etMobileNum.getText().toString().length() < Config.MOBILE_NUMBER_LENGTH){

            showAlert(Config.errorType,"Mobile Number Length Not Valid !!!");
            return false;

        }else if( generateOtp && isEmpty(mobileNumberAvailabilityBinding.etCnicNumber) ){

            showAlert(Config.errorType,"CNIC Number Is Empty !!!");
            return false;

        }else if ( generateOtp && mobileNumberAvailabilityBinding.etCnicNumber.getText().toString().length() < Config.CNIC_LENGTH ){

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

    @Override
    protected void onRestart() {
        super.onRestart();
        setLayout();
    }
}