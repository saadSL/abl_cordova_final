package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode.BranchesModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode.GetBranchPostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtp;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectBankingModeViewModel extends BaseViewModel {

    public MutableLiveData<BranchesModel> branchesLiveData = new MutableLiveData<>();

    public MutableLiveData<RegisterVerifyOtpResponse> registerOtpLiveData = new MutableLiveData<>();

    public void getBranches(GetBranchPostModel getBranchPostModel) {

        Call<BranchesModel> callableRes = AblApplication.apiInterface.getBranches(getBranchPostModel);
        callableRes.enqueue(new Callback<BranchesModel>() {
            @Override
            public void onResponse(Call<BranchesModel> call, Response<BranchesModel> response) {
                if (response.code() == 200) {
                    branchesLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<BranchesModel> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });

    }



    public void postBankingMethod(RegisterVerifyOtp registerVerifyOtp){
        Call<RegisterVerifyOtpResponse> callableRes = AblApplication.apiInterface.registerVerifyOtp(registerVerifyOtp);
        callableRes.enqueue(new Callback<RegisterVerifyOtpResponse>() {
            @Override
            public void onResponse(Call<RegisterVerifyOtpResponse> call, Response<RegisterVerifyOtpResponse> response) {
                if (response.code() == 200) {
                    registerOtpLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<RegisterVerifyOtpResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });

    }

}
