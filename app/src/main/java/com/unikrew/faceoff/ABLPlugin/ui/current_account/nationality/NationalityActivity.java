package com.unikrew.faceoff.ABLPlugin.ui.current_account.nationality;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivitySelectNationalityBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.ConsumerListItemResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.current_account.countries.CountriesResponse;
import com.unikrew.faceoff.ABLPlugin.model.current_account.countries.CountriesResponseData;
import com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info.CurrentAccountTaxPostConsumerList;
import com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info.CurrentAccountTaxPostNationality;
import com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info.CurrentAccountTaxPostParams;
import com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info.CurrentAccountTaxResponse;
import com.unikrew.faceoff.ABLPlugin.ui.current_account.organizational_details.OrganizationDetailsActivity;
import com.unikrew.faceoff.ABLPlugin.ui.current_account.personal_details.PersonalDetailsViewModel;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;
import java.util.List;

public class NationalityActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ActivitySelectNationalityBinding nationalityBinding;
    private NationalityAdapter nationalityAdapter;
    private PersonalDetailsViewModel personalDetailsViewModel;
    private ArrayList<CountriesResponseData> countriesArray = new ArrayList<>();
    private ArrayList<CurrentAccountTaxPostNationality> nationalitiesArray;
    private int rdaCustomerId;
    private List<ConsumerListItemResponse> consumerList;
    private int NATIONALITY_TYPE_ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setViewModel();
        observe();
        setClicks();
        setLayout();
        getSharedPrefData();
        setLogoLayout(nationalityBinding.logoToolbar.tvDate);

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
        rdaCustomerId = consumerList.get(0).getRdaCustomerProfileId();
    }

    private void observe() {
        personalDetailsViewModel.countriesResponse.observe(this, new Observer<CountriesResponse>() {
            @Override
            public void onChanged(CountriesResponse countriesResponse) {
                dismissLoading();
                countriesArray.addAll(countriesResponse.getData());
                addOneNationalityItem();
                setNationalityAdapter();
            }
        });

        personalDetailsViewModel.currentAccountTaxSuccessResponse.observe(this, new Observer<CurrentAccountTaxResponse>() {
            @Override
            public void onChanged(CurrentAccountTaxResponse currentAccountTaxResponse) {
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
        openActivity(OrganizationDetailsActivity.class);
    }


    private void setViewModel() {
        nationalitiesArray = new ArrayList<>();
        personalDetailsViewModel = new ViewModelProvider(this).get(PersonalDetailsViewModel.class);
        personalDetailsViewModel.getCountries();
        showLoading();
    }

    private void setLayout() {
        nationalityBinding.steps.screenHeader.stepsHeading1.setText("Your");
        nationalityBinding.steps.screenHeader.stepsHeading2.setText("Details");
        nationalityBinding.steps.step1.setBackground(this.getDrawable(R.color.custom_blue));
        nationalityBinding.steps.step2.setBackground(this.getDrawable(R.color.custom_blue));
        nationalityBinding.steps.step3.setBackground(this.getDrawable(R.color.light_gray));
        nationalityBinding.steps.step4.setBackground(this.getDrawable(R.color.light_gray));
    }


    private void setClicks() {
        nationalityBinding.swNationality.setOnCheckedChangeListener(this);
        nationalityBinding.btnContainer.btnNext.setOnClickListener(this);
        nationalityBinding.btnContainer.btBack.setOnClickListener(this);
    }

    private void setNationalityAdapter() {
        nationalityAdapter = new NationalityAdapter(nationalitiesArray, countriesArray, this);

        nationalityBinding.rvNationality.setLayoutManager(new LinearLayoutManager(this));
        nationalityBinding.rvNationality.setAdapter(nationalityAdapter);
    }

    private CurrentAccountTaxPostNationality getNationalityItem() {
        CurrentAccountTaxPostNationality nationalitiesItemModel = new CurrentAccountTaxPostNationality();
        nationalitiesItemModel.setNationalityId(getStringFromPref(Config.CNIC_NUMBER));
        nationalitiesItemModel.setRdaCustomerId(rdaCustomerId);
        return nationalitiesItemModel;
    }

    private void setBinding() {
        nationalityBinding = ActivitySelectNationalityBinding.inflate(getLayoutInflater());
        setContentView(nationalityBinding.getRoot());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                postNationalities();
                break;
            case R.id.bt_back:
                finish();
                break;
        }
    }

    private void postNationalities() {
        personalDetailsViewModel.postPersonalDetails(getNationalityPostData(), getStringFromPref(Config.ACCESS_TOKEN));
        showLoading();
    }

    private CurrentAccountTaxPostParams getNationalityPostData() {
        CurrentAccountTaxPostParams currentAccountTaxPostParams = new CurrentAccountTaxPostParams();
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
        currentAccountTaxPostConsumerList.setOccupationId(consumerList.get(0).getOccupationId());
        currentAccountTaxPostConsumerList.setProfessionId(consumerList.get(0).getProfessionId());
        currentAccountTaxPostConsumerList.setCustomerNtn(consumerList.get(0).getCustomerNtn());
        currentAccountTaxPostConsumerList.setKinName(consumerList.get(0).getKinName());
        currentAccountTaxPostConsumerList.setKinCnic(consumerList.get(0).getKinCnic());
        currentAccountTaxPostConsumerList.setKinMobile(consumerList.get(0).getKinMobile());
        currentAccountTaxPostConsumerList.setNationalityTypeId(consumerList.get(0).getNationalityTypeId());
        currentAccountTaxPostConsumerList.setNationalities(nationalitiesArray);

        currentAccountConsumerList.add(currentAccountTaxPostConsumerList);

        currentAccountTaxPostParams.getData().setConsumerList(currentAccountConsumerList);
        return currentAccountTaxPostParams;
    }

    @Override
    public void onCheckedChanged(CompoundButton switchButton, boolean isChecked) {
        switch (switchButton.getId()) {
            case R.id.sw_nationality:
                nationalitiesArray.clear();
                if (isChecked) {
                    addTwoNationalityItems();
                } else {
                    //for single nationality
                    addOneNationalityItem();
                }
                nationalityAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void addOneNationalityItem() {
        NATIONALITY_TYPE_ID = Config.SINGLE_NATIONALITY;
        nationalitiesArray.add(getNationalityItem());
    }

    private void addTwoNationalityItems() {
        NATIONALITY_TYPE_ID = Config.DUAL_NATIONALITY;
        nationalitiesArray.add(getNationalityItem());
        nationalitiesArray.add(getNationalityItem());
    }
}
