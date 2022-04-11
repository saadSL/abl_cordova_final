package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.account_application;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ApplicationListLayoutBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.delete_drafted_application.DeleteDraftedApplicationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.delete_drafted_application.DeleteDraftedApplicationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerifyOtpResponseAppList;
import com.unikrew.faceoff.Config;

public class AccountApplicationActivity extends BaseActivity implements AccountApplicationInterface, View.OnClickListener {
    GetDraftedAppsVerifyOtpResponse res;
    AccountApplicationAdapter adapter;

    private ApplicationListLayoutBinding binding;
    private GetConsumerAccountDetailsPostParams consumerAccDetailspostParams;
    private DeleteDraftedApplicationPostParams deleteDraftedAppPostParams;
    private AccountApplicationViewModel viewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_list_layout);
        setBinding();
        set();
        initializeAdapter();
        observe();
    }

    private void observe() {
        viewModel.consumerAccountDetailsSuccessLiveData.observe(this, new Observer<GetConsumerAccountDetailsResponse>() {
            @Override
            public void onChanged(GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse) {
                showAlert(Config.successType,getConsumerAccountDetailsResponse.getMessage().getDescription());
                loader.dismiss();
            }
        });
        viewModel.consumerAccountDetailsErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType,errMsg);
                loader.dismiss();
            }
        });

        viewModel.deleteDraftedAppSuccessLiveData.observe(this, new Observer<DeleteDraftedApplicationResponse>() {
            @Override
            public void onChanged(DeleteDraftedApplicationResponse deleteDraftedApplicationResponse) {
                showAlert(Config.successType,deleteDraftedApplicationResponse.getMessage().getDescription());
                loader.dismiss();
            }
        });

        viewModel.deleteDraftedAppErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType,errMsg);
                loader.dismiss();
            }
        });
    }

    private void getConsumerAccountDetails() {
        setConsumerAccDetailsPostParams();
        viewModel.getConsumerAccDetails(
                consumerAccDetailspostParams,
                res.getData().getAccessToken());
        showLoading();
        loader.show();
    }

    private void setConsumerAccDetailsPostParams() {
        consumerAccDetailspostParams.getData().setRdaCustomerProfileId(
                res.getData().getAppList().get(0).getRdaCustomerProfileId()
        );
        consumerAccDetailspostParams.getData().setRdaCustomerAccInfoId(
                res.getData().getAppList().get(0).getRdaCustomerAccInfoId()
        );
        consumerAccDetailspostParams.getData().setCustomerTypeId(
                res.getData().getAppList().get(0).getCustomerTypeId()
        );
    }

    private void set() {
        res = (GetDraftedAppsVerifyOtpResponse) getIntent().getSerializableExtra(Config.APP_LIST);
        consumerAccDetailspostParams = new GetConsumerAccountDetailsPostParams();
        deleteDraftedAppPostParams = new DeleteDraftedApplicationPostParams();

        viewModel = new ViewModelProvider(this).get(AccountApplicationViewModel.class);

        binding.genBtn.btnNext.setOnClickListener(this);
        binding.createAppFab.setOnClickListener(this);

    }

    private void setBinding() {
        binding = ApplicationListLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initializeAdapter() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new AccountApplicationAdapter(res.getData().getAppList(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void deleteAppListAt(GetDraftedAppsVerifyOtpResponseAppList getDraftedAppsVerifyOtpResponseAppList) {
        setDeletePostParams(getDraftedAppsVerifyOtpResponseAppList);
        viewModel.deleteDraftedApplication(
                deleteDraftedAppPostParams,
                res.getData().getAccessToken() );
        showLoading();
        loader.show();
    }

    private void setDeletePostParams(GetDraftedAppsVerifyOtpResponseAppList getDraftedAppsVerifyOtpResponseAppList) {

        deleteDraftedAppPostParams.getData().setCustomerProfileId(
                getDraftedAppsVerifyOtpResponseAppList.getRdaCustomerProfileId()
        );
        deleteDraftedAppPostParams.getData().setCustomerAccountInfoId(
                getDraftedAppsVerifyOtpResponseAppList.getRdaCustomerAccInfoId()
        );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                getConsumerAccountDetails();
                break;
            case R.id.create_app_fab:
                showAlert(Config.successType,"Create Application");
                break;
        }
    }
}
