package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.remitter_details;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivityRemitterDetailsBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponseAccountInformation;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.remitter_details.PdaRemitterDetailListItem;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.remitter_details.RemitterDetailsDataModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.remitter_details.RemitterDetailsPostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.remitter_details.RemitterDetailsResponseModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AccountInformationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;
import java.util.List;

public class RemitterDetailsActivity extends BaseActivity {

    private ActivityRemitterDetailsBinding remitterDetailsBinding;
    private Boolean IS_RESUMED;
    private RegisterVerifyOtpResponse registerVerifyOtpResponse;
    private GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse;
    private List<PdaRemitterDetailListItem> pdaRemitterDetailList;
    private RemitterDetailsAdapter remitterDetailsAdapter;
    private RemitterDetailsViewModel remitterDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        getSharedPrefData();
        setViewModel();
        setObservers();
        setDetailsAdapter();
        clicks();
    }

    private void setObservers() {
        remitterDetailsViewModel.remitterLiveData.observe(this, new Observer<RemitterDetailsResponseModel>() {
            @Override
            public void onChanged(RemitterDetailsResponseModel remitterDetailsResponseModel) {
                dismissLoading();
            }
        });


        remitterDetailsViewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                dismissLoading();
                showAlert(Config.errorType, errMsg);
            }
        });
    }

    private void setViewModel() {
        remitterDetailsViewModel = new ViewModelProvider(this).get(RemitterDetailsViewModel.class);
    }

    private void clicks() {
        remitterDetailsBinding.addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });


        remitterDetailsBinding.layoutBtn.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        remitterDetailsBinding.layoutBtn.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidations();
            }
        });
    }

    private void checkValidations() {
        //loop will terminate if there is any empty field, else network call will be made
        for (int i = 0; i < pdaRemitterDetailList.size(); i++) {
            if (pdaRemitterDetailList.get(i).getRemitterName() == null || pdaRemitterDetailList.get(i).getRelationshipWithRemitter() == null) {
                showAlert(Config.errorType, getString(R.string.text_fields_error));
                return;
            }
        }

        Log.d("stats", "hello outside loop");
        showLoading();
        remitterDetailsViewModel.postRemitterDetails(getPostParams(),getStringFromPref(Config.ACCESS_TOKEN));

    }

    private RemitterDetailsPostModel getPostParams() {
        RemitterDetailsPostModel remitterDetailsPostModel =  new RemitterDetailsPostModel();
        RemitterDetailsDataModel remitterDetailsDataModel = remitterDetailsPostModel.getData();
        if (IS_RESUMED) {
            //flow for resumed application
            GetConsumerAccountDetailsResponseAccountInformation accountInformation = getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getAccountInformation();
            remitterDetailsDataModel.setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
            remitterDetailsDataModel.setRdaCustomerId(accountInformation.getRdaCustomerId());
            remitterDetailsDataModel.setBankingModeId(accountInformation.getBankingModeId());
            remitterDetailsDataModel.setCustomerBranch(accountInformation.getCustomerBranch());
            remitterDetailsDataModel.setPurposeOfAccountId(accountInformation.getPurposeOfAccountId());
        } else {
            //flow for new application
            AccountInformationResponse accountInformation = registerVerifyOtpResponse.getData().getConsumerList().get(0).getAccountInformation();
            remitterDetailsDataModel.setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
            remitterDetailsDataModel.setRdaCustomerId(accountInformation.getRdaCustomerId());
            remitterDetailsDataModel.setBankingModeId(accountInformation.getBankingModeId());
            remitterDetailsDataModel.setCustomerBranch(accountInformation.getCustomerBranch());
            remitterDetailsDataModel.setPurposeOfAccountId(accountInformation.getPurposeOfAccountId());
        }
        remitterDetailsDataModel.setCustomerTypeId(Config.CUSTOMER_TYPE_ID);
        remitterDetailsDataModel.setPhysicalCardInd(0);
        remitterDetailsDataModel.setAtmTypeId(null);
        remitterDetailsDataModel.setTransAlertInd(0);
        remitterDetailsDataModel.setChequeBookReqInd(null);
        remitterDetailsDataModel.setTransactionalAlertId(null);
        remitterDetailsDataModel.setNoOfJointApplicatns(0);
        remitterDetailsDataModel.getPdaRemitterDetailList().addAll(pdaRemitterDetailList);

        return remitterDetailsPostModel;
    }

    private void addItem() {
        pdaRemitterDetailList.add(new PdaRemitterDetailListItem());
        remitterDetailsAdapter.notifyItemInserted(pdaRemitterDetailList.size() - 1);
    }

    private void setDetailsAdapter() {
        pdaRemitterDetailList = new ArrayList<>();
        remitterDetailsAdapter = new RemitterDetailsAdapter(pdaRemitterDetailList, this);
        pdaRemitterDetailList.add(new PdaRemitterDetailListItem());
        remitterDetailsBinding.rvRemitterDetails.setLayoutManager(new LinearLayoutManager(this));
        remitterDetailsBinding.rvRemitterDetails.setAdapter(remitterDetailsAdapter);
    }

    private void setBinding() {
        remitterDetailsBinding = ActivityRemitterDetailsBinding.inflate(getLayoutInflater());
        setContentView(remitterDetailsBinding.getRoot());
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
}