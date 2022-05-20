package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivityPersonalDetailsTwoBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_two.PersonalDetailsTwoConsumerListItemModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_two.PersonalDetailsTwoDataModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_two.PersonalDetailsTwoPostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.PostUserAddressDataItem;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.PostUserAddressListItem;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.PostUserAddressModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.UserAddressResponseModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AccountInformationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AddressesItemResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.ConsumerListItemResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.Config;

import java.util.List;


public class PersonalDetailsTwoActivity extends BaseActivity {

    private ActivityPersonalDetailsTwoBinding personalDetailsTwoBinding;
    private PersonalDetailsViewModel personalDetailsViewModel;
    private List<ConsumerListItemResponse> consumerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setViewModel();
        getSharedPrefData();
        setData();
        clicks();
        setObserver();
        setLogoLayout(personalDetailsTwoBinding.layoutLogo.tvDate);
    }

    private void setObserver() {
        personalDetailsViewModel.registerEmploymentDetailsResponseMutableLiveData.observe(this, new Observer<RegisterEmploymentDetailsResponse>() {
            @Override
            public void onChanged(RegisterEmploymentDetailsResponse registerEmploymentDetailsResponse) {
                dismissLoading();
                postUserAddress();
            }
        });

        personalDetailsViewModel.userAddressMutableLiveData.observe(this, new Observer<UserAddressResponseModel>() {
            @Override
            public void onChanged(UserAddressResponseModel registerEmploymentDetailsResponse) {
                dismissLoading();
                goToPersonalDetailsThree();
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

    private void postUserAddress() {
        personalDetailsViewModel.userAddress(getAddressParams(), getStringFromPref(Config.ACCESS_TOKEN));
    }

    private PostUserAddressModel getAddressParams() {
        PostUserAddressModel postUserAddressModel = new PostUserAddressModel();
        PostUserAddressDataItem userAddressDataItem = new PostUserAddressDataItem();
        bindAddressGenericData(userAddressDataItem);
        postUserAddressModel.getData().add(userAddressDataItem);
        return postUserAddressModel;
    }

    private void bindAddressGenericData(PostUserAddressDataItem userAddressDataItem) {
        for (int i = 0; i < consumerList.size(); i++) {
            PostUserAddressListItem addressListItem = new PostUserAddressListItem();
            addressListItem.setAddressTypeId(Config.ADDRESS_TYPE_ID);
            addressListItem.setRdaCustomerId(consumerList.get(i).getRdaCustomerProfileId());
            addressListItem.setCountryId(Config.COUNTRY_ID);
            if (i == consumerList.size() - 1) {
                addressListItem.setCountry(personalDetailsTwoBinding.etCountry.getText().toString());
                addressListItem.setNearestLandMark(personalDetailsTwoBinding.etLandmark.getText().toString());
                addressListItem.setCustomerAddress(personalDetailsTwoBinding.etAddress.getText().toString());
                addressListItem.setCity(personalDetailsTwoBinding.etCity.getText().toString());
                addressListItem.setCustomerTown(personalDetailsTwoBinding.etTown.getText().toString());
                userAddressDataItem.setPrimary(consumerList.size() <= 1);
            } else {
                addressListItem.setCountry(null);
                addressListItem.setNearestLandMark(consumerList.get(i).getNearestLandmark());
                addressListItem.setCustomerAddress(consumerList.get(i).getCustomerAddress());
                addressListItem.setCity(null);
                addressListItem.setCustomerTown(null);
                userAddressDataItem.setPrimary(false);
            }
            userAddressDataItem.getAddressesList().add(addressListItem);
        }
    }

    private void setViewModel() {
        personalDetailsViewModel = new ViewModelProvider(this).get(PersonalDetailsViewModel.class);
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
        personalDetailsTwoBinding.layoutBtn.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        personalDetailsTwoBinding.layoutBtn.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidations();
            }
        });
    }

    private void checkValidations() {
        if (isEmpty(personalDetailsTwoBinding.etAddress) || isEmpty(personalDetailsTwoBinding.etLandmark) || isEmpty(personalDetailsTwoBinding.etCity) || isEmpty(personalDetailsTwoBinding.etTown) || isEmpty(personalDetailsTwoBinding.etCountry)) {
            showAlert(Config.errorType, getString(R.string.text_fields_error));
        } else {
            postDetailsTwo();
        }
    }

    private void postDetailsTwo() {
        showLoading();
        personalDetailsViewModel.postPersonalDetailsTwo(getParams(), getStringFromPref(Config.ACCESS_TOKEN));
    }

    private PersonalDetailsTwoPostModel getParams() {
        PersonalDetailsTwoPostModel personalDetailsTwoPostModel = new PersonalDetailsTwoPostModel();
        PersonalDetailsTwoDataModel detailsTwoDataModel = new PersonalDetailsTwoDataModel();
        bindGenericData(detailsTwoDataModel);
        personalDetailsTwoPostModel.setData(detailsTwoDataModel);
        return personalDetailsTwoPostModel;
    }

    private void bindGenericData(PersonalDetailsTwoDataModel detailsTwoDataModel) {
        for (int i = 0; i < consumerList.size(); i++) {
            PersonalDetailsTwoConsumerListItemModel consumerListItem = new PersonalDetailsTwoConsumerListItemModel();
            AccountInformationResponse accountInformation = consumerList.get(i).getAccountInformation();
            consumerListItem.setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
            //used rdaCustomerProfileId instead of rdaCustomerId since both are same
            consumerListItem.setRdaCustomerProfileId(accountInformation.getRdaCustomerId());
            if (i == consumerList.size() - 1) {
                if (!isEmpty(personalDetailsTwoBinding.etEmail)) {
                    consumerListItem.setEmailAddress(personalDetailsTwoBinding.etEmail.getText().toString());
                    consumerListItem.setPrimary(consumerList.size() <= 1);
                }
                if (!isEmpty(personalDetailsTwoBinding.etLandline)) {
                    consumerListItem.setLandlineNumber(personalDetailsTwoBinding.etLandline.getText().toString());
                    consumerListItem.setPrimary(false);
                }
            } else {
                consumerListItem.setEmailAddress(consumerList.get(i).getEmailAddress());
                consumerListItem.setLandlineNumber(null);
            }
            detailsTwoDataModel.getConsumerList().add(consumerListItem);
        }
    }

    private void goToPersonalDetailsThree() {
        Intent intent = new Intent(this, PersonalDetailsThreeActivity.class);
        startActivity(intent);
    }

    private void setData() {
        //to bind the data of latest member
        AddressesItemResponse addressesItemResponse = consumerList.get(consumerList.size() - 1).getAddresses().get(0);
        bindText(consumerList.get(consumerList.size() - 1).getEmailAddress(), personalDetailsTwoBinding.etEmail);
        bindText(addressesItemResponse.getCustomerAddress(), personalDetailsTwoBinding.etAddress);
        bindText(addressesItemResponse.getNearestLandMark(), personalDetailsTwoBinding.etLandmark);
        bindText(addressesItemResponse.getCity(), personalDetailsTwoBinding.etCity);
        bindText(addressesItemResponse.getCustomerTown(), personalDetailsTwoBinding.etTown);
        bindText(addressesItemResponse.getCountry(), personalDetailsTwoBinding.etCountry);
    }

    private void bindText(String value, EditText et) {
        if (value != null && !value.equals("")) {
            et.setText(value);
        }
    }

    private void setBinding() {
        personalDetailsTwoBinding = ActivityPersonalDetailsTwoBinding.inflate(getLayoutInflater());
        setContentView(personalDetailsTwoBinding.getRoot());
    }
}