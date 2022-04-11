package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.change_mobile_number;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.change_mobile_number.ChangeMobileNumberPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.change_mobile_number.ChangeMobileNumberResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeMobileNumberViewModel extends BaseViewModel {

    MutableLiveData<ChangeMobileNumberResponse> changeMobileNumSuccess = new MutableLiveData<ChangeMobileNumberResponse>();
    MutableLiveData<String> changeMobileNumError = new MutableLiveData<String>();

    public void changeMobileNumber(ChangeMobileNumberPostParams postParams){

        Call<ChangeMobileNumberResponse> callableRes = AblApplication.apiInterface.changeMobileNumber(postParams);
        callableRes.enqueue(new Callback<ChangeMobileNumberResponse>() {
            @Override
            public void onResponse(Call<ChangeMobileNumberResponse> call, Response<ChangeMobileNumberResponse> response) {
                if (response.code() == 200){
                    changeMobileNumSuccess.postValue(response.body());
                }else{
                    changeMobileNumError.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<ChangeMobileNumberResponse> call, Throwable t) {
                changeMobileNumError.postValue(t.getMessage());
            }
        });
    }
}
