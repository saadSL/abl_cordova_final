package com.unikrew.faceoff.ABLPlugin.ui.cnic_upload;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.phase2.view_apps_generate_otp.ViewAppsGenerateOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.phase2.view_apps_generate_otp.ViewAppsGenerateOtpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CnicUploadViewModel extends BaseViewModel {

    public MutableLiveData<ViewAppsGenerateOtpResponse> responseLiveData = new MutableLiveData<ViewAppsGenerateOtpResponse>();
    public MutableLiveData<String> responseErrorLiveData = new MutableLiveData<String>();

    public void viewAppsGenerateOtpPostData(ViewAppsGenerateOtpPostParams postParams){

        try {
            Call<ViewAppsGenerateOtpResponse> callableRes = AblApplication.apiInterface.viewAppsGenerateOtp(postParams);
            callableRes.enqueue(new Callback<ViewAppsGenerateOtpResponse>() {
                @Override
                public void onResponse(Call<ViewAppsGenerateOtpResponse> call, Response<ViewAppsGenerateOtpResponse> response) {
                    if ( response.code() == 200 ){
                        responseLiveData.postValue( response.body() );
                    }else{
                        responseErrorLiveData.postValue( getErrorDetail(response) );
                    }
                }

                @Override
                public void onFailure(Call<ViewAppsGenerateOtpResponse> call, Throwable t) {
                    responseErrorLiveData.postValue( t.getMessage() );
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
