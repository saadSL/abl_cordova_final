package com.unikrew.faceoff.ABLPlugin.ui.current_account.personal_details;

import android.content.Intent;
import android.graphics.Color;
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

import com.google.gson.Gson;
import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.TaxResidentDetailsBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.ConsumerListItemResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.tin_unavailability_reasons.TinUnavailabilityReasonsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.tin_unavailability_reasons.TinUnavailabilityReasonsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.tin_unavailability_reasons.TinUnavailabilityReasonsResponseData;
import com.unikrew.faceoff.ABLPlugin.model.current_account.countries.CountriesResponse;
import com.unikrew.faceoff.ABLPlugin.model.current_account.countries.CountriesResponseData;
import com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info.CurrentAccountTaxPostConsumerList;
import com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info.CurrentAccountTaxPostNationality;
import com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info.CurrentAccountTaxPostParams;
import com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info.CurrentAccountTaxPostResidentCountries;
import com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info.CurrentAccountTaxResponse;
import com.unikrew.faceoff.ABLPlugin.ui.current_account.setup_transaction.SetupTransactionActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_transaction.SelectCardActivity;
import com.unikrew.faceoff.ABLPlugin.ui.current_account.setup_transaction.SetupTransactionActivity;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;
import java.util.List;

public class PersonalDetailsActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private TaxResidentDetailsBinding taxResidentDetailsBinding;
    private PersonalDetailsViewModel personalDetailsViewModel;
    private TinUnavailabilityReasonsPostParams tinUnavailabilityReasonsPostParams;
    private TinUnavailabilityReasonsResponseData tinUnavailabilityReason = null;
    private CountriesResponseData selectedResidentCountry = null;
    private int isTaxResidentOutside = 0;
    private boolean tinNumberAvailable = false;
    private CurrentAccountTaxPostParams currentAccountTaxPostParams;
    private List<ConsumerListItemResponse> consumerList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setViewModel();
        setClicks();
        setLayout();
        observe();
        getSharedPrefData();
        getTinUnavailabilityReasons();
    }

    private void setLayout() {
        taxResidentDetailsBinding.steps.screenHeader.stepsHeading1.setText("Personal");
        taxResidentDetailsBinding.steps.screenHeader.stepsHeading2.setText("Details");
        setLogoLayout(taxResidentDetailsBinding.logoToolbar.tvDate);
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

    private void getTinUnavailabilityReasons() {
        setUnavailabilityReasonsPostParams();
        personalDetailsViewModel.getTinUnavailabilityReasons(tinUnavailabilityReasonsPostParams);
        showLoading();
    }
    private void setUnavailabilityReasonsPostParams() {
        tinUnavailabilityReasonsPostParams.getData().setCodeTypeId(Config.TIN_UNAVAILABILITY_REASONS);
    }

    private void setUnavailabilityReasons(ArrayList<TinUnavailabilityReasonsResponseData> tinUnavailabilityReasonsResponse) {
        ArrayList<TinUnavailabilityReasonsResponseData> _allReasons = new ArrayList<>();

        TinUnavailabilityReasonsResponseData data = new TinUnavailabilityReasonsResponseData();
        data.setDescription("");
        data.setId(0);
        data.setName("Choose Reasons");

        _allReasons.add(data);

        taxResidentDetailsBinding.spUnavailabilityReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0){
                    tinUnavailabilityReason = (TinUnavailabilityReasonsResponseData) adapterView.getSelectedItem();
                    if (tinUnavailabilityReason.getId() == 101202){
                        taxResidentDetailsBinding.llTinUnavailabilityReason.setVisibility(View.VISIBLE);
                    }else{
                        taxResidentDetailsBinding.llTinUnavailabilityReason.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        for (int i = 0 ; i < tinUnavailabilityReasonsResponse.size() ; i++){
            _allReasons.add(tinUnavailabilityReasonsResponse.get(i));
        }

        ArrayAdapter<TinUnavailabilityReasonsResponseData> dataAdapter = new ArrayAdapter<TinUnavailabilityReasonsResponseData>(this, android.R.layout.simple_spinner_item, _allReasons){
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
        taxResidentDetailsBinding.spUnavailabilityReason.setAdapter(dataAdapter);
    }


    private void setViewModel() {
        personalDetailsViewModel = new ViewModelProvider(this).get(PersonalDetailsViewModel.class);
        tinUnavailabilityReasonsPostParams = new TinUnavailabilityReasonsPostParams();
        currentAccountTaxPostParams = new CurrentAccountTaxPostParams();
    }

    private void observe() {
        personalDetailsViewModel.countriesResponse.observe(this, new Observer<CountriesResponse>() {
            @Override
            public void onChanged(CountriesResponse countriesResponse) {
                dismissLoading();
                setCountriesSpinner(countriesResponse);
            }
        });

        personalDetailsViewModel.tinUnavailabilityReasonsSuccessResponse.observe(this, new Observer<TinUnavailabilityReasonsResponse>() {
            @Override
            public void onChanged(TinUnavailabilityReasonsResponse tinUnavailabilityReasonsResponse) {
                dismissLoading();
                setUnavailabilityReasons(tinUnavailabilityReasonsResponse.getData());
            }
        });

        personalDetailsViewModel.currentAccountTaxSuccessResponse.observe(this, new Observer<CurrentAccountTaxResponse>() {
            @Override
            public void onChanged(CurrentAccountTaxResponse currentAccountTaxResponse) {
                dismissLoading();
                openTransactionActivity();
            }
        });

        personalDetailsViewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                dismissLoading();
                showAlert(Config.errorType,errMsg);
            }
        });
    }

    private void openTransactionActivity() {
        Intent intent = new Intent(this, SetupTransactionActivity.class);
    }

    private void setCountriesSpinner(CountriesResponse countriesResponse) {
        ArrayList<CountriesResponseData> _allCountries = new ArrayList<>();

        CountriesResponseData countriesResponseData = new CountriesResponseData();
        countriesResponseData.setCode("0");
        countriesResponseData.setId(0);
        countriesResponseData.setDialingCode("0");
        countriesResponseData.setDescription("Select Country");
        countriesResponseData.setName("Select Country");

        _allCountries.add(countriesResponseData);

        taxResidentDetailsBinding.spTaxResidentCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0){
                    selectedResidentCountry = (CountriesResponseData) adapterView.getSelectedItem();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (countriesResponse.getData().size() > 0){
            for (int i = 0 ; i < countriesResponse.getData().size() ; i++){
                _allCountries.add(countriesResponse.getData().get(i));
            }

            ArrayAdapter<CountriesResponseData> dataAdapter = new ArrayAdapter<CountriesResponseData>(this, android.R.layout.simple_spinner_item, _allCountries){
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
            taxResidentDetailsBinding.spTaxResidentCountry.setAdapter(dataAdapter);
        }
    }

    private void setClicks() {
        taxResidentDetailsBinding.swTaxResident.setOnCheckedChangeListener(this);
        taxResidentDetailsBinding.swTaxIdentityNumber.setOnCheckedChangeListener(this);
        taxResidentDetailsBinding.btnContainer.btnNext.setOnClickListener(this);

    }

    private void setBinding() {
        taxResidentDetailsBinding = TaxResidentDetailsBinding.inflate(getLayoutInflater());
        setContentView(taxResidentDetailsBinding.getRoot());
    }

    @Override
    public void onCheckedChanged(CompoundButton switchBtn, boolean isChecked) {
        switch (switchBtn.getId()){
            case R.id.sw_tax_resident:
                if (isChecked){
                    isTaxResidentOutside = 1;
                    openCountrySelectionLayout();
                    getCountries();
                }else{
                    isTaxResidentOutside = 0;
                    closeCountrySelectionLayout();
                }
                break;
            case R.id.sw_tax_identity_number:
                if (isChecked){
                    tinNumberAvailable = true;
                    openTinAvailableLayout();
                }else{
                    tinNumberAvailable = false;
                    openTinUnAvailableLayout();
                }
                break;
        }
    }

    private void getCountries() {
        personalDetailsViewModel.getCountries();
        showLoading();
    }

    private void openTinUnAvailableLayout() {
        taxResidentDetailsBinding.llTINAvailable.setVisibility(View.GONE);
        taxResidentDetailsBinding.llTINUnavailable.setVisibility(View.VISIBLE);
    }

    private void openTinAvailableLayout() {
        taxResidentDetailsBinding.llTINAvailable.setVisibility(View.VISIBLE);
        taxResidentDetailsBinding.llTINUnavailable.setVisibility(View.GONE);
    }

    private void closeCountrySelectionLayout() {
        taxResidentDetailsBinding.spTaxResidentCountry.setVisibility(View.GONE);
    }

    private void openCountrySelectionLayout() {
        taxResidentDetailsBinding.spTaxResidentCountry.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                postPersonalDetails();
                break;
        }
    }

    private void postPersonalDetails() {
        if ( isValid() ){
            setPersonalDetailsPostParams();
            personalDetailsViewModel.postPersonalDetails(currentAccountTaxPostParams,getStringFromPref(Config.ACCESS_TOKEN));
            showLoading();
        }
    }

    private void setPersonalDetailsPostParams() {
        ArrayList<CurrentAccountTaxPostConsumerList> currentAccountConsumerList = new ArrayList<>();

        CurrentAccountTaxPostConsumerList currentAccountTaxPostConsumerList = new CurrentAccountTaxPostConsumerList();

        currentAccountTaxPostConsumerList.setRdaCustomerProfileId(consumerList.get(0).getRdaCustomerProfileId());
        currentAccountTaxPostConsumerList.setRdaCustomerAccInfoId(consumerList.get(0).getAccountInformation().getRdaCustomerAccInfoId());
        currentAccountTaxPostConsumerList.setCustomerTypeId(consumerList.get(0).getCustomerTypeId());
        currentAccountTaxPostConsumerList.setFullName(consumerList.get(0).getFullName());
        currentAccountTaxPostConsumerList.setFatherHusbandName(consumerList.get(0).getFatherHusbandName());
        currentAccountTaxPostConsumerList.setMotherMaidenName(consumerList.get(0).getMotherMaidenName());
        currentAccountTaxPostConsumerList.setCityOfBirth(consumerList.get(0).getCityOfBirth());
        currentAccountTaxPostConsumerList.setPrimary(consumerList.get(0).isPrimary());
        currentAccountTaxPostConsumerList.setEmailAddress(consumerList.get(0).getEmailAddress());
        currentAccountTaxPostConsumerList.setTaxResidentInd(isTaxResidentOutside);
        currentAccountTaxPostConsumerList.setOccupationId(consumerList.get(0).getOccupationId());
        currentAccountTaxPostConsumerList.setProfessionId(consumerList.get(0).getProfessionId());
        currentAccountTaxPostConsumerList.setCustomerNtn(consumerList.get(0).getCustomerNtn());
        if (selectedResidentCountry!=null){
            currentAccountTaxPostConsumerList.setRdaCustomerCountryId(selectedResidentCountry.getId());
        }

        currentAccountTaxPostConsumerList.setKinName(consumerList.get(0).getKinName());
        currentAccountTaxPostConsumerList.setKinCnic(consumerList.get(0).getKinCnic());
        currentAccountTaxPostConsumerList.setKinMobile(consumerList.get(0).getKinMobile());
        currentAccountTaxPostConsumerList.setNationalityTypeId(consumerList.get(0).getNationalityTypeId());
        currentAccountTaxPostConsumerList.setNationalities(null);
        currentAccountTaxPostConsumerList.setResidentCountries(getResidentCountries());

        currentAccountConsumerList.add(currentAccountTaxPostConsumerList);

        currentAccountTaxPostParams.getData().setConsumerList(currentAccountConsumerList);
    }

    private ArrayList<CurrentAccountTaxPostResidentCountries> getResidentCountries() {
        ArrayList<CurrentAccountTaxPostResidentCountries> residentCountries = new ArrayList<>();

        CurrentAccountTaxPostResidentCountries postResidentCountries = new CurrentAccountTaxPostResidentCountries();
        if (selectedResidentCountry!=null){
            postResidentCountries.setCountryOfTaxResidenceId(selectedResidentCountry.getId());
        }

        postResidentCountries.setRdaCustomerId(consumerList.get(0).getRdaCustomerProfileId());
        if (tinNumberAvailable){
            postResidentCountries.setTaxIdentityNo(Integer.parseInt(taxResidentDetailsBinding.etTaxIdentityNumber.getText().toString()));
        }
        postResidentCountries.setTinReasonId(tinUnavailabilityReason.getId());
        postResidentCountries.setExplanation(taxResidentDetailsBinding.etTinUnavailabilityReason.getText().toString());

        residentCountries.add(postResidentCountries);

        return residentCountries;
    }

    private boolean isValid() {
        if (isTaxResidentOutside == 1){
            if (selectedResidentCountry == null){
                showAlert(Config.errorType,"Please Select One Country !!!");
                return false;
            }
        }
        if (tinNumberAvailable){
            if (isEmpty(taxResidentDetailsBinding.etTaxIdentityNumber)){
                showAlert(Config.errorType,"Please Enter Tax Identity Number !!!");
                return false;
            }
        }
        if (!tinNumberAvailable){
            if (tinUnavailabilityReason == null){
                showAlert(Config.errorType,"Please Select A Reason For Tax Identity Unavailability !!!");
                return false;
            }

            if ( tinUnavailabilityReason.getId() == 101202 ){
                if (isEmpty(taxResidentDetailsBinding.etTinUnavailabilityReason)){
                    showAlert(Config.errorType,"Please Enter A Reason For Tax Identity Unavailability !!!");
                    return false;
                }
            }
        }
        return true;
    }


}
