package com.unikrew.faceoff.ABLPlugin.ui.cnicavailability;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.unikrew.faceoff.ABLPlugin.interfaces.AblApplication;
import com.unikrew.faceoff.ABLPlugin.model.CnicPostParams;
import com.unikrew.faceoff.ABLPlugin.model.OtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.OtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.ResponseDTO;
import com.unikrew.faceoff.ABLPlugin.model.UpdateBioMetricStatusPostParams;
import com.unikrew.faceoff.ABLPlugin.model.UpdateBioMetricStatusResponse;
import com.ofss.digx.mobile.android.allied.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CnicAvailabilityViewModel extends ViewModel {

    public MutableLiveData<ResponseDTO> CnicSuccessLiveData = new MutableLiveData<ResponseDTO>();
    public MutableLiveData<String> CnicErrorLiveData = new MutableLiveData<String>();
    public MutableLiveData<String> CnicVerifiedLiveData = new MutableLiveData<String>();


    Activity activity;
    AlertDialog loader;


    public void postCNIC(CnicPostParams cd, Activity myActivity) throws InterruptedException {
        this.activity = myActivity;

        Call<ResponseDTO> callableRes = AblApplication.apiInterface.CNICpost(cd);
        callableRes.enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                if (response.code() == 200) {
                    CnicSuccessLiveData.postValue(response.body());
                    loader.dismiss();
                } else if (response.code() == 403) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject message = jObjError.getJSONObject("message");
                        if (message.getString("status").equals("api_error")) {
                            CnicErrorLiveData.postValue(message.getString("errorDetail"));
                            loader.dismiss();
                        } else {
                            if (message.getString("status").equals("409")) {
                                CnicVerifiedLiveData.postValue(message.getString("description"));
                            } else if (message.getString("status").equals("401")) {
                                CnicErrorLiveData.postValue(message.getString("description"));
                            }

                            loader.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                CnicErrorLiveData.postValue(t.getMessage());
                loader.dismiss();
            }
        });
        showLoading();
        loader.show();
    }

    private void showLoading() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setView(View.inflate(activity, R.layout.loader, null));
        builder1.setCancelable(false);
        loader = builder1.create();
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
