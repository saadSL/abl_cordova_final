package com.unikrew.faceoff.ABLPlugin.ui.current_account.residential_address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.AcitvityPermanentResidentialAddressBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.PostUserAddressDataItem;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.PostUserAddressListItem;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.PostUserAddressModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.UserAddressResponseModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.ConsumerListItemResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details.PersonalDetailsViewModel;
import com.unikrew.faceoff.ABLPlugin.ui.current_account.nationality.NationalityActivity;
import com.unikrew.faceoff.Config;

import java.util.List;

public class ResidentialAddressActivity extends BaseActivity {

    private AcitvityPermanentResidentialAddressBinding residentialAddressBinding;
    private PersonalDetailsViewModel personalDetailsViewModel;
    private List<ConsumerListItemResponse> consumerList;
    private UserAddressResponseModel userAddressResponseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        getSharedPrefData();
        setViewModel();
        setClicks();
        observers();
        setLogoLayout(residentialAddressBinding.logoToolbar.tvDate);
        setLayout();
    }

    private void observers() {
        personalDetailsViewModel.userAddressMutableLiveData.observe(this, new Observer<UserAddressResponseModel>() {
            @Override
            public void onChanged(UserAddressResponseModel registerEmploymentDetailsResponse) {
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
        openActivity(NationalityActivity.class);
    }

    private void setLayout() {
        residentialAddressBinding.steps.screenHeader.stepsHeading1.setText("Your");
        residentialAddressBinding.steps.screenHeader.stepsHeading2.setText("Details");
        residentialAddressBinding.steps.step1.setBackground(this.getDrawable(R.color.custom_blue));
        residentialAddressBinding.steps.step2.setBackground(this.getDrawable(R.color.custom_blue));
        residentialAddressBinding.steps.step3.setBackground(this.getDrawable(R.color.light_gray));
        residentialAddressBinding.steps.step4.setBackground(this.getDrawable(R.color.light_gray));
    }

    private void getSharedPrefData() {

        //flow for drafted application
        if (getSerializableFromPref(Config.USER_ADDRESS_RESPONSE, UserAddressResponseModel.class) == null) {
            GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse = (GetConsumerAccountDetailsResponse) getSerializableFromPref(Config.GET_CONSUMER_RESPONSE, GetConsumerAccountDetailsResponse.class);
            consumerList = getConsumerAccountDetailsResponse.getData().getConsumerList();
        }
        //flow for new application
        else {
            userAddressResponseModel = (UserAddressResponseModel) getSerializableFromPref(Config.USER_ADDRESS_RESPONSE, UserAddressResponseModel.class);
        }

    }

    private void setViewModel() {
        personalDetailsViewModel = new ViewModelProvider(this).get(PersonalDetailsViewModel.class);
    }

    private void postUserAddress() {
        showLoading();
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
        PostUserAddressListItem addressListItemOne = new PostUserAddressListItem();
        addressListItemOne.setCountryId(Config.COUNTRY_ID);
        addressListItemOne.setAddressTypeId(Config.CURRENT_ADDRESS_TYPE_ID);
        if (userAddressResponseModel != null) {
            addressListItemOne.setRdaCustomerProfileAddrId(userAddressResponseModel.getData().get(0).getAddressesList().get(0).getRdaCustomerProfileAddrId());
            addressListItemOne.setRdaCustomerId(userAddressResponseModel.getData().get(0).getAddressesList().get(0).getRdaCustomerId());
            addressListItemOne.setNearestLandMark(userAddressResponseModel.getData().get(0).getAddressesList().get(0).getNearestLandMark());
            addressListItemOne.setCustomerAddress(userAddressResponseModel.getData().get(0).getAddressesList().get(0).getCustomerAddress());
        } else {
            addressListItemOne.setRdaCustomerProfileAddrId(consumerList.get(0).getRdaCustomerProfileAddrId());
            addressListItemOne.setNearestLandMark(consumerList.get(0).getNearestLandmark());
            addressListItemOne.setCustomerAddress(consumerList.get(0).getCustomerAddress());
            addressListItemOne.setRdaCustomerId(consumerList.get(0).getRdaCustomerProfileId());
        }
        userAddressDataItem.getAddressesList().add(addressListItemOne);

        PostUserAddressListItem addressListItemTwo = new PostUserAddressListItem();
        addressListItemTwo.setAddressTypeId(Config.PERMANENT_ADDRESS_TYPE_ID);
        addressListItemTwo.setNearestLandMark(residentialAddressBinding.etLandmark.getText().toString());
        addressListItemTwo.setCustomerAddress(residentialAddressBinding.etAddress.getText().toString());
        addressListItemTwo.setCustomerTown(residentialAddressBinding.etTown.getText().toString());
        addressListItemTwo.setCity(residentialAddressBinding.etCity.getText().toString());
        addressListItemTwo.setPhone(residentialAddressBinding.etLandline.getText().toString());
        if (userAddressResponseModel != null) {
            addressListItemTwo.setRdaCustomerId(userAddressResponseModel.getData().get(0).getAddressesList().get(0).getRdaCustomerId());
        } else {
            addressListItemTwo.setRdaCustomerId(consumerList.get(0).getRdaCustomerProfileId());
        }
        userAddressDataItem.getAddressesList().add(addressListItemTwo);
        userAddressDataItem.setPrimary(true);
    }

    private void setClicks() {
        residentialAddressBinding.btnContainer.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidations();
            }
        });
        residentialAddressBinding.btnContainer.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setBinding() {
        residentialAddressBinding = AcitvityPermanentResidentialAddressBinding.inflate(getLayoutInflater());
        setContentView(residentialAddressBinding.getRoot());
    }

    private void checkValidations() {
        if (isEmpty(residentialAddressBinding.etAddress) || isEmpty(residentialAddressBinding.etLandmark) || isEmpty(residentialAddressBinding.etCity) || isEmpty(residentialAddressBinding.etTown) || isEmpty(residentialAddressBinding.etLandline)) {
            showAlert(Config.errorType, getString(R.string.text_fields_error));
        } else {
            postUserAddress();
        }
    }
}