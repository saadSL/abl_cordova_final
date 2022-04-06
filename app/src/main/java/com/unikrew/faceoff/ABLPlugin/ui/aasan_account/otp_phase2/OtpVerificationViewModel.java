package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.otp_phase2;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerfiyOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerifyOtpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerificationViewModel extends BaseViewModel {

    public MutableLiveData<GetDraftedAppsVerifyOtpResponse> otpVerificationResponseLiveData = new MutableLiveData<GetDraftedAppsVerifyOtpResponse>();
    public MutableLiveData<String> otpVerificationErrorLiveData = new MutableLiveData<String>();

    public void getDraftedAppsVerifyOtp(GetDraftedAppsVerfiyOtpPostParams postParams){
        Call<GetDraftedAppsVerifyOtpResponse> callableRes = AblApplication.apiInterface.getDraftedAppsVerifyOtp(postParams);

        callableRes.enqueue(new Callback<GetDraftedAppsVerifyOtpResponse>() {
            @Override
            public void onResponse(Call<GetDraftedAppsVerifyOtpResponse> call, Response<GetDraftedAppsVerifyOtpResponse> response) {
                if (response.code() == 200){
                    otpVerificationResponseLiveData.postValue(response.body());
                }else{
                    otpVerificationErrorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<GetDraftedAppsVerifyOtpResponse> call, Throwable t) {
                otpVerificationErrorLiveData.postValue(t.getMessage());
            }
        });
    }
}
