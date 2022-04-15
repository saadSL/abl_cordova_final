package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivityPersonalDetailsTwoBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AddressesItemResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.ConsumerListItemResponse;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account.SelectBankingModeActivity;
import com.unikrew.faceoff.Config;

public class PersonalDetailsTwoActivity extends BaseActivity {

    private ActivityPersonalDetailsTwoBinding personalDetailsTwoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setData();
        clicks();
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
        if (isEmpty(personalDetailsTwoBinding.etAddress) || isEmpty(personalDetailsTwoBinding.etLandmark) || isEmpty(personalDetailsTwoBinding.etCity) || isEmpty(personalDetailsTwoBinding.etTown) || isEmpty(personalDetailsTwoBinding.etCountry)){
            showAlert(Config.errorType, getString(R.string.text_fields_error));
        }else {
            goToPersonalDetailsThree();
        }
    }

    private void goToPersonalDetailsThree() {
        Intent intent = new Intent(this, PersonalDetailsThreeActivity.class);
        startActivity(intent);
    }

    private void setData() {
        AddressesItemResponse addressesItemResponse = SelectBankingModeActivity.globalRegisterVerifyOtp.getData().getConsumerList().get(0).getAddresses().get(0);
        ConsumerListItemResponse consumerListItemResponse = SelectBankingModeActivity.globalRegisterVerifyOtp.getData().getConsumerList().get(0);
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

        if (consumerListItemResponse.getEmailAddress() != null && !consumerListItemResponse.getEmailAddress().equals("")) {
            personalDetailsTwoBinding.etEmail.setText(consumerListItemResponse.getEmailAddress());
        }
    }

    private void setBinding() {
        personalDetailsTwoBinding = ActivityPersonalDetailsTwoBinding.inflate(getLayoutInflater());
        setContentView(personalDetailsTwoBinding.getRoot());
    }
}