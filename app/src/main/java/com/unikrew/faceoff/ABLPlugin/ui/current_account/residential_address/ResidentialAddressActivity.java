package com.unikrew.faceoff.ABLPlugin.ui.current_account.residential_address;

import androidx.appcompat.app.AppCompatActivity;
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
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.ConsumerListItemResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details.PersonalDetailsViewModel;
import com.unikrew.faceoff.Config;

import java.util.List;

public class ResidentialAddressActivity extends BaseActivity {

    private AcitvityPermanentResidentialAddressBinding residentialAddressBinding;
    private PersonalDetailsViewModel personalDetailsViewModel;
    private List<ConsumerListItemResponse> consumerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        getSharedPrefData();
        setViewModel();
        setClicks();
        setLogoLayout(residentialAddressBinding.logoToolbar.tvDate);
        setLayout();
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

    private void setViewModel() {
        personalDetailsViewModel = new ViewModelProvider(this).get(PersonalDetailsViewModel.class);
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
            addressListItem.setAddressTypeId(Config.PERMANENT_ADDRESS_TYPE_ID);
            addressListItem.setRdaCustomerId(consumerList.get(i).getRdaCustomerProfileId());
            addressListItem.setCountryId(Config.COUNTRY_ID);
            addressListItem.setCountry(null);
            if (i == consumerList.size() - 1) {

                addressListItem.setNearestLandMark(residentialAddressBinding.etLandmark.getText().toString());
                addressListItem.setCustomerAddress(residentialAddressBinding.etAddress.getText().toString());
                addressListItem.setCity(residentialAddressBinding.etCity.getText().toString());
                addressListItem.setCustomerTown(residentialAddressBinding.etTown.getText().toString());
                userAddressDataItem.setPrimary(consumerList.size() <= 1);
            } else {

                addressListItem.setNearestLandMark(consumerList.get(i).getNearestLandmark());
                addressListItem.setCustomerAddress(consumerList.get(i).getCustomerAddress());
                addressListItem.setCity(null);
                addressListItem.setCustomerTown(null);
                userAddressDataItem.setPrimary(false);
            }
            userAddressDataItem.getAddressesList().add(addressListItem);
        }
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