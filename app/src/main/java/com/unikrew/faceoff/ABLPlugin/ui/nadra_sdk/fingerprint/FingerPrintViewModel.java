package com.unikrew.faceoff.ABLPlugin.ui.nadra_sdk.fingerprint;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationNadraPostParams;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationNadraResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FingerPrintViewModel extends ViewModel {

    private Activity activity;
    private AlertDialog loader;



    public MutableLiveData<BioMetricVerificationNadraResponse> BioMetricStatusSuccessLiveData = new MutableLiveData<BioMetricVerificationNadraResponse>();
    public MutableLiveData<String> BioMetricStatusErrorLiveData = new MutableLiveData<String>();

    public void updateBioMetricStatus(BioMetricVerificationNadraPostParams pp, String accessToken, Activity myActivity) {

        this.activity = myActivity;
        Call<BioMetricVerificationNadraResponse> callableRes = AblApplication.apiInterface.UpdateBioMetricStatus(pp, "Bearer " + accessToken);
        callableRes.enqueue(new Callback<BioMetricVerificationNadraResponse>() {
            @Override
            public void onResponse(Call<BioMetricVerificationNadraResponse> call, Response<BioMetricVerificationNadraResponse> response) {
                if (response.code() == 200) {
                    BioMetricStatusSuccessLiveData.postValue(response.body());
                    loader.dismiss();
                } else if (response.code() == 403) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String msg = jObjError.getJSONObject("message").getString("description");
                        BioMetricStatusErrorLiveData.postValue(msg);
                        loader.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BioMetricVerificationNadraResponse> call, Throwable t) {
                BioMetricStatusErrorLiveData.postValue(t.getMessage());
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
