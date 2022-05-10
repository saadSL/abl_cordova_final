package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.review_documents;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewDocumentViewModel extends BaseViewModel {

    public void getAccountDetails(GetConsumerAccountDetailsPostParams accountDetailsPostParams, String accessToken) {
        Call<GetConsumerAccountDetailsResponse> callableRes = AblApplication.apiInterface.getConsumerAccDetails(accountDetailsPostParams,accessToken);
        callableRes.enqueue(new Callback<GetConsumerAccountDetailsResponse>() {
            @Override
            public void onResponse(Call<GetConsumerAccountDetailsResponse> call, Response<GetConsumerAccountDetailsResponse> response) {
                if (response.code() == )
            }

            @Override
            public void onFailure(Call<GetConsumerAccountDetailsResponse> call, Throwable t) {

            }
        });
    }
}
