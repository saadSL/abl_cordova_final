package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_transaction;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.setup_transaction.SetupTransactionPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.setup_transaction.SetupTransactionResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCardViewModel extends BaseViewModel {

    MutableLiveData<SetupTransactionResponse> setupTransactionResponseMutableLiveData = new MutableLiveData<SetupTransactionResponse>();
    MutableLiveData<String> setupTransactionErrorLiveData = new MutableLiveData<String>();

    public void registerTransactionDetails(SetupTransactionPostParams postParams, String accessToken){
        Call<SetupTransactionResponse> callableRes = AblApplication.apiInterface.setupTransactionDetails(postParams, "Bearer "+accessToken);
        callableRes.enqueue(new Callback<SetupTransactionResponse>() {
            @Override
            public void onResponse(Call<SetupTransactionResponse> call, Response<SetupTransactionResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        setupTransactionResponseMutableLiveData.postValue(response.body());
                    }else{
                        setupTransactionErrorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    setupTransactionErrorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<SetupTransactionResponse> call, Throwable t) {
                setupTransactionErrorLiveData.postValue(t.getMessage());
            }
        });
    }
}
