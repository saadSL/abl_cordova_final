package com.unikrew.faceoff.ABLPlugin.base;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ofss.digx.mobile.android.allied.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Response;

public class BaseViewModel extends ViewModel {

    public AlertDialog loader;
    public MutableLiveData<String> errorLiveData = new MutableLiveData<String>();


    public String getErrorDetail(Response response) {
        String errorMsg = "";
        try {
            JSONObject jObjError = null;
            if (response.errorBody() != null) {

                jObjError = new JSONObject(response.errorBody().string());
                JSONObject message = jObjError.getJSONObject("message");
                errorMsg = "Status : "+message.getString("status")+
                        ".\nDescription : "+message.getString("description")+
                        ".\nError Detail : "+message.getString("errorDetail");

            }else{
                errorMsg = "Response error Body is empty !!!";
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return errorMsg;
    }


    public void showLoading(Activity activity) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setView(View.inflate(activity, R.layout.loader, null));
        builder1.setCancelable(false);
        loader = builder1.create();
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
