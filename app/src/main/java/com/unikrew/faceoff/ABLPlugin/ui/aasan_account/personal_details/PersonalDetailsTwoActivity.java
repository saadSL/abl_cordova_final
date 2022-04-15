package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivityPersonalDetailsTwoBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.PostUserAddressDataItem;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.PostUserAddressListItem;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.PostUserAddressModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.UserAddressResponseModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmployeeDetailsPostConsumerList;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmployeeDetailsPostData;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmployeeDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AddressesItemResponse;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account.SelectBankingModeActivity;
import com.unikrew.faceoff.Config;


public class PersonalDetailsTwoActivity extends BaseActivity {

    private ActivityPersonalDetailsTwoBinding personalDetailsTwoBinding;
    private RegisterEmployeeDetailsPostParams registerEmployeeDetailsPostParams;
    private PersonalDetailsViewModel personalDetailsViewModel;
    private AddressesItemResponse addressesItemResponse;

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
        addressListItem.setRdaCustomerId(addressesItemResponse.getRdaCustomerId());
        addressListItem.setNearestLandMark(personalDetailsTwoBinding.etLandmark.getText().toString());
        addressListItem.setCustomerAddress(personalDetailsTwoBinding.etAddress.getText().toString());
        addressListItem.setCity(personalDetailsTwoBinding.etCity.getText().toString());
        addressListItem.setCountryId(addressesItemResponse.getCountryId());

        userAddressDataItem.getAddressesList().add(addressListItem);
        userAddressDataItem.setPrimary(true);

        postUserAddressModel.getData().add(userAddressDataItem);
        return postUserAddressModel;
    }

    private void setViewModel() {
        personalDetailsViewModel = new ViewModelProvider(this).get(PersonalDetailsViewModel.class);
    }

    private void getIntentData() {
        registerEmployeeDetailsPostParams = (RegisterEmployeeDetailsPostParams) getIntent().getSerializableExtra("registerEmployeeDetailsPostParams");
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
        personalDetailsViewModel.postPersonalDetails(getParams(),  getStringFromPref(Config.ACCESS_TOKEN));
    }

    private RegisterEmployeeDetailsPostParams getParams() {
        RegisterEmployeeDetailsPostData data = registerEmployeeDetailsPostParams.getData();
        RegisterEmployeeDetailsPostConsumerList consumerListItem = registerEmployeeDetailsPostParams.getData().getConsumerList().get(0);
        if (!isEmpty(personalDetailsTwoBinding.etEmail)) {
            consumerListItem.setEmailAddress(personalDetailsTwoBinding.etEmail.getText().toString());
        }
        if (!isEmpty(personalDetailsTwoBinding.etLandline)) {
            consumerListItem.setLandlineNumber(personalDetailsTwoBinding.etLandline.getText().toString());
        }

        data.consumerList.add(consumerListItem);
        registerEmployeeDetailsPostParams.setData(data);
        return registerEmployeeDetailsPostParams;
    }

    private void goToPersonalDetailsThree() {
        Intent intent = new Intent(this, PersonalDetailsThreeActivity.class);
        intent.putExtra("registerEmployeeDetailsPostParams", registerEmployeeDetailsPostParams);
        startActivity(intent);
    }

    private void setData() {
        addressesItemResponse = SelectBankingModeActivity.globalRegisterVerifyOtp.getData().getConsumerList().get(0).getAddresses().get(0);
        if (addressesItemResponse.getCustomerAddress() != null && !addressesItemResponse.getCustomerAddress().equals("")) {
            personalDetailsTwoBinding.etAddress.setText(addressesItemResponse.getCustomerAddress());
        }

        if (addressesItemResponse.getNearestLandMark() != null && !addressesItemResponse.getNearestLandMark().equals("")) {
            personalDetailsTwoBinding.etLandmark.setText(addressesItemResponse.getNearestLandMark());
        }

        if (addressesItemResponse.getCity() != null && !addressesItemResponse.getCity().equals("")) {
            personalDetailsTwoBinding.etCity.setText(addressesItemResponse.getCity());
        }

        if (addressesItemResponse.getCustomerTown() != null && !addressesItemResponse.getCustomerTown().equals("")) {
            personalDetailsTwoBinding.etTown.setText(addressesItemResponse.getCustomerTown());
        }

        if (addressesItemResponse.getCountry() != null && !addressesItemResponse.getCountry().equals("")) {
            personalDetailsTwoBinding.etCountry.setText(addressesItemResponse.getCountry());
        }

    }

    private void setBinding() {
        personalDetailsTwoBinding = ActivityPersonalDetailsTwoBinding.inflate(getLayoutInflater());
        setContentView(personalDetailsTwoBinding.getRoot());
    }
}