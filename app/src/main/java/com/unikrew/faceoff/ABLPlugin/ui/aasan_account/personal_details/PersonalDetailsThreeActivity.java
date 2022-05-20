package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivityPersonalDetailsThreeBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_three.PersonalDetailsThreeConsumerListItemModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_three.PersonalDetailsThreeDataModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_three.PersonalDetailsThreePostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycPostData;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkResponseData;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AccountInformationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.ConsumerListItemResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.remitter_details.RemitterDetailsActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_transaction.SelectCardActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.upload_document.UploadDocumentActivity;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;
import java.util.List;

public class PersonalDetailsThreeActivity extends BaseActivity {

    private ActivityPersonalDetailsThreeBinding personalDetailsThreeBinding;
    private PersonalDetailsViewModel personalDetailsViewModel;
    private ArrayList<MobileNetworkResponseData> occupationArray, professionArray;
    private List<ConsumerListItemResponse> consumerList;

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
            RegisterVerifyOtpResponse  registerVerifyOtpResponse = (RegisterVerifyOtpResponse) getSerializableFromPref(Config.REG_OTP_RESPONSE, RegisterVerifyOtpResponse.class);
            consumerList = registerVerifyOtpResponse.getData().getConsumerList();
        }
        //flow for drafted application
        else {
            GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse = (GetConsumerAccountDetailsResponse) getSerializableFromPref(Config.GET_CONSUMER_RESPONSE, GetConsumerAccountDetailsResponse.class);
            consumerList = getConsumerAccountDetailsResponse.getData().getConsumerList();
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
        }
        else {
            postDetailsThree();
        }
    }

    private void postDetailsThree() {
        showLoading();
        personalDetailsViewModel.postPersonalDetailsThree(getParams(), getStringFromPref(Config.ACCESS_TOKEN));
    }

    private PersonalDetailsThreePostModel getParams() {
        PersonalDetailsThreePostModel personalDetailsThreePostModel = new PersonalDetailsThreePostModel();
        PersonalDetailsThreeDataModel detailsThreeDataModel = new PersonalDetailsThreeDataModel();
        bindGenericData(detailsThreeDataModel);
        personalDetailsThreePostModel.setData(detailsThreeDataModel);
        return personalDetailsThreePostModel;
    }

    private void bindGenericData(PersonalDetailsThreeDataModel detailsThreeDataModel) {
        for (int i = 0; i < consumerList.size(); i++) {
            PersonalDetailsThreeConsumerListItemModel consumerListItem = new PersonalDetailsThreeConsumerListItemModel();
            AccountInformationResponse accountInformation = consumerList.get(i).getAccountInformation();
            consumerListItem.setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
            //used rdaCustomerProfileId instead of rdaCustomerId since both are same
            consumerListItem.setRdaCustomerProfileId(accountInformation.getRdaCustomerId());
            if (i == consumerList.size() - 1) {
                consumerListItem.setOccupationId(occupationArray.get(personalDetailsThreeBinding.spinnerOccupation.getSelectedItemPosition()).getId());
                consumerListItem.setProfessionId(professionArray.get(personalDetailsThreeBinding.spinnerProfession.getSelectedItemPosition()).getId());
                consumerListItem.setPrimary(consumerList.size() <= 1);
            } else {
                consumerListItem.setOccupationId(consumerList.get(i).getOccupationId());
                consumerListItem.setProfessionId(consumerList.get(i).getProfessionId());
                consumerListItem.setPrimary(false);
            }
            detailsThreeDataModel.getConsumerList().add(consumerListItem);
        }
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
                moveNext();
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
        for (int i = 0; i < consumerList.size(); i++) {
            AccountInformationResponse accountInformation = consumerList.get(i).getAccountInformation();
            saveKycPostData.setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
            //used rdaCustomerProfileId instead of rdaCustomerId since both are same
            saveKycPostData.setRdaCustomerProfileId(accountInformation.getRdaCustomerId());
            if (i == consumerList.size() - 1) {
                saveKycPostData.setAverageMonthlySalary(Long.valueOf(personalDetailsThreeBinding.etSalary.getText().toString().trim()));
            } else {
                saveKycPostData.setAverageMonthlySalary(null);
            }
            saveKycPostParams.getData().add(saveKycPostData);
        }
        return saveKycPostParams;
    }

    private void moveNext() {
        if (consumerList.size()>1){
            openActivity(UploadDocumentActivity.class);
        }else {
            if (getIntFromPref(Config.ACCOUNT_VARIANT_ID) == Config.REMITTANCE_ACCOUNT) {
                openActivity(RemitterDetailsActivity.class);
            } else {
                openActivity(SelectCardActivity.class);
            }
        }

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

}