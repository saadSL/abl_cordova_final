package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.review_documents;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewDocumentViewModel extends BaseViewModel {

    MutableLiveData<GetConsumerAccountDetailsResponse> accDetailsSuccess = new MutableLiveData<>();


    public void getAccountDetails(GetConsumerAccountDetailsPostParams accountDetailsPostParams, String accessToken) {
        Call<GetConsumerAccountDetailsResponse> callableRes = AblApplication.apiInterface.getConsumerAccDetails(accountDetailsPostParams,accessToken);
        callableRes.enqueue(new Callback<GetConsumerAccountDetailsResponse>() {
            @Override
            public void onResponse(Call<GetConsumerAccountDetailsResponse> call, Response<GetConsumerAccountDetailsResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        accDetailsSuccess.postValue(response.body());
                    }else{
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<GetConsumerAccountDetailsResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }
}
