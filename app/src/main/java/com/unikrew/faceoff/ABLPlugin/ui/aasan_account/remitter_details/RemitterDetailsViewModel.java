package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.remitter_details;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.remitter_details.RemitterDetailsPostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.remitter_details.RemitterDetailsResponseModel;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemitterDetailsViewModel extends BaseViewModel {
    public MutableLiveData<RemitterDetailsResponseModel> remitterLiveData = new MutableLiveData<>();

    public void postRemitterDetails(RemitterDetailsPostModel remitterDetailsPostModel, String accessToken) {

        Call<RemitterDetailsResponseModel> callableRes = AblApplication.apiInterface.postRemitterDetails(remitterDetailsPostModel,"Bearer " + accessToken);
        callableRes.enqueue(new Callback<RemitterDetailsResponseModel>() {
            @Override
            public void onResponse(Call<RemitterDetailsResponseModel> call, Response<RemitterDetailsResponseModel> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        remitterLiveData.postValue(response.body());
                    }else{
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<RemitterDetailsResponseModel> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });

    }
}
