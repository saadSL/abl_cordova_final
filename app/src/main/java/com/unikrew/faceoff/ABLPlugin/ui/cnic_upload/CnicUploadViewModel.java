package com.unikrew.faceoff.ABLPlugin.ui.cnic_upload;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.model.phase2.view_apps_generate_otp.ViewAppsGenerateOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.phase2.view_apps_generate_otp.ViewAppsGenerateOtpResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CnicUploadViewModel extends ViewModel {

    public MutableLiveData<ViewAppsGenerateOtpResponse> responseLiveData = new MutableLiveData<ViewAppsGenerateOtpResponse>();
    public MutableLiveData<String> responseErrorLiveData = new MutableLiveData<String>();

    public void postData(ViewAppsGenerateOtpPostParams postParams){

        try {
            Call<ViewAppsGenerateOtpResponse> callableRes = AblApplication.apiInterface.viewAppsGenerateOtp(postParams);
            callableRes.enqueue(new Callback<ViewAppsGenerateOtpResponse>() {
                @Override
                public void onResponse(Call<ViewAppsGenerateOtpResponse> call, Response<ViewAppsGenerateOtpResponse> response) {
                    if (response.code() == 200){
                        responseLiveData.postValue(response.body());
                    }else if (response.code() == 403){
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            JSONObject message = jObjError.getJSONObject("message");
                            responseErrorLiveData.postValue(message.getString("errorDetail"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ViewAppsGenerateOtpResponse> call, Throwable t) {
                    System.out.println("Error : "+t.getMessage());
                }
            });
        }catch (Exception e){

        }

    }
}
