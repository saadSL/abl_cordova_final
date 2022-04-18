package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.PostUserAddressModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.UserAddressResponseModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PersonalDetailsViewModel extends BaseViewModel {

    public MutableLiveData<MobileNetworkResponse> occupationLiveData = new MutableLiveData<>();
    public MutableLiveData<MobileNetworkResponse> professionLiveData = new MutableLiveData<>();

   public MutableLiveData<RegisterEmploymentDetailsResponse> registerEmploymentDetailsResponseMutableLiveData = new MutableLiveData<RegisterEmploymentDetailsResponse>();
   public MutableLiveData<UserAddressResponseModel> userAddressMutableLiveData = new MutableLiveData<UserAddressResponseModel>();

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


    public void postPersonalDetails(RegisterEmploymentDetailsPostParams postParams, String accessToken){
        Call<RegisterEmploymentDetailsResponse> callableRes = AblApplication.apiInterface.registerEmpDetails(postParams,"Bearer "+accessToken);
        callableRes.enqueue(new Callback<RegisterEmploymentDetailsResponse>() {
            @Override
            public void onResponse(Call<RegisterEmploymentDetailsResponse> call, Response<RegisterEmploymentDetailsResponse> response) {
                if (response.code()==200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        registerEmploymentDetailsResponseMutableLiveData.postValue(response.body());
                    }else{
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<RegisterEmploymentDetailsResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void userAddress(PostUserAddressModel userAddressModel, String accessToken) {
        Call<UserAddressResponseModel> callableRes = AblApplication.apiInterface.postUserAddress(userAddressModel, "Bearer " + accessToken);
        callableRes.enqueue(new Callback<UserAddressResponseModel>() {
            @Override
            public void onResponse(Call<UserAddressResponseModel> call, Response<UserAddressResponseModel> response) {
                if (response.code() == 200) {
                    if (response.body().getMessage().getStatus().equals("200")) {
                        userAddressMutableLiveData.postValue(response.body());
                    } else {
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                } else {
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<UserAddressResponseModel> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }
}
