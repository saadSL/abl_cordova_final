package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivityPersonalDetailsThreeBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponseAccountInformation;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_three.PersonalDetailsThreeConsumerListItemModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_three.PersonalDetailsThreeDataModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_three.PersonalDetailsThreePostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_two.PersonalDetailsTwoConsumerListItemModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_two.PersonalDetailsTwoPostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmployeeDetailsPostConsumerList;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmployeeDetailsPostData;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmployeeDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycPostData;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkResponseData;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AccountInformationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_transaction.SelectCardActivity;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;
import java.util.List;

public class PersonalDetailsThreeActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private ActivityPersonalDetailsThreeBinding personalDetailsThreeBinding;
    private PersonalDetailsViewModel personalDetailsViewModel;
    private ArrayList<MobileNetworkResponseData> occupationArray, professionArray;
    private Boolean IS_RESUMED;
    private RegisterVerifyOtpResponse registerVerifyOtpResponse;
    private GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setViewModel();
        getDropDownData();
        getSharedPrefData();
        setObservers();
        clicks();
        setLogoLayout(personalDetailsThreeBinding.layoutLogo.tvDate);
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

    private void clicks() {
        personalDetailsThreeBinding.layoutBtn.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        personalDetailsThreeBinding.layoutBtn.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidations();
            }
        });
    }

    private void checkValidations() {
        if (isEmpty(personalDetailsThreeBinding.etSalary)) {
            showAlert(Config.errorType, getString(R.string.text_fields_error));
        } else {
            postDetailsThree();
        }
    }

    private void postDetailsThree() {
        showLoading();
        personalDetailsViewModel.postPersonalDetailsThree(getParams(), getStringFromPref(Config.ACCESS_TOKEN));
    }

    private PersonalDetailsThreePostModel getParams() {
        PersonalDetailsThreePostModel personalDetailsThreePostModel = new PersonalDetailsThreePostModel();
        PersonalDetailsThreeConsumerListItemModel consumerListItem = new PersonalDetailsThreeConsumerListItemModel();

        if (IS_RESUMED) {
            //flow for resumed application
            GetConsumerAccountDetailsResponseAccountInformation accountInformation = getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getAccountInformation();
            consumerListItem.setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
            //used rdaCustomerProfileId instead of rdaCustomerId since both are same
            consumerListItem.setRdaCustomerProfileId(accountInformation.getRdaCustomerId());
        } else {
            //flow for new application
            AccountInformationResponse accountInformation = registerVerifyOtpResponse.getData().getConsumerList().get(0).getAccountInformation();
            consumerListItem.setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
            //used rdaCustomerProfileId instead of rdaCustomerId since both are same
            consumerListItem.setRdaCustomerProfileId(accountInformation.getRdaCustomerId());
        }

        consumerListItem.setOccupationId(occupationArray.get(personalDetailsThreeBinding.spinnerOccupation.getSelectedItemPosition()).getId());
        consumerListItem.setProfessionId(professionArray.get(personalDetailsThreeBinding.spinnerProfession.getSelectedItemPosition()).getId());

        PersonalDetailsThreeDataModel data = new PersonalDetailsThreeDataModel();
        data.getConsumerList().add(consumerListItem);
        personalDetailsThreePostModel.setData(data);
        return personalDetailsThreePostModel;
    }

    private void setObservers() {
        personalDetailsViewModel.professionLiveData.observe(this, new Observer<MobileNetworkResponse>() {
            @Override
            public void onChanged(MobileNetworkResponse mobileNetworkResponse) {
                dismissLoading();
                setProfessionSpinner(mobileNetworkResponse);
            }
        });

        personalDetailsViewModel.occupationLiveData.observe(this, new Observer<MobileNetworkResponse>() {
            @Override
            public void onChanged(MobileNetworkResponse mobileNetworkResponse) {
                dismissLoading();
                setOccupationSpinner(mobileNetworkResponse);
            }
        });

        personalDetailsViewModel.saveKycResponseMutableLiveData.observe(this, new Observer<SaveKycResponse>() {
            @Override
            public void onChanged(SaveKycResponse saveKycResponse) {
                dismissLoading();
                openTransactionSelectionActivity();
            }
        });


        personalDetailsViewModel.registerEmploymentDetailsResponseMutableLiveData.observe(this, new Observer<RegisterEmploymentDetailsResponse>() {
            @Override
            public void onChanged(RegisterEmploymentDetailsResponse registerEmploymentDetailsResponse) {
                dismissLoading();
                postUserSalary();
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

    private void postUserSalary() {
        personalDetailsViewModel.saveKyc(getKycParams(), getStringFromPref(Config.ACCESS_TOKEN));
    }

    private SaveKycPostParams getKycParams() {
        SaveKycPostParams saveKycPostParams = new SaveKycPostParams();
        SaveKycPostData saveKycPostData = new SaveKycPostData();
        if (IS_RESUMED){
            GetConsumerAccountDetailsResponseAccountInformation accountInformation = getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getAccountInformation();
            saveKycPostData.setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
            //used rdaCustomerProfileId instead of rdaCustomerId since both are same
            saveKycPostData.setRdaCustomerProfileId(accountInformation.getRdaCustomerId());
        }else {
            //flow for new application
            AccountInformationResponse accountInformation = registerVerifyOtpResponse.getData().getConsumerList().get(0).getAccountInformation();
            saveKycPostData.setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
            //used rdaCustomerProfileId instead of rdaCustomerId since both are same
            saveKycPostData.setRdaCustomerProfileId(accountInformation.getRdaCustomerId());
        }

        saveKycPostData.setAverageMonthlySalary(Long.valueOf(personalDetailsThreeBinding.etSalary.getText().toString().trim()));
        saveKycPostParams.getData().add(saveKycPostData);
        return saveKycPostParams;
    }

    private void openTransactionSelectionActivity() {
        Intent intent = new Intent(this, SelectCardActivity.class);
        startActivity(intent);
    }

    private void setOccupationSpinner(MobileNetworkResponse mobileNetworkResponse) {
        occupationArray = mobileNetworkResponse.getData();
        setSpinner(occupationArray, personalDetailsThreeBinding.spinnerOccupation);
    }

    private void setProfessionSpinner(MobileNetworkResponse mobileNetworkResponse) {
        professionArray = mobileNetworkResponse.getData();
        setSpinner(professionArray, personalDetailsThreeBinding.spinnerProfession);
    }


    private void setSpinner(ArrayList<MobileNetworkResponseData> dataArrayList, Spinner spinner) {
        List<String> _allItemsArray = new ArrayList<>();
        if (dataArrayList.size() > 0) {
            // Spinner click listener
            spinner.setOnItemSelectedListener(this);
            // Spinner Drop down elements
            for (int i = 0; i < dataArrayList.size(); i++) {
                _allItemsArray.add(dataArrayList.get(i).getName());
            }

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, _allItemsArray);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);
        }
    }


    private void setBinding() {
        personalDetailsThreeBinding = ActivityPersonalDetailsThreeBinding.inflate(getLayoutInflater());
        setContentView(personalDetailsThreeBinding.getRoot());
    }

    private void setViewModel() {
        personalDetailsViewModel = new ViewModelProvider(this).get(PersonalDetailsViewModel.class);
    }

    private void getDropDownData() {
        showLoading();
        getOccupation();
        getProfession();
    }

    private void getProfession() {
        MobileNetworkPostParams mobileNetworkPostParams = new MobileNetworkPostParams();
        mobileNetworkPostParams.getData().codeTypeId = Config.PROFESSION_CODE;
        personalDetailsViewModel.getProfession(mobileNetworkPostParams);
    }


    private void getOccupation() {
        MobileNetworkPostParams mobileNetworkPostParams = new MobileNetworkPostParams();
        mobileNetworkPostParams.getData().codeTypeId = Config.OCCUPATION_CODE;
        personalDetailsViewModel.getOccupation(mobileNetworkPostParams);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}