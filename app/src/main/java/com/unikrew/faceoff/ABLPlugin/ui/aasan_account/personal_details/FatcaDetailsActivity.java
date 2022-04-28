package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.FreelancerFatcaInfoBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.fatca_details.FatcaDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.fatca_details.FatcaDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.fatca_details.FatcaReqPostDtoList;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;

public class FatcaDetailsActivity extends BaseActivity {

    private int attorneyInd = 0;
    private int birthUSInd = 0;
    private int careAddrInd = 0;
    private int greenCardHolderInd = 0;
    private int usCitizenInd = 0;
    private int usMailAddrInd = 0;
    private int usTaxResidentInd = 0;

    private Boolean IS_RESUMED;

    private FreelancerFatcaInfoBinding freelancerFatcaInfoBinding;
    private FatcaDetailsPostParams fatcaDetailsPostParams;
    private PersonalDetailsViewModel personalDetailsViewModel;
    private RegisterVerifyOtpResponse registerVerifyOtpResponse;
    private GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setLayout();
        getSharedPrefData();
        setClicks();
        setViewModel();
        observe();
    }

    private void observe() {
        personalDetailsViewModel.fatcaDetailsSuccessResponse.observe(this, new Observer<FatcaDetailsResponse>() {
            @Override
            public void onChanged(FatcaDetailsResponse fatcaDetailsResponse) {
                openPersonalActivityOne();
                dismissLoading();
            }
        });

        personalDetailsViewModel.fatcaDetailsErrorResponse.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType,errMsg);
                dismissLoading();
            }
        });
    }

    private void openPersonalActivityOne() {
        Intent intent = new Intent(this,PersonalDetailsOneActivity.class);
        startActivity(intent);
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

    private void setViewModel() {
        personalDetailsViewModel = new ViewModelProvider(this).get(PersonalDetailsViewModel.class);
        fatcaDetailsPostParams = new FatcaDetailsPostParams();
    }

    private void setClicks() {
        freelancerFatcaInfoBinding.swUsTaxResident.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    usTaxResidentInd = 1;
                }else{
                    usTaxResidentInd = 0;
                }
            }
        });

        freelancerFatcaInfoBinding.swUsCitizen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    usCitizenInd = 1;
                }else{
                    usCitizenInd = 0;
                }
            }
        });

        freelancerFatcaInfoBinding.swMailAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    usMailAddrInd = 1;
                }else{
                    usMailAddrInd = 0;
                }
            }
        });

        freelancerFatcaInfoBinding.swUsPlaceOfBirth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    birthUSInd = 1;
                }else{
                    birthUSInd = 0;
                }
            }
        });

        freelancerFatcaInfoBinding.swStandingInstruction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    careAddrInd = 1;
                }else{
                    careAddrInd = 0;
                }
            }
        });

        freelancerFatcaInfoBinding.swGreenCardHolder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    greenCardHolderInd = 1;
                }else{
                    greenCardHolderInd = 0;
                }
            }
        });

        freelancerFatcaInfoBinding.swIssuedAttorney.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    attorneyInd = 1;
                }else{
                    attorneyInd = 0;
                }
            }
        });

        freelancerFatcaInfoBinding.btnContainer.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitFatcaDetails();
            }
        });

        freelancerFatcaInfoBinding.btnContainer.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void submitFatcaDetails() {
        setFatcaDetailsPostParams();
        personalDetailsViewModel.submitFatcaDetails(fatcaDetailsPostParams,getStringFromPref(Config.ACCESS_TOKEN));
        showLoading();
    }

    private void setFatcaDetailsPostParams() {
        fatcaDetailsPostParams.getData().setFatcaReqDtoList(getFatcaReqDtoList());
    }

    private ArrayList<FatcaReqPostDtoList> getFatcaReqDtoList() {
        ArrayList<FatcaReqPostDtoList> fatcaReqPostDtoLists = new ArrayList<>();
        FatcaReqPostDtoList fatcaReqPostDtoListObject = new FatcaReqPostDtoList();
        fatcaReqPostDtoListObject.setAttorneyInd(attorneyInd);
        fatcaReqPostDtoListObject.setBirthUSInd(birthUSInd);
        fatcaReqPostDtoListObject.setCareAddrInd(careAddrInd);
        fatcaReqPostDtoListObject.setGreenCardHolderInd(greenCardHolderInd);
        if (IS_RESUMED){
            fatcaReqPostDtoListObject.setRdaCustomerId(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getRdaCustomerProfileId());
        }else{
            fatcaReqPostDtoListObject.setRdaCustomerId(registerVerifyOtpResponse.getData().getConsumerList().get(0).getRdaCustomerProfileId());
        }

        fatcaReqPostDtoListObject.setUsCitizenInd(usCitizenInd);
        fatcaReqPostDtoListObject.setUsMailAddrInd(usMailAddrInd);
        fatcaReqPostDtoListObject.setUsTaxResidentInd(usTaxResidentInd);

        fatcaReqPostDtoLists.add(fatcaReqPostDtoListObject);

        return fatcaReqPostDtoLists;
    }

    private void setLayout() {
        freelancerFatcaInfoBinding.steps.screenHeader.stepsHeading1.setText("Details");
        freelancerFatcaInfoBinding.steps.screenHeader.stepsHeading2.setText("FATCA");
        freelancerFatcaInfoBinding.steps.step1.setBackground(this.getDrawable(R.color.custom_blue));
        freelancerFatcaInfoBinding.steps.step2.setBackground(this.getDrawable(R.color.custom_blue));
    }

    private void setBinding() {
        freelancerFatcaInfoBinding = FreelancerFatcaInfoBinding.inflate(getLayoutInflater());
        setContentView(freelancerFatcaInfoBinding.getRoot());
    }
}
