package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.account_application;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.delete_drafted_application.DeleteDraftedApplicationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.delete_drafted_application.DeleteDraftedApplicationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountApplicationViewModel extends BaseViewModel {

    MutableLiveData<GetConsumerAccountDetailsResponse> consumerAccountDetailsSuccessLiveData = new MutableLiveData<GetConsumerAccountDetailsResponse>();
    MutableLiveData<String> consumerAccountDetailsErrorLiveData = new MutableLiveData<String>();


    MutableLiveData<DeleteDraftedApplicationResponse> deleteDraftedAppSuccessLiveData = new MutableLiveData<DeleteDraftedApplicationResponse>();
    MutableLiveData<String> deleteDraftedAppErrorLiveData = new MutableLiveData<String>();

    public void getConsumerAccDetails(GetConsumerAccountDetailsPostParams postParams,String accessToken){

        Call<GetConsumerAccountDetailsResponse> callableRes = AblApplication.apiInterface.getConsumerAccDetails(postParams,"Bearer "+accessToken);
        callableRes.enqueue(new Callback<GetConsumerAccountDetailsResponse>() {
            @Override
            public void onResponse(Call<GetConsumerAccountDetailsResponse> call, Response<GetConsumerAccountDetailsResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        consumerAccountDetailsSuccessLiveData.postValue(response.body());
                    }else{
                        consumerAccountDetailsErrorLiveData.postValue(response.body().getMessage().getDescription());
                    }

                }else{
                    consumerAccountDetailsErrorLiveData.postValue( getErrorDetail(response) );
                }
            }

            @Override
            public void onFailure(Call<GetConsumerAccountDetailsResponse> call, Throwable t) {
                consumerAccountDetailsErrorLiveData.postValue(t.getMessage());
            }
        });
    }


    public void deleteDraftedApplication(DeleteDraftedApplicationPostParams postParams,String accessToken){

        Call<DeleteDraftedApplicationResponse> callableRes = AblApplication.apiInterface.deleteDraftedApplication(postParams,"Bearer "+accessToken);
        callableRes.enqueue(new Callback<DeleteDraftedApplicationResponse>() {
            @Override
            public void onResponse(Call<DeleteDraftedApplicationResponse> call, Response<DeleteDraftedApplicationResponse> response) {
                if (response.code() == 200){
                    deleteDraftedAppSuccessLiveData.postValue(response.body());
                }else{
                    deleteDraftedAppErrorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<DeleteDraftedApplicationResponse> call, Throwable t) {
                deleteDraftedAppErrorLiveData.postValue(t.getMessage());
            }
        });
    }

}
