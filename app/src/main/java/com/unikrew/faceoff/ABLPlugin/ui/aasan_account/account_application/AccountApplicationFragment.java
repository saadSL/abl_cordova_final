package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.account_application;

import static androidx.navigation.ViewKt.findNavController;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ApplicationListItemBinding;
import com.ofss.digx.mobile.android.allied.databinding.ApplicationListLayoutBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseFragment;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.delete_drafted_application.DeleteDraftedApplicationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.delete_drafted_application.DeleteDraftedApplicationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerifyOtpResponseAppList;
import com.unikrew.faceoff.Config;

public class AccountApplicationFragment extends BaseFragment implements AccountApplicationInterface, View.OnClickListener {
    GetDraftedAppsVerifyOtpResponse res;
    AccountApplicationAdapter adapter;

    private ApplicationListLayoutBinding layoutBinding;
    ApplicationListItemBinding itemBinding;
    private GetConsumerAccountDetailsPostParams consumerAccDetailsPostParams;
    private DeleteDraftedApplicationPostParams deleteDraftedAppPostParams;
    private AccountApplicationViewModel viewModel;

    private GetDraftedAppsVerifyOtpResponseAppList selectedAppList;

    private Boolean deletePrompt = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBinding = ApplicationListLayoutBinding.inflate(inflater, container, false);

        set();
        initializeAdapter();
        observe();


        return layoutBinding.getRoot();
    }

    private void setLayout() {
        layoutBinding.screenHeader.stepsHeading1.setText("Resume");
        layoutBinding.screenHeader.stepsHeading2.setText("Application");
        set();
        initializeAdapter();
        setLogoLayout(layoutBinding.logoToolbar.tvDate);
    }

    private void observe() {
        viewModel.consumerAccountDetailsSuccessLiveData.observe(getViewLifecycleOwner(), new Observer<GetConsumerAccountDetailsResponse>() {
            @Override
            public void onChanged(GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse) {
                saveSerializableInPref(Config.GET_CONSUMER_RESPONSE, getConsumerAccountDetailsResponse);

                if (!getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getStepperSections().isSETUP_ACCOUNT_BANKING_MODE()) {

                    findNavController(layoutBinding.getRoot()).navigate(R.id.openBankingMode);

                } else if (!getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getStepperSections().isSETUP_ACCOUNT_TYPE()) {

                    findNavController(layoutBinding.getRoot()).navigate(R.id.openAccountType);

                } else if (!getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getStepperSections().isSETUP_ACCOUNT_INCOME()) {

                    findNavController(layoutBinding.getRoot()).navigate(R.id.openPreferredAccount);

                } else if (!getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getStepperSections().isPERSONAL_DETAIL_NAMES()) {

                    findNavController(layoutBinding.getRoot()).navigate(R.id.openPersonalDetailsOne);

                } else if (!getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getStepperSections().isPERSONAL_DETAIL_ADDRESS()) {

                    findNavController(layoutBinding.getRoot()).navigate(R.id.openPersonalDetailsTwo);

                } else if (!getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getStepperSections().isPERSONAL_DETAIL_EMPLOYMENT()) {

                    findNavController(layoutBinding.getRoot()).navigate(R.id.openPersonalDetailsThree);

                } else if (!getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getStepperSections().isTRANSACTIONAL_DETAIL()) {

                    findNavController(layoutBinding.getRoot()).navigate(R.id.openSelectCard);

                } else if (!getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getStepperSections().isDOCUMENT_UPLOADER()) {

                    findNavController(layoutBinding.getRoot()).navigate(R.id.openUploadDocument);

                } else {
                    showAlert(Config.successType, "Opening Summary Details ");
                }

                dismissLoading();
            }
        });
        viewModel.consumerAccountDetailsErrorLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                dismissLoading();
            }
        });

        viewModel.deleteDraftedAppSuccessLiveData.observe(getViewLifecycleOwner(), new Observer<DeleteDraftedApplicationResponse>() {
            @Override
            public void onChanged(DeleteDraftedApplicationResponse deleteDraftedApplicationResponse) {
                showAlert(Config.successType, deleteDraftedApplicationResponse.getMessage().getDescription());
                dismissLoading();
            }
        });

        viewModel.deleteDraftedAppErrorLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                dismissLoading();
            }
        });
    }

    private void openActivity(final Class<? extends Activity> activityToOpen) {
        Intent intent = new Intent(mContext, activityToOpen);
        startActivity(intent);
    }

    private void getConsumerAccountDetails() {
        if (selectedAppList != null) {
            setConsumerAccDetailsPostParams();
            saveStringInPref(Config.PROFILE_ID, String.valueOf(selectedAppList.getRdaCustomerProfileId()));
            saveStringInPref(Config.ACCOUNT_INFO_ID, String.valueOf(selectedAppList.getRdaCustomerAccInfoId()));
            viewModel.getConsumerAccDetails(
                    consumerAccDetailsPostParams,
                    getStringFromPref(Config.ACCESS_TOKEN));
            showLoading();
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
        res = (GetDraftedAppsVerifyOtpResponse) getArguments().getSerializable(Config.RESPONSE);
        consumerAccDetailsPostParams = new GetConsumerAccountDetailsPostParams();
        deleteDraftedAppPostParams = new DeleteDraftedApplicationPostParams();

        viewModel = new ViewModelProvider(this).get(AccountApplicationViewModel.class);

        layoutBinding.btnContainer.btnNext.setOnClickListener(this);
        layoutBinding.createAppFab.setOnClickListener(this);
        layoutBinding.btnContainer.btBack.setOnClickListener(this);
    }


    private void initializeAdapter() {
        adapter = new AccountApplicationAdapter(res.getData().getAppList(), this, mContext);
        layoutBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        layoutBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void deleteAppListAt(GetDraftedAppsVerifyOtpResponseAppList getDraftedAppsVerifyOtpResponseAppList, int position) {

        if (!deletePrompt) {
            View checkBoxView = View.inflate(mContext, R.layout.delete_dialogue, null);
            CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.del_cb);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    deletePrompt = true;
                }
            });

            AlertDialog.Builder deleteDialogue = new AlertDialog.Builder(mContext);
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
                findNavController(layoutBinding.getRoot()).navigate(R.id.openBankingMode);
                break;
            case R.id.bt_back:
//                finish();
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        setLayout();
    }

}