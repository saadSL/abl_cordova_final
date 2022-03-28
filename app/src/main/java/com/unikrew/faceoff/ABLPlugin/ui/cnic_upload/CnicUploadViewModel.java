package com.unikrew.faceoff.ABLPlugin.ui.cnic_upload;

import androidx.lifecycle.ViewModel;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.model.phase2.view_apps_generate_otp.ViewAppsGenerateOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.phase2.view_apps_generate_otp.ViewAppsGenerateOtpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CnicUploadViewModel extends ViewModel {

    public void postData(ViewAppsGenerateOtpPostParams postParams){
        Call<ViewAppsGenerateOtpResponse> callableRes = AblApplication.apiInterface.viewAppsGenerateOtp(postParams);
        callableRes.enqueue(new Callback<ViewAppsGenerateOtpResponse>() {
            @Override
            public void onResponse(Call<ViewAppsGenerateOtpResponse> call, Response<ViewAppsGenerateOtpResponse> response) {
                System.out.println("The Response Status : "+response.body());
//                System.out.println("The Response Message : "+response.body().getMessage().getDescription());

            }

            @Override
            public void onFailure(Call<ViewAppsGenerateOtpResponse> call, Throwable t) {
                System.out.println("Error : "+t.getMessage());
            }
        });
    }
}
