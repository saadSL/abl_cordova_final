package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.mobile_number;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileNumberViewModel extends BaseViewModel {

    public MutableLiveData<ViewAppsGenerateOtpResponse> responseLiveData = new MutableLiveData<ViewAppsGenerateOtpResponse>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<String>();

    public void viewAppsGenerateOtpPostData(ViewAppsGenerateOtpPostParams postParams){

        Call<ViewAppsGenerateOtpResponse> res = AblApplication.apiInterface.viewAppsGenerateOtp(postParams);
        res.enqueue(new Callback<ViewAppsGenerateOtpResponse>() {
            @Override
            public void onResponse(Call<ViewAppsGenerateOtpResponse> call, Response<ViewAppsGenerateOtpResponse> response) {
                if (response.code() == 200){
                    responseLiveData.postValue(response.body());
                }else{
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<ViewAppsGenerateOtpResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

}
