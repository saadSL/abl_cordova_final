package com.unikrew.faceoff.ABLPlugin.ui.current_account.personal_details;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.tin_unavailability_reasons.TinUnavailabilityReasonsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.tin_unavailability_reasons.TinUnavailabilityReasonsResponse;
import com.unikrew.faceoff.ABLPlugin.model.current_account.countries.CountriesResponse;
import com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info.CurrentAccountTaxPostParams;
import com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info.CurrentAccountTaxResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalDetailsViewModel extends BaseViewModel {

    MutableLiveData<CountriesResponse> countriesResponse = new MutableLiveData<>();
    MutableLiveData<TinUnavailabilityReasonsResponse> tinUnavailabilityReasonsSuccessResponse = new MutableLiveData<>();
    MutableLiveData<CurrentAccountTaxResponse> currentAccountTaxSuccessResponse = new MutableLiveData<>();


    public void getCountries(){
        Call<CountriesResponse> callableRes = AblApplication.apiInterface.getCountries();
        callableRes.enqueue(new Callback<CountriesResponse>() {
            @Override
            public void onResponse(Call<CountriesResponse> call, Response<CountriesResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        countriesResponse.postValue(response.body());
                    }else{
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<CountriesResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void getTinUnavailabilityReasons(TinUnavailabilityReasonsPostParams tinUnavailabilityReasonsPostParams) {
        Call<TinUnavailabilityReasonsResponse> callableRes = AblApplication.apiInterface.getTinUnavailabilityReasons(tinUnavailabilityReasonsPostParams);
        callableRes.enqueue(new Callback<TinUnavailabilityReasonsResponse>() {
            @Override
            public void onResponse(Call<TinUnavailabilityReasonsResponse> call, Response<TinUnavailabilityReasonsResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        tinUnavailabilityReasonsSuccessResponse.postValue(response.body());
                    }else{
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<TinUnavailabilityReasonsResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void postPersonalDetails(CurrentAccountTaxPostParams postParams,String accessToken) {
        Call<CurrentAccountTaxResponse> callableRes = AblApplication.apiInterface.postCurrentAccountTaxPersonalDetails(postParams,"Bearer "+accessToken);
        callableRes.enqueue(new Callback<CurrentAccountTaxResponse>() {
            @Override
            public void onResponse(Call<CurrentAccountTaxResponse> call, Response<CurrentAccountTaxResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        currentAccountTaxSuccessResponse.postValue(response.body());
                    }else{
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<CurrentAccountTaxResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });

    }
}
