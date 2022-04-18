package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivityPersonalDetailsTwoBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.PostUserAddressDataItem;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.PostUserAddressListItem;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.PostUserAddressModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.UserAddressResponseModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsPostConsumerList;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmployeeDetailsPostData;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AddressesItemResponse;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account.SelectBankingModeActivity;
import com.unikrew.faceoff.Config;


public class PersonalDetailsTwoActivity extends BaseActivity {

    private ActivityPersonalDetailsTwoBinding personalDetailsTwoBinding;
    private RegisterEmploymentDetailsPostParams registerEmploymentDetailsPostParams;
    private PersonalDetailsViewModel personalDetailsViewModel;
    private AddressesItemResponse addressesItemResponse;
    private Boolean IS_RESUMED;
    private GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setViewModel();
        getIntentData();
        setData();
        clicks();
        setObserver();
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
        PostUserAddressListItem addressListItem = new PostUserAddressListItem();
        addressListItem.setAddressTypeId(Config.ADDRESS_TYPE_ID);
        if (IS_RESUMED) {
            addressListItem.setRdaCustomerId(getConsumerAccountDetailsResponse.getData().consumerList.get(0).getRdaCustomerProfileId());
        } else {
            addressListItem.setRdaCustomerId(registerEmployeeDetailsPostParams.getData().consumerList.get(0).getRdaCustomerProfileId());
        }

        addressListItem.setNearestLandMark(personalDetailsTwoBinding.etLandmark.getText().toString());
        addressListItem.setCustomerAddress(personalDetailsTwoBinding.etAddress.getText().toString());
        addressListItem.setCity(personalDetailsTwoBinding.etCity.getText().toString());
        addressListItem.setCountryId(157);

        userAddressDataItem.getAddressesList().add(addressListItem);
        userAddressDataItem.setPrimary(true);

        postUserAddressModel.getData().add(userAddressDataItem);
        return postUserAddressModel;
    }

    private void setViewModel() {
        personalDetailsViewModel = new ViewModelProvider(this).get(PersonalDetailsViewModel.class);
    }

    private void getIntentData() {
        IS_RESUMED = getIntent().getBooleanExtra(Config.IS_RESUMED, false);
        if (IS_RESUMED) {
            getConsumerAccountDetailsResponse = (GetConsumerAccountDetailsResponse) getIntent().getSerializableExtra("getConsumerAccountDetailsResponse");
        } else {
            registerEmployeeDetailsPostParams = (RegisterEmployeeDetailsPostParams) getIntent().getSerializableExtra("registerEmployeeDetailsPostParams");
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
        personalDetailsViewModel.postPersonalDetails(getParams(), getStringFromPref(Config.ACCESS_TOKEN));
    }

    private RegisterEmployeeDetailsPostParams getParams() {

        RegisterEmployeeDetailsPostData data;
        RegisterEmployeeDetailsPostConsumerList consumerListItem;
        if (IS_RESUMED) {
            //flow for resumed application
            data = new RegisterEmployeeDetailsPostData();
            consumerListItem = new RegisterEmployeeDetailsPostConsumerList();
            //binding previously saved data, So nulls don't get posted with new request
            consumerListItem.setRdaCustomerAccInfoId(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getAccountInformation().rdaCustomerAccInfoId);
            consumerListItem.setRdaCustomerProfileId(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getRdaCustomerProfileId());
            consumerListItem.setFullName(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getFullName());
            consumerListItem.setFatherHusbandName(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getFatherHusbandName());
            consumerListItem.setMotherMaidenName(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getMotherMaidenName());
            consumerListItem.setPlaceofBirth(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getCityOfBirth());
            consumerListItem.setPrimary(true);

        } else {
            //for new application
            data = registerEmployeeDetailsPostParams.getData();
            consumerListItem = registerEmployeeDetailsPostParams.getData().getConsumerList().get(0);
        }

        if (!isEmpty(personalDetailsTwoBinding.etEmail)) {
            consumerListItem.setEmailAddress(personalDetailsTwoBinding.etEmail.getText().toString());
        }
        if (!isEmpty(personalDetailsTwoBinding.etLandline)) {
            consumerListItem.setLandlineNumber(personalDetailsTwoBinding.etLandline.getText().toString());
        }


        if (IS_RESUMED) {
            data.consumerList.add(consumerListItem);
        } else {
            data.consumerList.set(0, consumerListItem);
        }

        registerEmployeeDetailsPostParams.setData(data);
        return registerEmployeeDetailsPostParams;
    }

    private void goToPersonalDetailsThree() {
        Intent intent = new Intent(this, PersonalDetailsThreeActivity.class);
        intent.putExtra("registerEmployeeDetailsPostParams", registerEmployeeDetailsPostParams);
        startActivity(intent);
    }

    private void setData() {
        if (IS_RESUMED) {
            //address flow for resumed application


        } else {
            //address flow for new application
        }

//        addressesItemResponse = SelectBankingModeActivity.globalRegisterVerifyOtp.getData().getConsumerList().get(0).getAddresses().get(0);
//        if (addressesItemResponse.getCustomerAddress() != null && !addressesItemResponse.getCustomerAddress().equals("")) {
//            personalDetailsTwoBinding.etAddress.setText(addressesItemResponse.getCustomerAddress());
//        }
//
//        if (addressesItemResponse.getNearestLandMark() != null && !addressesItemResponse.getNearestLandMark().equals("")) {
//            personalDetailsTwoBinding.etLandmark.setText(addressesItemResponse.getNearestLandMark());
//        }
//
//        if (addressesItemResponse.getCity() != null && !addressesItemResponse.getCity().equals("")) {
//            personalDetailsTwoBinding.etCity.setText(addressesItemResponse.getCity());
//        }
//
//        if (addressesItemResponse.getCustomerTown() != null && !addressesItemResponse.getCustomerTown().equals("")) {
//            personalDetailsTwoBinding.etTown.setText(addressesItemResponse.getCustomerTown());
//        }
//
//        if (addressesItemResponse.getCountry() != null && !addressesItemResponse.getCountry().equals("")) {
//            personalDetailsTwoBinding.etCountry.setText(addressesItemResponse.getCountry());
//        }

    }

    private void setBinding() {
        personalDetailsTwoBinding = ActivityPersonalDetailsTwoBinding.inflate(getLayoutInflater());
        setContentView(personalDetailsTwoBinding.getRoot());
    }
}