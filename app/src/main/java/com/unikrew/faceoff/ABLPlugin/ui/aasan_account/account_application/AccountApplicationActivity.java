package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.account_application;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.employment_details.EmploymentDetailsActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details.PersonalDetailsOneActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details.PersonalDetailsTwoActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account.SelectAccountTypeActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account.SelectBankingModeActivity;
import com.unikrew.faceoff.Config;

public class AccountApplicationActivity extends BaseActivity implements AccountApplicationInterface, View.OnClickListener {
    GetDraftedAppsVerifyOtpResponse res;
    AccountApplicationAdapter adapter;

    private ApplicationListLayoutBinding layoutBinding;
    private GetConsumerAccountDetailsPostParams consumerAccDetailsPostParams;
    private DeleteDraftedApplicationPostParams deleteDraftedAppPostParams;
    private AccountApplicationViewModel viewModel;

    private GetDraftedAppsVerifyOtpResponseAppList selectedAppList;

    private Boolean deletePrompt = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        set();
        initializeAdapter();
        observe();
    }

    private void observe() {
        viewModel.consumerAccountDetailsSuccessLiveData.observe(this, new Observer<GetConsumerAccountDetailsResponse>() {
            @Override
            public void onChanged(GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse) {
                saveSerializableInPref("getConsumerAccountDetailsResponse",getConsumerAccountDetailsResponse);

                if (!getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getStepperSections().isSETUP_ACCOUNT_BANKING_MODE()) {
                    openActivity(SelectBankingModeActivity.class, getConsumerAccountDetailsResponse);
                } else if (!getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getStepperSections().isSETUP_ACCOUNT_TYPE()) {
                    Intent intent = new Intent(AccountApplicationActivity.this, SelectAccountTypeActivity.class);
                    intent.putExtra(Config.IS_RESUMED, true);
                    intent.putExtra("getConsumerAccountDetailsResponse", getConsumerAccountDetailsResponse);
                    startActivity(intent);
                } else if (!getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getStepperSections().isSETUP_ACCOUNT_INCOME()) {
                    showAlert(Config.successType, "Opening Account Income ");
                } else if (!getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getStepperSections().isPERSONAL_DETAIL_NAMES()) {
                    Intent intent = new Intent(AccountApplicationActivity.this, PersonalDetailsOneActivity.class);
                    intent.putExtra(Config.IS_RESUMED, true);
                    intent.putExtra("getConsumerAccountDetailsResponse", getConsumerAccountDetailsResponse);
                    startActivity(intent);
                } else if (!getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getStepperSections().isPERSONAL_DETAIL_ADDRESS()) {
                    Intent intent = new Intent(AccountApplicationActivity.this, PersonalDetailsTwoActivity.class);
                    intent.putExtra(Config.IS_RESUMED, true);
                    intent.putExtra("getConsumerAccountDetailsResponse", getConsumerAccountDetailsResponse);
                    startActivity(intent);
                } else if (!getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getStepperSections().isPERSONAL_DETAIL_EMPLOYMENT()) {
                    openActivity(EmploymentDetailsActivity.class, getConsumerAccountDetailsResponse);
                } else if (!getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getStepperSections().isTRANSACTIONAL_DETAIL()) {
                    showAlert(Config.successType, "Opening Transaction Detail ");
                } else if (!getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getStepperSections().isDOCUMENT_UPLOADER()) {
                    showAlert(Config.successType, "Opening Document Uploader ");
                } else {
                    showAlert(Config.successType, "Opening Summary Details ");
                }

                loader.dismiss();
            }
        });
        viewModel.consumerAccountDetailsErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                loader.dismiss();
            }
        });

        viewModel.deleteDraftedAppSuccessLiveData.observe(this, new Observer<DeleteDraftedApplicationResponse>() {
            @Override
            public void onChanged(DeleteDraftedApplicationResponse deleteDraftedApplicationResponse) {
                showAlert(Config.successType, deleteDraftedApplicationResponse.getMessage().getDescription());
                loader.dismiss();
            }
        });

        viewModel.deleteDraftedAppErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                loader.dismiss();
            }
        });
    }

    private void openActivity(final Class<? extends Activity> activityToOpen, GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse) {
        Intent intent = new Intent(this, activityToOpen);
        intent.putExtra(Config.RESPONSE, getConsumerAccountDetailsResponse);
        startActivity(intent);
    }

    private void getConsumerAccountDetails() {
        if (selectedAppList != null) {
            setConsumerAccDetailsPostParams();
            viewModel.getConsumerAccDetails(
                    consumerAccDetailsPostParams,
                    res.getData().getAccessToken());
            showLoading();
            loader.show();
        } else {
            showAlert(Config.errorType, "No application selected !!!");
        }


    }

    private void setConsumerAccDetailsPostParams() {
        consumerAccDetailsPostParams.getData().setRdaCustomerProfileId(
                selectedAppList.getRdaCustomerProfileId()
        );
        consumerAccDetailsPostParams.getData().setRdaCustomerAccInfoId(
                selectedAppList.getRdaCustomerAccInfoId()
        );
        consumerAccDetailsPostParams.getData().setCustomerTypeId(
                selectedAppList.getCustomerTypeId()
        );
    }

    private void set() {
        res = (GetDraftedAppsVerifyOtpResponse) getIntent().getSerializableExtra(Config.RESPONSE);
        consumerAccDetailsPostParams = new GetConsumerAccountDetailsPostParams();
        deleteDraftedAppPostParams = new DeleteDraftedApplicationPostParams();

        viewModel = new ViewModelProvider(this).get(AccountApplicationViewModel.class);

        layoutBinding.genBtn.btnNext.setOnClickListener(this);
        layoutBinding.createAppFab.setOnClickListener(this);
        layoutBinding.genBtn.btnNext.setOnClickListener(this);
        layoutBinding.genBtn.btnCancel.setOnClickListener(this);
    }

    private void setBinding() {
        layoutBinding = ApplicationListLayoutBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
    }

    private void initializeAdapter() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new AccountApplicationAdapter(res.getData().getAppList(), this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void deleteAppListAt(GetDraftedAppsVerifyOtpResponseAppList getDraftedAppsVerifyOtpResponseAppList, int position) {

        if (!deletePrompt) {
            View checkBoxView = View.inflate(this, R.layout.delete_dialogue, null);
            CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.del_cb);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    deletePrompt = true;
                }
            });

            AlertDialog.Builder deleteDialogue = new AlertDialog.Builder(this);
            deleteDialogue.setTitle("Delete Confirmation");
            deleteDialogue.setMessage("Are You Sure To Delete");
            deleteDialogue.setView(checkBoxView);

            deleteDialogue.setCancelable(false);
            deleteDialogue.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    setDeletePostParams(getDraftedAppsVerifyOtpResponseAppList);
                    viewModel.deleteDraftedApplication(
                            deleteDraftedAppPostParams,
                            res.getData().getAccessToken());
                    res.getData().getAppList().remove(position);
                    adapter.setList(res.getData().getAppList());
                    showLoading();
                    loader.show();
                }
            });

            deleteDialogue.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            deleteDialogue.show();
        } else {
            setDeletePostParams(getDraftedAppsVerifyOtpResponseAppList);
            viewModel.deleteDraftedApplication(
                    deleteDraftedAppPostParams,
                    res.getData().getAccessToken());
            res.getData().getAppList().remove(position);
            adapter.setList(res.getData().getAppList());
            showLoading();
            loader.show();
        }

    }

    @Override
    public void setSelectionAt(int position) {
        if (res.getData().getAppList().size() >= 0) {
            for (int i = 0; i < res.getData().getAppList().size(); i++) {
                res.getData().getAppList().get(i).setSelected(false);
            }
            res.getData().getAppList().get(position).setSelected(true);
            selectedAppList = res.getData().getAppList().get(position);
        }
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
        switch (view.getId()) {
            case R.id.btn_next:
                getConsumerAccountDetails();
                break;
            case R.id.create_app_fab:
                openSetupAccountActivity();
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    private void openSetupAccountActivity() {
        Intent intent = new Intent(this, SelectBankingModeActivity.class);
        startActivity(intent);
    }
}
