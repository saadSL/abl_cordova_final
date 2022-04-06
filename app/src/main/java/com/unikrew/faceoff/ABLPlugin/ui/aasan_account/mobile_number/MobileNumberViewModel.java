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

//    MutableLiveData<MobileNetworkResponse> mobileNetworkResponse = new MutableLiveData<MobileNetworkResponse>();
//    MutableLiveData<String> mobileNetworkErrorLiveData = new MutableLiveData<String>();
//
//    public void postDataForMobileNetwork(MobileNetworkPostParams postParams,Activity activity){
//        try{
//            Call<MobileNetworkResponse> callableRes = AblApplication.apiInterface.getMobileNetworks(postParams);
//            callableRes.enqueue(new Callback<MobileNetworkResponse>() {
//                @Override
//                public void onResponse(Call<MobileNetworkResponse> call, Response<MobileNetworkResponse> response) {
//                    if (response.code() == 200){
//                        mobileNetworkResponse.postValue(response.body());
//                    }else {
//                        mobileNetworkErrorLiveData.postValue("Mobile Network Error \n"+getErrorDetail(response));
//                    }
//                    loader.dismiss();
//                }
//
//                @Override
//                public void onFailure(Call<MobileNetworkResponse> call, Throwable t) {
//                    mobileNetworkErrorLiveData.postValue(t.getMessage());
//                    loader.dismiss();
//                }
//            });
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        showLoading(activity);
//        loader.show();
//    }

    public MutableLiveData<ViewAppsGenerateOtpResponse> responseLiveData = new MutableLiveData<ViewAppsGenerateOtpResponse>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<String>();

    public void viewAppsGenerateOtpPostData(ViewAppsGenerateOtpPostParams postParams,Activity activity){

        Call<ViewAppsGenerateOtpResponse> res = AblApplication.apiInterface.viewAppsGenerateOtp(postParams);
        res.enqueue(new Callback<ViewAppsGenerateOtpResponse>() {
            @Override
            public void onResponse(Call<ViewAppsGenerateOtpResponse> call, Response<ViewAppsGenerateOtpResponse> response) {
                if (response.code() == 200){
                    responseLiveData.postValue(response.body());
                }else{
                    errorLiveData.postValue(getErrorDetail(response));
                }
                loader.dismiss();
            }

            @Override
            public void onFailure(Call<ViewAppsGenerateOtpResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
                loader.dismiss();
            }
        });
        showLoading(activity);
        loader.show();
    }

}
