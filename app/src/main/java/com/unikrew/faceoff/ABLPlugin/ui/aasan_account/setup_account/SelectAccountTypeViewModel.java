package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.AccountTypePostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.AccountTypeResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkResponse;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodePostParams;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponse;
import com.unikrew.faceoff.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectAccountTypeViewModel extends BaseViewModel {

    public MutableLiveData<LookUpCodeResponse> purposeOfAccountLiveData = new MutableLiveData<>();
    public MutableLiveData<AccountTypeResponse> accountTypeResponseLiveData = new MutableLiveData<>();


    public void getPurposeOfAccount(LookUpCodePostParams mobileNetworkPostParams) {

        Call<LookUpCodeResponse> callableRes = AblApplication.apiInterface.getLookUpResponse(mobileNetworkPostParams);
        callableRes.enqueue(new Callback<LookUpCodeResponse>() {
            @Override
            public void onResponse(Call<LookUpCodeResponse> call, Response<LookUpCodeResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        purposeOfAccountLiveData.postValue(response.body());
                    }else{
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<LookUpCodeResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });

    }

    public void postAccountType(AccountTypePostParams accountTypeParams, String accessToken) {
        Call<AccountTypeResponse> callableRes = AblApplication.apiInterface.postAccountType(accountTypeParams ,"Bearer " + accessToken);
        callableRes.enqueue(new Callback<AccountTypeResponse>() {
            @Override
            public void onResponse(Call<AccountTypeResponse> call, Response<AccountTypeResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        accountTypeResponseLiveData.postValue(response.body());
                    }else{
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<AccountTypeResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });

    }

    MutableLiveData<LookUpCodeResponse> preferredAccountSuccess = new MutableLiveData<>();
    public void getPreferredAccount(LookUpCodePostParams postParams) {
        Call<LookUpCodeResponse> callableRes = AblApplication.apiInterface.getLookUpResponse(postParams);
        callableRes.enqueue(new Callback<LookUpCodeResponse>() {
            @Override
            public void onResponse(Call<LookUpCodeResponse> call, Response<LookUpCodeResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        preferredAccountSuccess.postValue(response.body());
                    }else{
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<LookUpCodeResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }
}
