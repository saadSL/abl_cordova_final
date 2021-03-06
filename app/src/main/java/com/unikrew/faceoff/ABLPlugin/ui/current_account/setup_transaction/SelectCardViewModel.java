package com.unikrew.faceoff.ABLPlugin.ui.current_account.setup_transaction;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.setup_transaction.SetupTransactionPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.setup_transaction.SetupTransactionResponse;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodePostParams;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponse;
import com.unikrew.faceoff.ABLPlugin.model.current_account.setup_transactions.CurrentAccountSetupTransactionPostParams;
import com.unikrew.faceoff.ABLPlugin.model.current_account.setup_transactions.CurrentAccountSetupTransactionResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCardViewModel extends BaseViewModel {

    MutableLiveData<CurrentAccountSetupTransactionResponse> setupTransactionResponseMutableLiveData = new MutableLiveData<CurrentAccountSetupTransactionResponse>();
    MutableLiveData<String> setupTransactionErrorLiveData = new MutableLiveData<String>();

    public void registerTransactionDetails(CurrentAccountSetupTransactionPostParams postParams, String accessToken){
        Call<CurrentAccountSetupTransactionResponse> callableRes = AblApplication.apiInterface.currentAccountSetupTransactionDetails(postParams, "Bearer "+accessToken);
        callableRes.enqueue(new Callback<CurrentAccountSetupTransactionResponse>() {
            @Override
            public void onResponse(Call<CurrentAccountSetupTransactionResponse> call, Response<CurrentAccountSetupTransactionResponse> response) {
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
            public void onFailure(Call<CurrentAccountSetupTransactionResponse> call, Throwable t) {
                setupTransactionErrorLiveData.postValue(t.getMessage());
            }
        });
    }


    MutableLiveData<LookUpCodeResponse> atmCardsSuccessResponse = new MutableLiveData<LookUpCodeResponse>();

    public void getAtmCards(LookUpCodePostParams atmCardsPostParams) {
        Call<LookUpCodeResponse> callableRes = AblApplication.apiInterface.getLookUpResponse(atmCardsPostParams);
        callableRes.enqueue(new Callback<LookUpCodeResponse>() {
            @Override
            public void onResponse(Call<LookUpCodeResponse> call, Response<LookUpCodeResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        atmCardsSuccessResponse.postValue(response.body());
                    }else{
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<LookUpCodeResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    MutableLiveData<LookUpCodeResponse> visaCardSuccessResponse = new MutableLiveData<>();

    public void getVisaCardReasons(LookUpCodePostParams visaCardPostParams) {
        Call<LookUpCodeResponse> callableRes = AblApplication.apiInterface.getLookUpResponse(visaCardPostParams);
        callableRes.enqueue(new Callback<LookUpCodeResponse>() {
            @Override
            public void onResponse(Call<LookUpCodeResponse> call, Response<LookUpCodeResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        visaCardSuccessResponse.postValue(response.body());
                    }else{
                        errorLiveData.postValue(getErrorDetail(response));
                    }

                }else{
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<LookUpCodeResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    MutableLiveData<LookUpCodeResponse> cardDeliverySuccessResponse = new MutableLiveData<>();

    public void getCardDeliveryOptions(LookUpCodePostParams cardDeliveryPostParams) {
        Call<LookUpCodeResponse> callableRes = AblApplication.apiInterface.getLookUpResponse(cardDeliveryPostParams);
        callableRes.enqueue(new Callback<LookUpCodeResponse>() {
            @Override
            public void onResponse(Call<LookUpCodeResponse> call, Response<LookUpCodeResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        cardDeliverySuccessResponse.postValue(response.body());
                    }else{
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<LookUpCodeResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }
}
