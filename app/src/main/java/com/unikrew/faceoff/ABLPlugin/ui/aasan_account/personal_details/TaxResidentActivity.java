package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.FreelancerTaxResidentInfoBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.freelancer_tax_info.FreelancerTaxPostConsumerList;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.freelancer_tax_info.FreelancerTaxPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.freelancer_tax_info.FreelancerTaxPostResidentCountries;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.freelancer_tax_info.FreelancerTaxResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.tin_unavailability_reasons.TinUnavailabilityReasonsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.tin_unavailability_reasons.TinUnavailabilityReasonsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.tin_unavailability_reasons.TinUnavailabilityReasonsResponseData;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodePostParams;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponse;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponseData;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;

public class TaxResidentActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private FreelancerTaxResidentInfoBinding freelancerTaxResidentInfoBinding;

    private int taxResidentOutsidePakistan = 0;
    private Boolean taxIdentificationNumber = false;

    private FreelancerTaxPostParams freelancerTaxPostParams;
    private LookUpCodePostParams tinUnavailabilityReasonsPostParams;
    private PersonalDetailsViewModel personalDetailsViewModel;

    private TinUnavailabilityReasonsResponseData tinUnavailabilityReason;

    private Boolean IS_RESUMED;
    private RegisterVerifyOtpResponse registerVerifyOtpResponse;
    private GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setLayout();
        getSharedPrefData();
        setViewModel();
        setClicks();
        getTinUnavailabilityReasons();
        observe();
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

    private void observe() {
        personalDetailsViewModel.tinUnavailabilityReasonsSuccessResponse.observe(this, new Observer<LookUpCodeResponse>() {
            @Override
            public void onChanged(LookUpCodeResponse tinUnavailabilityReasonsResponse) {
                setUnavailabilityReasons(tinUnavailabilityReasonsResponse.getData());
                dismissLoading();
            }
        });

        personalDetailsViewModel.tinUnavailabilityReasonsError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType,errMsg);
                dismissLoading();
            }
        });

        personalDetailsViewModel.freelancerTaxSuccessResponse.observe(this, new Observer<FreelancerTaxResponse>() {
            @Override
            public void onChanged(FreelancerTaxResponse freelancerTaxResponse) {
                dismissLoading();
                moveNext();
            }
        });

        personalDetailsViewModel.freelancerTaxErrorResponse.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType,errMsg);
                dismissLoading();
            }
        });

    }

    private void moveNext() {
        if (getIntFromPref(Config.ACCOUNT_VARIANT_ID)== Config.REMITTANCE_ACCOUNT){
            openActivity(PersonalDetailsOneActivity.class);
        }else {
            openActivity(FatcaDetailsActivity.class);
        }
    }

    private void setViewModel() {
        personalDetailsViewModel = new ViewModelProvider(this).get(PersonalDetailsViewModel.class);
        tinUnavailabilityReasonsPostParams = new LookUpCodePostParams();
        freelancerTaxPostParams = new FreelancerTaxPostParams();
    }

    private void getTinUnavailabilityReasons() {
        setUnavailabilityReasonsPostParams();
        personalDetailsViewModel.getTinUnavailabilityReasons(tinUnavailabilityReasonsPostParams);
        showLoading();
    }

    private void setUnavailabilityReasonsPostParams() {
        tinUnavailabilityReasonsPostParams.getData().setCodeTypeId(Config.TIN_UNAVAILABILITY_REASONS);
    }

    private void setLayout() {
        setLogoLayout(freelancerTaxResidentInfoBinding.logoToolbar.tvDate);
        freelancerTaxResidentInfoBinding.steps.screenHeader.stepsHeading1.setText("Personal");
        freelancerTaxResidentInfoBinding.steps.screenHeader.stepsHeading2.setText("Details");
        freelancerTaxResidentInfoBinding.steps.step1.setBackground(this.getDrawable(R.color.custom_blue));
        freelancerTaxResidentInfoBinding.steps.step2.setBackground(this.getDrawable(R.color.custom_blue));
    }

    private void setClicks() {
        freelancerTaxResidentInfoBinding.swTaxIdentityNumber.setOnCheckedChangeListener(this);
        freelancerTaxResidentInfoBinding.btnYesTaxResident.setOnClickListener(this);
        freelancerTaxResidentInfoBinding.btnNoTaxResident.setOnClickListener(this);
        freelancerTaxResidentInfoBinding.btnContainer.btnNext.setOnClickListener(this);
        freelancerTaxResidentInfoBinding.btnContainer.btBack.setOnClickListener(this);
    }

    private void setBinding() {
        freelancerTaxResidentInfoBinding = FreelancerTaxResidentInfoBinding.inflate(getLayoutInflater());
        setContentView(freelancerTaxResidentInfoBinding.getRoot());
    }

    @Override
    public void onCheckedChanged(CompoundButton switchButton, boolean isChecked) {
        switch (switchButton.getId()){
            case R.id.sw_tax_identity_number:
                if (isChecked){
                    taxIdentificationNumber = true;
                    freelancerTaxResidentInfoBinding.llTINAvailable.setVisibility(View.VISIBLE);
                    freelancerTaxResidentInfoBinding.llTINUnavailable.setVisibility(View.GONE);
                }else{
                    taxIdentificationNumber = false;
                    freelancerTaxResidentInfoBinding.llTINUnavailable.setVisibility(View.VISIBLE);
                    freelancerTaxResidentInfoBinding.llTINAvailable.setVisibility(View.GONE);

                }
                break;
        }
    }

    private void setUnavailabilityReasons(ArrayList<LookUpCodeResponseData> tinUnavailabilityReasonsResponse) {
        ArrayList<LookUpCodeResponseData> _allReasons = new ArrayList<>();

        LookUpCodeResponseData data = new LookUpCodeResponseData();
        data.setDescription("");
        data.setId(0);
        data.setName("Choose Reasons");

        _allReasons.add(data);

        freelancerTaxResidentInfoBinding.spUnavailabilityReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tinUnavailabilityReason = (TinUnavailabilityReasonsResponseData) adapterView.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
            for (int i = 0 ; i < tinUnavailabilityReasonsResponse.size() ; i++){
                _allReasons.add(tinUnavailabilityReasonsResponse.get(i));
            }

            ArrayAdapter<LookUpCodeResponseData> dataAdapter = new ArrayAdapter<LookUpCodeResponseData>(this, android.R.layout.simple_spinner_item, _allReasons){
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0){
                        return false;
                    }else{
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView textView = (TextView) view;
                    if (position == 0){
                        textView.setTextColor(Color.GRAY);
                    }else{
                        textView.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            freelancerTaxResidentInfoBinding.spUnavailabilityReason.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_yes_tax_resident:
                freelancerTaxResidentInfoBinding.btnYesTaxResident.setBackground(this.getDrawable(R.drawable.button_blue_shape));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    freelancerTaxResidentInfoBinding.btnYesTaxResident.setTextColor(this.getColor(R.color.white));
                    freelancerTaxResidentInfoBinding.btnNoTaxResident.setTextColor(this.getColor(R.color.custom_blue));
                }
                freelancerTaxResidentInfoBinding.btnNoTaxResident.setBackground(this.getDrawable(R.drawable.transparent_bg));
                taxResidentOutsidePakistan = 1;
                break;
            case R.id.btn_no_tax_resident:
                freelancerTaxResidentInfoBinding.btnNoTaxResident.setBackground(this.getDrawable(R.drawable.button_blue_shape));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    freelancerTaxResidentInfoBinding.btnNoTaxResident.setTextColor(this.getColor(R.color.white));
                    freelancerTaxResidentInfoBinding.btnYesTaxResident.setTextColor(this.getColor(R.color.custom_blue));

                }
                freelancerTaxResidentInfoBinding.btnYesTaxResident.setBackground(this.getDrawable(R.drawable.transparent_bg));
                taxResidentOutsidePakistan = 0;
                break;

            case R.id.btn_next:
                submitTaxInfo();
                break;

            case R.id.bt_back:
                finish();
                break;
        }
    }

    private void submitTaxInfo() {
        if (taxIdentificationNumber){
            if (isEmpty(freelancerTaxResidentInfoBinding.etTaxIdentityNumber)){
                showAlert(Config.errorType,"Please Enter Tax Identification Number !!!");
            }
        }else if (!taxIdentificationNumber && (tinUnavailabilityReason == null || tinUnavailabilityReason.getId() == 0)){
                showAlert(Config.errorType,"Please Select TIN unavailability reason !!!");
        }else{
            setFreelancerTaxPostParams();
            personalDetailsViewModel.submitFreelancerTaxDetails(freelancerTaxPostParams,getStringFromPref(Config.ACCESS_TOKEN));
            showLoading();
        }
    }

    private void setFreelancerTaxPostParams() {
        freelancerTaxPostParams.getData().setConsumerList(getFreelancerConsumerList());
    }

    private ArrayList<FreelancerTaxPostConsumerList> getFreelancerConsumerList() {
        FreelancerTaxPostConsumerList freelancerTaxPostConsumerList = new FreelancerTaxPostConsumerList();
        ArrayList<FreelancerTaxPostConsumerList> consumerLists = new ArrayList<FreelancerTaxPostConsumerList>();
        if (IS_RESUMED){
            freelancerTaxPostConsumerList.setCustomerTypeId(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getCustomerTypeId());
            freelancerTaxPostConsumerList.setFatherHusbandName(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getFatherHusbandName());
            freelancerTaxPostConsumerList.setFullName(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getFullName());
            freelancerTaxPostConsumerList.setPrimary(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).isPrimary());
            freelancerTaxPostConsumerList.setMotherMaidenName(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getMotherMaidenName());
            freelancerTaxPostConsumerList.setOccupationId(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getOccupationId());
            freelancerTaxPostConsumerList.setProfessionId(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getProfessionId());
            freelancerTaxPostConsumerList.setRdaCustomerAccInfoId(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getAccountInformation().getRdaCustomerAccInfoId());
            freelancerTaxPostConsumerList.setRdaCustomerProfileId(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getRdaCustomerProfileId());

        }else{
            freelancerTaxPostConsumerList.setCustomerTypeId(registerVerifyOtpResponse.getData().getConsumerList().get(0).getCustomerTypeId());
            freelancerTaxPostConsumerList.setFatherHusbandName(registerVerifyOtpResponse.getData().getConsumerList().get(0).getFatherHusbandName());
            freelancerTaxPostConsumerList.setFullName(registerVerifyOtpResponse.getData().getConsumerList().get(0).getFullName());
            freelancerTaxPostConsumerList.setPrimary(registerVerifyOtpResponse.getData().getConsumerList().get(0).isPrimary());
            freelancerTaxPostConsumerList.setMotherMaidenName(registerVerifyOtpResponse.getData().getConsumerList().get(0).getMotherMaidenName());
            freelancerTaxPostConsumerList.setOccupationId(registerVerifyOtpResponse.getData().getConsumerList().get(0).getOccupationId());
            freelancerTaxPostConsumerList.setProfessionId(registerVerifyOtpResponse.getData().getConsumerList().get(0).getProfessionId());
            freelancerTaxPostConsumerList.setRdaCustomerAccInfoId(registerVerifyOtpResponse.getData().getConsumerList().get(0).getAccountInformation().getRdaCustomerAccInfoId());
            freelancerTaxPostConsumerList.setRdaCustomerProfileId(registerVerifyOtpResponse.getData().getConsumerList().get(0).getRdaCustomerProfileId());
        }
        freelancerTaxPostConsumerList.setResidentCountries(getResidentCountries());
        consumerLists.add(freelancerTaxPostConsumerList);
        return consumerLists;
    }

    private ArrayList<FreelancerTaxPostResidentCountries> getResidentCountries() {
        ArrayList<FreelancerTaxPostResidentCountries> residentCountries = new ArrayList<FreelancerTaxPostResidentCountries>();

        FreelancerTaxPostResidentCountries freelancerTaxPostResidentCountries = new FreelancerTaxPostResidentCountries();
        freelancerTaxPostResidentCountries.setTaxResidentInd(taxResidentOutsidePakistan);
        freelancerTaxPostResidentCountries.setExplanation(null);
        if (taxIdentificationNumber){
            freelancerTaxPostResidentCountries.setTaxIdentityNo(Integer.parseInt(freelancerTaxResidentInfoBinding.etTaxIdentityNumber.getText().toString()));
        }else{
            freelancerTaxPostResidentCountries.setTinReasonId(tinUnavailabilityReason.getId());
        }
        if (IS_RESUMED){
            freelancerTaxPostResidentCountries.setRdaCustomerId(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getRdaCustomerProfileId());
        }else{
            freelancerTaxPostResidentCountries.setRdaCustomerId(registerVerifyOtpResponse.getData().getConsumerList().get(0).getRdaCustomerProfileId());
        }
        residentCountries.add(freelancerTaxPostResidentCountries);
        return residentCountries;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setLayout();
    }
}
