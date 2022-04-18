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
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmployeeDetailsPostConsumerList;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmployeeDetailsPostData;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmployeeDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account.SelectBankingModeActivity;
import com.unikrew.faceoff.Config;
;import java.util.ArrayList;
import java.util.List;


public class PersonalDetailsOneActivity extends BaseActivity implements AdapterClickListener {

    private ActivityPersonalDetailsOneBinding personalDetailsBinding;
    private SuggestionsAdapter motherNameAdapter, placeOfBirthAdapter;
    private final List<SelectionModel> _motherNameSuggestions = new ArrayList<>();
    private final List<SelectionModel> _placeOfBirthSuggestions = new ArrayList<>();
    private PersonalDetailsViewModel personalDetailsViewModel;
    private RegisterEmployeeDetailsPostParams registerEmployeeDetailsPostParams;
    private String selectedMotherName, selectedPlaceOfBirth;
    private Boolean IS_RESUMED;
    private GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        getIntentData();
        setData();
        setViewModel();
        setMotherNameRecycler();
        setPlaceOfBirthRecycler();
        clicks();
        setObservers();
    }

    private void getIntentData() {
        IS_RESUMED = getIntent().getBooleanExtra(Config.IS_RESUMED, false);
        if (IS_RESUMED) {
            getConsumerAccountDetailsResponse = (GetConsumerAccountDetailsResponse) getIntent().getSerializableExtra("getConsumerAccountDetailsResponse");
        } else {

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
        registerEmployeeDetailsPostParams = new RegisterEmployeeDetailsPostParams();
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
        personalDetailsViewModel.postPersonalDetails(getParams(), getStringFromPref(Config.ACCESS_TOKEN));
    }

    private RegisterEmployeeDetailsPostParams getParams() {
        RegisterEmployeeDetailsPostConsumerList consumerListItem = new RegisterEmployeeDetailsPostConsumerList();

        if (IS_RESUMED) {
            //flow for resumed application
            GetConsumerAccountDetailsResponseAccountInformation accountInformation = getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getAccountInformation();
            consumerListItem.setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
            //used rdaCustomerProfileId instead of rdaCustomerId since both are same
            consumerListItem.setRdaCustomerProfileId(accountInformation.getRdaCustomerId());
        } else {
            //flow for new application
            consumerListItem.setRdaCustomerAccInfoId(SelectBankingModeActivity.globalRegisterVerifyOtp.getData().getConsumerList().get(0).getAccountInformation().getRdaCustomerAccInfoId());
            consumerListItem.setRdaCustomerProfileId(SelectBankingModeActivity.globalRegisterVerifyOtp.getData().getConsumerList().get(0).getRdaCustomerProfileId());
        }

        consumerListItem.setFullName(personalDetailsBinding.etFullName.getText().toString());
        consumerListItem.setFatherHusbandName(personalDetailsBinding.etFatherName.getText().toString());
        consumerListItem.setMotherMaidenName(selectedMotherName);
        consumerListItem.setPlaceofBirth(selectedPlaceOfBirth);
        consumerListItem.setPrimary(true);
        RegisterEmployeeDetailsPostData registerEmployeeDetailsPostData = new RegisterEmployeeDetailsPostData();
        registerEmployeeDetailsPostData.consumerList.add(consumerListItem);

        registerEmployeeDetailsPostParams.setData(registerEmployeeDetailsPostData);
        return registerEmployeeDetailsPostParams;
    }

    private void goToPersonalDetailsTwo() {
        Intent intent = new Intent(this, PersonalDetailsTwoActivity.class);
        intent.putExtra("registerEmployeeDetailsPostParams", registerEmployeeDetailsPostParams);
        intent.putExtra(Config.IS_RESUMED,false);
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
            fullName = getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getFullName();
            fatherHusbandName = getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getFatherHusbandName();
        } else {
            //flow for new application
            fullName = SelectBankingModeActivity.globalRegisterVerifyOtp.getData().getConsumerList().get(0).getFullName();
            fatherHusbandName = SelectBankingModeActivity.globalRegisterVerifyOtp.getData().getConsumerList().get(0).getFatherHusbandName();
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
            placeOfBirthSuggestions = getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getSuggestPlaceOfBirth();
        } else {
            //flow for new application
            placeOfBirthSuggestions = SelectBankingModeActivity.globalRegisterVerifyOtp.getData().getConsumerList().get(0).getSuggestPlaceOfBirth();
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
            motherNameSuggestions = getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getSuggestMotherNames();
        } else {
            //flow for new application
            motherNameSuggestions = SelectBankingModeActivity.globalRegisterVerifyOtp.getData().getConsumerList().get(0).getSuggestMotherNames();
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