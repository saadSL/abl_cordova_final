package com.unikrew.faceoff.ABLPlugin.ui.current_account.kin_details;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.common.register_consumer_basic_info.RegisterConsumerBasicInfoPostParams;
import com.unikrew.faceoff.ABLPlugin.model.common.register_consumer_basic_info.RegisterConsumerBasicInfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KinDetailsViewModel extends BaseViewModel {

    MutableLiveData<RegisterConsumerBasicInfoResponse> kinDetailsSuccessResponse = new MutableLiveData<>();

    public void postKinDetails(RegisterConsumerBasicInfoPostParams registerKinDetailsPostParams, String accessToken) {
        Call<RegisterConsumerBasicInfoResponse> callableRes = AblApplication.apiInterface.postKinDetails(registerKinDetailsPostParams,"Bearer "+accessToken);
        callableRes.enqueue(new Callback<RegisterConsumerBasicInfoResponse>() {
            @Override
            public void onResponse(Call<RegisterConsumerBasicInfoResponse> call, Response<RegisterConsumerBasicInfoResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        kinDetailsSuccessResponse.postValue(response.body());
                    }else{
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<RegisterConsumerBasicInfoResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }
}
