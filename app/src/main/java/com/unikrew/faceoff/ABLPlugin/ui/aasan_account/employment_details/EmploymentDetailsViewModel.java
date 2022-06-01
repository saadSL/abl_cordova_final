package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.employment_details;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.occupation.OccupationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.occupation.OccupationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.profession.ProfessionPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.profession.ProfessionResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycResponse;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodePostParams;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponse;
import com.unikrew.faceoff.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmploymentDetailsViewModel extends BaseViewModel {
    MutableLiveData<LookUpCodeResponse> occupationResponseMutableLiveData = new MutableLiveData<LookUpCodeResponse>();
    MutableLiveData<String> occupationErrorLiveData = new MutableLiveData<String>();

    MutableLiveData<LookUpCodeResponse> professionResponseMutableLiveData = new MutableLiveData<LookUpCodeResponse>();
    MutableLiveData<String> professionErrorLiveData = new MutableLiveData<String>();

    MutableLiveData<RegisterEmploymentDetailsResponse> registerEmploymentDetailsResponseMutableLiveData = new MutableLiveData<RegisterEmploymentDetailsResponse>();
    MutableLiveData<String> registerEmployeeDetailsErrorLiveData = new MutableLiveData<String>();

    MutableLiveData<SaveKycResponse> saveKycResponseMutableLiveData = new MutableLiveData<SaveKycResponse>();
    MutableLiveData<String> saveKycResponseErrorLiveData = new MutableLiveData<String>();

    public void postOccupationData(LookUpCodePostParams occupationPostParams) {
        Call<LookUpCodeResponse> callableRes = AblApplication.apiInterface.getLookUpResponse(occupationPostParams);
        callableRes.enqueue(new Callback<LookUpCodeResponse>() {
            @Override
            public void onResponse(Call<LookUpCodeResponse> call, Response<LookUpCodeResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        occupationResponseMutableLiveData.postValue(response.body());
                    }else{
                        occupationErrorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    occupationErrorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<LookUpCodeResponse> call, Throwable t) {
                occupationErrorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void postProfessionData(LookUpCodePostParams professionPostParams) {
        Call<LookUpCodeResponse> callableRes = AblApplication.apiInterface.getLookUpResponse(professionPostParams);
        callableRes.enqueue(new Callback<LookUpCodeResponse>() {
            @Override
            public void onResponse(Call<LookUpCodeResponse> call, Response<LookUpCodeResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        professionResponseMutableLiveData.postValue(response.body());
                    }else{
                        professionErrorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    professionErrorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<LookUpCodeResponse> call, Throwable t) {
                professionErrorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void registerEmpDetails(RegisterEmploymentDetailsPostParams postParams, String accessToken){
//        Call<RegisterEmploymentDetailsResponse> callableRes = AblApplication.apiInterface.registerEmpDetails(postParams,"Bearer "+accessToken);
//        callableRes.enqueue(new Callback<RegisterEmploymentDetailsResponse>() {
//            @Override
//            public void onResponse(Call<RegisterEmploymentDetailsResponse> call, Response<RegisterEmploymentDetailsResponse> response) {
//                if (response.code()==200){
//                    if (response.body().getMessage().getStatus().equals("200")){
//                        registerEmploymentDetailsResponseMutableLiveData.postValue(response.body());
//                    }else{
//                        registerEmployeeDetailsErrorLiveData.postValue(getErrorDetail(response));
//                    }
//                }else{
//                    registerEmployeeDetailsErrorLiveData.postValue(getErrorDetail(response));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RegisterEmploymentDetailsResponse> call, Throwable t) {
//                registerEmployeeDetailsErrorLiveData.postValue(t.getMessage());
//            }
//        });
    }

    public void saveKyc(SaveKycPostParams saveKycPostParams, String accessToken) {
        Call<SaveKycResponse> callableRes = AblApplication.apiInterface.saveMonthlySalary(saveKycPostParams, "Bearer "+accessToken);
        callableRes.enqueue(new Callback<SaveKycResponse>() {
            @Override
            public void onResponse(Call<SaveKycResponse> call, Response<SaveKycResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        saveKycResponseMutableLiveData.postValue(response.body());
                    }else{
                        saveKycResponseErrorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    saveKycResponseErrorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<SaveKycResponse> call, Throwable t) {
                saveKycResponseErrorLiveData.postValue(t.getMessage());
            }
        });
    }
}
