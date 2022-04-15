package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PersonalDetailsViewModel extends BaseViewModel {

    public MutableLiveData<MobileNetworkResponse> occupationLiveData = new MutableLiveData<>();
    public MutableLiveData<MobileNetworkResponse> professionLiveData = new MutableLiveData<>();

    public void getOccupation(MobileNetworkPostParams mobileNetworkPostParams) {

        Call<MobileNetworkResponse> callableRes = AblApplication.apiInterface.getPurposeOfAccount(mobileNetworkPostParams);
        callableRes.enqueue(new Callback<MobileNetworkResponse>() {
            @Override
            public void onResponse(Call<MobileNetworkResponse> call, Response<MobileNetworkResponse> response) {
                if (response.code() == 200) {
                    occupationLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<MobileNetworkResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });

    }

    public void getProfession(MobileNetworkPostParams mobileNetworkPostParams) {

        Call<MobileNetworkResponse> callableRes = AblApplication.apiInterface.getPurposeOfAccount(mobileNetworkPostParams);
        callableRes.enqueue(new Callback<MobileNetworkResponse>() {
            @Override
            public void onResponse(Call<MobileNetworkResponse> call, Response<MobileNetworkResponse> response) {
                if (response.code() == 200) {
                    professionLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<MobileNetworkResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });

    }
}
