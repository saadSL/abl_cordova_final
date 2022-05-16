package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivityPersonalDetailsOneBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.SelectionModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponseAccountInformation;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponseConsumerList;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_one.PersonalDetailsOneConsumerListItemModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_one.PersonalDetailsOneDataModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_one.PersonalDetailsOnePostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AccountInformationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.ConsumerListItemResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.Config;
;import java.util.ArrayList;
import java.util.List;


public class PersonalDetailsOneActivity extends BaseActivity implements AdapterClickListener {

    private ActivityPersonalDetailsOneBinding personalDetailsBinding;
    private SuggestionsAdapter motherNameAdapter, placeOfBirthAdapter;
    private final List<SelectionModel> _motherNameSuggestions = new ArrayList<>();
    private final List<SelectionModel> _placeOfBirthSuggestions = new ArrayList<>();
    private PersonalDetailsViewModel personalDetailsViewModel;
    private RegisterVerifyOtpResponse registerVerifyOtpResponse;
    private GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse;
    private String selectedMotherName, selectedPlaceOfBirth;
    private Boolean IS_RESUMED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        getSharedPrefData();
        setData();
        setViewModel();
        setMotherNameRecycler();
        setPlaceOfBirthRecycler();
        clicks();
        setObservers();
        setLogoLayout(personalDetailsBinding.layoutLogo.tvDate);
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

    private void setObservers() {
        personalDetailsViewModel.registerEmploymentDetailsResponseMutableLiveData.observe(this, new Observer<RegisterEmploymentDetailsResponse>() {
            @Override
            public void onChanged(RegisterEmploymentDetailsResponse registerEmploymentDetailsResponse) {
                dismissLoading();
                goToPersonalDetailsTwo();
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


    private void setViewModel() {
        personalDetailsViewModel = new ViewModelProvider(this).get(PersonalDetailsViewModel.class);
    }

    private void clicks() {
        personalDetailsBinding.layoutBtn.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidations();
            }
        });

        personalDetailsBinding.layoutBtn.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkValidations() {
        if (isEmpty(personalDetailsBinding.etFullName) || isEmpty(personalDetailsBinding.etFatherName)) {
            showAlert(Config.errorType, getString(R.string.text_fields_error));
        } else if (!isMotherNameSelected()) {
            showAlert(Config.errorType, getString(R.string.mother_name_error));
        } else if (!isBirthPlaceSelected()) {
            showAlert(Config.errorType, getString(R.string.birth_place_error));
        } else {
            postPersonalDetailsOne();
        }
    }

    private void postPersonalDetailsOne() {
        showLoading();
        personalDetailsViewModel.postPersonalDetailsOne(getParams(), getStringFromPref(Config.ACCESS_TOKEN));
    }

    private PersonalDetailsOnePostModel getParams() {
        PersonalDetailsOnePostModel personalDetailsOnePostModel = new PersonalDetailsOnePostModel();
        PersonalDetailsOneDataModel detailsOneDataModel = new PersonalDetailsOneDataModel();
        PersonalDetailsOneConsumerListItemModel consumerListItem;

        if (IS_RESUMED) {
            //flow for resumed application
            ArrayList<GetConsumerAccountDetailsResponseConsumerList> consumerList = getConsumerAccountDetailsResponse.getData().getConsumerList();
            for (int i = 0; i < consumerList.size(); i++) {
                consumerListItem = new PersonalDetailsOneConsumerListItemModel();
                GetConsumerAccountDetailsResponseAccountInformation accountInformation = getConsumerAccountDetailsResponse.getData().getConsumerList().get(i).getAccountInformation();
                consumerListItem.setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
                //used rdaCustomerProfileId instead of rdaCustomerId since both are same
                consumerListItem.setRdaCustomerProfileId(accountInformation.getRdaCustomerId());
            }
        } else {
            //flow for new application
            List<ConsumerListItemResponse> consumerList = registerVerifyOtpResponse.getData().getConsumerList();
            for (int i = 0; i < consumerList.size(); i++) {
                consumerListItem = new PersonalDetailsOneConsumerListItemModel();
                AccountInformationResponse accountInformation = registerVerifyOtpResponse.getData().getConsumerList().get(i).getAccountInformation();
                consumerListItem.setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
                //used rdaCustomerProfileId instead of rdaCustomerId since both are same
                consumerListItem.setRdaCustomerProfileId(accountInformation.getRdaCustomerId());
            }
        }
        personalDetailsOnePostModel.setData(detailsOneDataModel);
        return personalDetailsOnePostModel;
    }

    private void goToPersonalDetailsTwo() {
        Intent intent = new Intent(this, PersonalDetailsTwoActivity.class);
        startActivity(intent);
    }


    private boolean isMotherNameSelected() {
        for (int i = 0; i < _motherNameSuggestions.size(); i++) {
            if (_motherNameSuggestions.get(i).getSelected()) {
                selectedMotherName = _motherNameSuggestions.get(i).getTitle();
                return true;
            }
        }
        return false;
    }

    private boolean isBirthPlaceSelected() {
        for (int i = 0; i < _placeOfBirthSuggestions.size(); i++) {
            if (_placeOfBirthSuggestions.get(i).getSelected()) {
                selectedPlaceOfBirth = _placeOfBirthSuggestions.get(i).getTitle();
                return true;
            }
        }
        return false;
    }

    private void setData() {
        String fullName = null;
        String fatherHusbandName = null;
        if (IS_RESUMED) {
            //flow for resumed application
            fullName = getConsumerAccountDetailsResponse.getData().getConsumerList().get(getConsumerAccountDetailsResponse.getData().getConsumerList().size() - 1).getFullName();
            fatherHusbandName = getConsumerAccountDetailsResponse.getData().getConsumerList().get(getConsumerAccountDetailsResponse.getData().getConsumerList().size() - 1).getFatherHusbandName();
        } else {
            //flow for new application
            fullName = registerVerifyOtpResponse.getData().getConsumerList().get(registerVerifyOtpResponse.getData().getConsumerList().size() - 1).getFullName();
            fatherHusbandName = registerVerifyOtpResponse.getData().getConsumerList().get(registerVerifyOtpResponse.getData().getConsumerList().size() - 1).getFatherHusbandName();
        }
        if (fullName != null) {
            personalDetailsBinding.etFullName.setText(fullName);
        }

        if (fatherHusbandName != null) {
            personalDetailsBinding.etFatherName.setText(fatherHusbandName);
        }

    }

    private void setPlaceOfBirthRecycler() {
        List<String> placeOfBirthSuggestions;
        if (IS_RESUMED) {
            //flow for resumed application
            placeOfBirthSuggestions = getConsumerAccountDetailsResponse.getData().getConsumerList().get(getConsumerAccountDetailsResponse.getData().getConsumerList().size() - 1).getSuggestPlaceOfBirth();
        } else {
            //flow for new application
            placeOfBirthSuggestions = registerVerifyOtpResponse.getData().getConsumerList().get(registerVerifyOtpResponse.getData().getConsumerList().size() - 1).getSuggestPlaceOfBirth();
        }

        for (int i = 0; i < placeOfBirthSuggestions.size(); i++) {
            SelectionModel selectionModel = new SelectionModel();
            selectionModel.setSelected(false);
            selectionModel.setTitle(placeOfBirthSuggestions.get(i));
            _placeOfBirthSuggestions.add(selectionModel);
        }

        placeOfBirthAdapter = new SuggestionsAdapter(_placeOfBirthSuggestions, this, Config.PLACE_OF_BIRTH_SUGGESTION, this);
        personalDetailsBinding.rvPlaceOfBirth.setLayoutManager(new GridLayoutManager(this, 3));
        personalDetailsBinding.rvPlaceOfBirth.setAdapter(placeOfBirthAdapter);
    }

    private void setMotherNameRecycler() {
        List<String> motherNameSuggestions;
        if (IS_RESUMED) {
            //flow for resumed application
            motherNameSuggestions = getConsumerAccountDetailsResponse.getData().getConsumerList().get(getConsumerAccountDetailsResponse.getData().getConsumerList().size() - 1).getSuggestMotherNames();
        } else {
            //flow for new application
            motherNameSuggestions = registerVerifyOtpResponse.getData().getConsumerList().get(registerVerifyOtpResponse.getData().getConsumerList().size() - 1).getSuggestMotherNames();
        }

        for (int i = 0; i < motherNameSuggestions.size(); i++) {
            SelectionModel selectionModel = new SelectionModel();
            selectionModel.setSelected(false);
            selectionModel.setTitle(motherNameSuggestions.get(i));
            _motherNameSuggestions.add(selectionModel);
        }

        motherNameAdapter = new SuggestionsAdapter(_motherNameSuggestions, this, Config.MOTHER_NAME_SUGGESTION, this);
        personalDetailsBinding.rvMotherName.setLayoutManager(new GridLayoutManager(this, 3));
        personalDetailsBinding.rvMotherName.setAdapter(motherNameAdapter);
    }


    private void setBinding() {
        personalDetailsBinding = ActivityPersonalDetailsOneBinding.inflate(getLayoutInflater());
        setContentView(personalDetailsBinding.getRoot());
    }

    @Override
    public void onItemClick(String type, int position) {
        if (type.equals(Config.MOTHER_NAME_SUGGESTION)) {
            unselectAllMotherSuggestions();
            _motherNameSuggestions.get(position).setSelected(true);
            motherNameAdapter.notifyDataSetChanged();
        } else {
            unselectAllPlaceSuggestions();
            _placeOfBirthSuggestions.get(position).setSelected(true);
            placeOfBirthAdapter.notifyDataSetChanged();
        }
    }

    private void unselectAllMotherSuggestions() {
        for (int i = 0; i < _motherNameSuggestions.size(); i++) {
            _motherNameSuggestions.get(i).setSelected(false);
        }
    }

    private void unselectAllPlaceSuggestions() {
        for (int i = 0; i < _placeOfBirthSuggestions.size(); i++) {
            _placeOfBirthSuggestions.get(i).setSelected(false);
        }
    }
}