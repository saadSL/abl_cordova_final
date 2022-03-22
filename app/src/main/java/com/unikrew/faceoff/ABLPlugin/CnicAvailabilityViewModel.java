package com.unikrew.faceoff.ABLPlugin;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationNadraPostParams;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationNadraResponse;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationResponse;
import com.unikrew.faceoff.ABLPlugin.model.VerifyOtpBioMetricVerificationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.VerifyOtpBioMetricVerificationResponse;
import com.ofss.digx.mobile.android.allied.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CnicAvailabilityViewModel extends ViewModel {


    public MutableLiveData<BioMetricVerificationResponse> CnicSuccessLiveData = new MutableLiveData<BioMetricVerificationResponse>();
    public MutableLiveData<String> CnicErrorLiveData = new MutableLiveData<String>();
    public MutableLiveData<String> CnicVerifiedLiveData = new MutableLiveData<String>();




    private Activity activity;
    private AlertDialog loader;


    public void postCNIC(BioMetricVerificationPostParams cd, Activity myActivity) throws InterruptedException {
        this.activity = myActivity;

        Call<BioMetricVerificationResponse> callableRes = AblApplication.apiInterface.CNICpost(cd);
        callableRes.enqueue(new Callback<BioMetricVerificationResponse>() {
            @Override
            public void onResponse(Call<BioMetricVerificationResponse> call, Response<BioMetricVerificationResponse> response) {
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
            public void onFailure(Call<BioMetricVerificationResponse> call, Throwable t) {
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
