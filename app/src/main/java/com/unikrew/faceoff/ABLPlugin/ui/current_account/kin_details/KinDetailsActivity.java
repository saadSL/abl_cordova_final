package com.unikrew.faceoff.ABLPlugin.ui.current_account.kin_details;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.LayoutKinDetailsBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.ConsumerListItemResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.common.register_consumer_basic_info.RegisterConsumerBasicInfoPostConsumerList;
import com.unikrew.faceoff.ABLPlugin.model.common.register_consumer_basic_info.RegisterConsumerBasicInfoPostParams;
import com.unikrew.faceoff.ABLPlugin.model.common.register_consumer_basic_info.RegisterConsumerBasicInfoResponse;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.upload_document.UploadDocumentActivity;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;
import java.util.List;

public class KinDetailsActivity extends BaseActivity implements View.OnClickListener {
    private LayoutKinDetailsBinding kinDetailsBinding;
    private KinDetailsViewModel kinDetailsViewModel;
    private RegisterConsumerBasicInfoPostParams registerKinDetailsPostParams;
    private List<ConsumerListItemResponse> consumerList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setClicks();
        setViewModel();
        getSharedPrefData();
        observe();
    }

    private void observe() {
        kinDetailsViewModel.kinDetailsSuccessResponse.observe(this, new Observer<RegisterConsumerBasicInfoResponse>() {
            @Override
            public void onChanged(RegisterConsumerBasicInfoResponse registerConsumerBasicInfoResponse) {
                dismissLoading();
                openUploadDocumentActivity();
            }
        });

        kinDetailsViewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                dismissLoading();
                showAlert(Config.errorType, errMsg);
            }
        });
    }

    private void openUploadDocumentActivity() {
        openActivity(UploadDocumentActivity.class);
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
        kinDetailsViewModel = new ViewModelProvider(this).get(KinDetailsViewModel.class);
        registerKinDetailsPostParams = new RegisterConsumerBasicInfoPostParams();
    }

    private void setClicks() {
        kinDetailsBinding.btnContainer.btnNext.setOnClickListener(this);
    }

    private void setBinding() {
        kinDetailsBinding = LayoutKinDetailsBinding.inflate(getLayoutInflater());
        setContentView(kinDetailsBinding.getRoot());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                postKinDetails();
                break;
            case R.id.bt_back:
                finish();
                break;
        }
    }

    private void postKinDetails() {
        if (isValid()) {
            setKinDetailsPostParams();
            kinDetailsViewModel.postKinDetails(registerKinDetailsPostParams, getStringFromPref(Config.ACCESS_TOKEN));
            showLoading();
        }
    }

    private void setKinDetailsPostParams() {
        ArrayList<RegisterConsumerBasicInfoPostConsumerList> registerConsumerBasicInfoPostConsumerLists = new ArrayList<>();

        RegisterConsumerBasicInfoPostConsumerList consumer = new RegisterConsumerBasicInfoPostConsumerList();

        consumer.setRdaCustomerProfileId(consumerList.get(0).getRdaCustomerProfileId());
        consumer.setRdaCustomerAccInfoId(consumerList.get(0).getAccountInformation().getRdaCustomerAccInfoId());
        consumer.setCustomerTypeId(consumerList.get(0).getCustomerTypeId());

        consumer.setKinName(kinDetailsBinding.etKinName.getText().toString());
        consumer.setKinCnic(kinDetailsBinding.etKinCnic.getText().toString());
        consumer.setKinMobile(kinDetailsBinding.etKinMobileNumber.getText().toString());
        consumer.setNationalityTypeId(null);

        registerConsumerBasicInfoPostConsumerLists.add(consumer);

        registerKinDetailsPostParams.getData().setConsumerList(registerConsumerBasicInfoPostConsumerLists);
    }

    private boolean isValid() {
        if (isEmpty(kinDetailsBinding.etKinName)) {
            showAlert(Config.errorType, "Please Enter Kin Name !!!");
            return false;
        }

        if (isEmpty(kinDetailsBinding.etKinCnic)) {
            showAlert(Config.errorType, "Please Enter Kin CNIC !!!");
            return false;
        }

        if (isEmpty(kinDetailsBinding.etKinMobileNumber)) {
            showAlert(Config.errorType, "Please Enter Kin Mobile Number !!!");
            return false;
        }
        return true;
    }
}
