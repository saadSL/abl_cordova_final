package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.fatca_details.FatcaDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.fatca_details.FatcaDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.freelancer_tax_info.FreelancerTaxPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.freelancer_tax_info.FreelancerTaxResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_one.PersonalDetailsOnePostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_three.PersonalDetailsThreePostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_two.PersonalDetailsTwoPostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.PostUserAddressModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.UserAddressResponseModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmployeeDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.tin_unavailability_reasons.TinUnavailabilityReasonsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.tin_unavailability_reasons.TinUnavailabilityReasonsResponse;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodePostParams;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponse;
import com.unikrew.faceoff.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PersonalDetailsViewModel extends BaseViewModel {

    public MutableLiveData<LookUpCodeResponse> occupationLiveData = new MutableLiveData<>();
    public MutableLiveData<LookUpCodeResponse> professionLiveData = new MutableLiveData<>();

    public MutableLiveData<RegisterEmploymentDetailsResponse> registerEmploymentDetailsResponseMutableLiveData = new MutableLiveData<RegisterEmploymentDetailsResponse>();
    public MutableLiveData<UserAddressResponseModel> userAddressMutableLiveData = new MutableLiveData<UserAddressResponseModel>();

    public MutableLiveData<SaveKycResponse> saveKycResponseMutableLiveData = new MutableLiveData<SaveKycResponse>();

    public void getOccupation(LookUpCodePostParams mobileNetworkPostParams) {

        Call<LookUpCodeResponse> callableRes = AblApplication.apiInterface.getLookUpResponse(mobileNetworkPostParams);
        callableRes.enqueue(new Callback<LookUpCodeResponse>() {
            @Override
            public void onResponse(Call<LookUpCodeResponse> call, Response<LookUpCodeResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        occupationLiveData.postValue(response.body());
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

    public void getProfession(LookUpCodePostParams mobileNetworkPostParams) {

        Call<LookUpCodeResponse> callableRes = AblApplication.apiInterface.getLookUpResponse(mobileNetworkPostParams);
        callableRes.enqueue(new Callback<LookUpCodeResponse>() {
            @Override
            public void onResponse(Call<LookUpCodeResponse> call, Response<LookUpCodeResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        professionLiveData.postValue(response.body());
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


    public void postPersonalDetailsOne(PersonalDetailsOnePostModel postParams, String accessToken) {
        Call<RegisterEmploymentDetailsResponse> callableRes = AblApplication.apiInterface.postPersonalDetailsOne(postParams, "Bearer " + accessToken);
        callableRes.enqueue(new Callback<RegisterEmploymentDetailsResponse>() {
            @Override
            public void onResponse(Call<RegisterEmploymentDetailsResponse> call, Response<RegisterEmploymentDetailsResponse> response) {
                if (response.code() == 200){
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

    public void postPersonalDetailsTwo(PersonalDetailsTwoPostModel postParams, String accessToken) {
        Call<RegisterEmploymentDetailsResponse> callableRes = AblApplication.apiInterface.postPersonalDetailsTwo(postParams, "Bearer " + accessToken);
        callableRes.enqueue(new Callback<RegisterEmploymentDetailsResponse>() {
            @Override
            public void onResponse(Call<RegisterEmploymentDetailsResponse> call, Response<RegisterEmploymentDetailsResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getMessage().getStatus().equals("200")) {
                        registerEmploymentDetailsResponseMutableLiveData.postValue(response.body());
                    } else {
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                } else {
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<RegisterEmploymentDetailsResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }


    public void postPersonalDetailsThree(PersonalDetailsThreePostModel postParams, String accessToken) {
        Call<RegisterEmploymentDetailsResponse> callableRes = AblApplication.apiInterface.postPersonalDetailsThree(postParams, "Bearer " + accessToken);
        callableRes.enqueue(new Callback<RegisterEmploymentDetailsResponse>() {
            @Override
            public void onResponse(Call<RegisterEmploymentDetailsResponse> call, Response<RegisterEmploymentDetailsResponse> response) {
                if (response.code() == 200){
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
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        userAddressMutableLiveData.postValue(response.body());
                    }else{
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<UserAddressResponseModel> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }


    public void saveKyc(SaveKycPostParams saveKycPostParams, String accessToken) {
        Call<SaveKycResponse> callableRes = AblApplication.apiInterface.saveMonthlySalary(saveKycPostParams, "Bearer " + accessToken);
        callableRes.enqueue(new Callback<SaveKycResponse>() {
            @Override
            public void onResponse(Call<SaveKycResponse> call, Response<SaveKycResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        saveKycResponseMutableLiveData.postValue(response.body());
                    }else{
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<SaveKycResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    MutableLiveData<LookUpCodeResponse> tinUnavailabilityReasonsSuccessResponse = new MutableLiveData<LookUpCodeResponse>();
    MutableLiveData<String> tinUnavailabilityReasonsError = new MutableLiveData<String>();

    public void getTinUnavailabilityReasons(LookUpCodePostParams tinUnavailabilityReasonsPostParams) {
        Call<LookUpCodeResponse> callabeRes = AblApplication.apiInterface.getLookUpResponse(tinUnavailabilityReasonsPostParams);
        callabeRes.enqueue(new Callback<LookUpCodeResponse>() {
            @Override
            public void onResponse(Call<LookUpCodeResponse> call, Response<LookUpCodeResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        tinUnavailabilityReasonsSuccessResponse.postValue(response.body());
                    }else{
                        tinUnavailabilityReasonsError.postValue(getErrorDetail(response));
                    }
                }else{
                    tinUnavailabilityReasonsError.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<LookUpCodeResponse> call, Throwable t) {
                tinUnavailabilityReasonsError.postValue(t.getMessage());
            }
        });
    }

    MutableLiveData<FreelancerTaxResponse> freelancerTaxSuccessResponse = new MutableLiveData<FreelancerTaxResponse>();
    MutableLiveData<String> freelancerTaxErrorResponse = new MutableLiveData<String>();

    public void submitFreelancerTaxDetails(FreelancerTaxPostParams freelancerTaxPostParams, String accessToken) {
        Call<FreelancerTaxResponse> callableRes = AblApplication.apiInterface.submitFreelancerTaxDetails(freelancerTaxPostParams,"Bearer "+accessToken);
        callableRes.enqueue(new Callback<FreelancerTaxResponse>() {
            @Override
            public void onResponse(Call<FreelancerTaxResponse> call, Response<FreelancerTaxResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        freelancerTaxSuccessResponse.postValue(response.body());
                    }else{
                        freelancerTaxErrorResponse.postValue(getErrorDetail(response));
                    }
                }else{
                    freelancerTaxErrorResponse.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<FreelancerTaxResponse> call, Throwable t) {
                freelancerTaxErrorResponse.postValue(t.getMessage());
            }
        });
    }

    MutableLiveData<FatcaDetailsResponse> fatcaDetailsSuccessResponse = new MutableLiveData<>();
    MutableLiveData<String> fatcaDetailsErrorResponse = new MutableLiveData<>();

    public void submitFatcaDetails(FatcaDetailsPostParams fatcaDetailsPostParams, String accessToken) {
        Call<FatcaDetailsResponse> callableRes = AblApplication.apiInterface.submitFatcaDetails(fatcaDetailsPostParams,"Bearer "+accessToken);
        callableRes.enqueue(new Callback<FatcaDetailsResponse>() {
            @Override
            public void onResponse(Call<FatcaDetailsResponse> call, Response<FatcaDetailsResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        fatcaDetailsSuccessResponse.postValue(response.body());
                    }else{
                        fatcaDetailsErrorResponse.postValue(getErrorDetail(response));
                    }
                }else{
                    fatcaDetailsErrorResponse.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<FatcaDetailsResponse> call, Throwable t) {
                fatcaDetailsErrorResponse.postValue(t.getMessage());
            }
        });
    }
}