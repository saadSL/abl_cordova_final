package com.unikrew.faceoff.ABLPlugin.ui.current_account.organizational_details;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.OrganizationDetailsBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.freelancer_tax_info.FreelancerTaxPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycPostData;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkResponseData;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AccountInformationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.ConsumerListItemResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.tin_unavailability_reasons.TinUnavailabilityReasonsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodePostParams;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponse;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponseData;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.additional_applicant.AdditionalApplicantActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details.PersonalDetailsViewModel;
import com.unikrew.faceoff.ABLPlugin.ui.current_account.personal_details.PersonalDetailsActivity;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;
import java.util.List;

public class OrganizationDetailsActivity extends BaseActivity {

    private PersonalDetailsViewModel personalDetailsViewModel;
    private OrganizationDetailsBinding organizationDetailsBinding;
    private List<ConsumerListItemResponse> consumerList;
    private ArrayList<LookUpCodeResponseData> occupationArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setLayout();
        setViewModel();
        getSharedPrefData();
        setLogoLayout(organizationDetailsBinding.layoutLogo.tvDate);
        setObservers();
        clicks();
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

    private void clicks() {
        organizationDetailsBinding.btnContainer.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        organizationDetailsBinding.btnContainer.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidations();
            }
        });
    }

    private void checkValidations() {
        if (organizationDetailsBinding.etSalary.getText().toString().isEmpty()) {
            showAlert(Config.errorType, "Please enter your salary to continue");
        } else {
            postKyc();
        }
    }

    private void postKyc() {
        showLoading();
        personalDetailsViewModel.saveKyc(getKycParams(), getStringFromPref(Config.ACCESS_TOKEN));
    }

    private SaveKycPostParams getKycParams() {
        SaveKycPostParams saveKycPostParams = new SaveKycPostParams();
        SaveKycPostData saveKycPostData = new SaveKycPostData();
        for (int i = 0; i < consumerList.size(); i++) {
            AccountInformationResponse accountInformation = consumerList.get(i).getAccountInformation();
            saveKycPostData.setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
            //used rdaCustomerProfileId instead of rdaCustomerId since both are same
            saveKycPostData.setRdaCustomerProfileId(accountInformation.getRdaCustomerId());
            saveKycPostData.setOccupationId(String.valueOf(occupationArray.get(organizationDetailsBinding.spOccupation.getSelectedItemPosition()).getId()));
            saveKycPostData.setAverageMonthlySalary(Long.valueOf(organizationDetailsBinding.etSalary.getText().toString().trim()));
            if (!organizationDetailsBinding.etNtn.getText().toString().isEmpty()){
                saveKycPostData.setCustomerNtn(organizationDetailsBinding.etNtn.getText().toString().trim());
            }
            saveKycPostParams.getData().add(saveKycPostData);
        }
        return saveKycPostParams;
    }


    private void setObservers() {
        personalDetailsViewModel.occupationLiveData.observe(this, new Observer<LookUpCodeResponse>() {
            @Override
            public void onChanged(LookUpCodeResponse mobileNetworkResponse) {
                occupationArray = mobileNetworkResponse.getData();
                dismissLoading();
                setSpinner();
            }
        });

        personalDetailsViewModel.saveKycResponseMutableLiveData.observe(this, new Observer<SaveKycResponse>() {
            @Override
            public void onChanged(SaveKycResponse saveKycResponse) {
                dismissLoading();
                goToNext();
            }
        });

        personalDetailsViewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                dismissLoading();
                showAlert(Config.errorType, errMsg);
            }
        });

    }

    private void goToNext() {
        openActivity(PersonalDetailsActivity.class);
    }


    private void setSpinner() {
        List<String> _allItemsArray = new ArrayList<>();
        if (occupationArray.size() > 0) {
            // Spinner click listener
            // Spinner Drop down elements
            for (int i = 0; i < occupationArray.size(); i++) {
                _allItemsArray.add(occupationArray.get(i).getName());
            }

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, _allItemsArray);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            organizationDetailsBinding.spOccupation.setAdapter(dataAdapter);
        }
    }

    private void setViewModel() {
        personalDetailsViewModel = new ViewModelProvider(this).get(PersonalDetailsViewModel.class);
        getOccupation();
    }

    private void getOccupation() {
        showLoading();
        LookUpCodePostParams mobileNetworkPostParams = new LookUpCodePostParams();
        mobileNetworkPostParams.getData().codeTypeId = Config.OCCUPATION_CODE;
        personalDetailsViewModel.getOccupation(mobileNetworkPostParams);
    }

    private void setBinding() {
        organizationDetailsBinding = OrganizationDetailsBinding.inflate(getLayoutInflater());
        setContentView(organizationDetailsBinding.getRoot());
    }

    private void setLayout() {
        organizationDetailsBinding.steps.screenHeader.stepsHeading1.setText("Organization");
        organizationDetailsBinding.steps.screenHeader.stepsHeading2.setText("Details");
        organizationDetailsBinding.steps.step1.setBackground(this.getDrawable(R.color.custom_blue));
        organizationDetailsBinding.steps.step2.setBackground(this.getDrawable(R.color.custom_blue));
        organizationDetailsBinding.steps.step3.setBackground(this.getDrawable(R.color.light_gray));
        organizationDetailsBinding.steps.step4.setBackground(this.getDrawable(R.color.light_gray));
    }
}
