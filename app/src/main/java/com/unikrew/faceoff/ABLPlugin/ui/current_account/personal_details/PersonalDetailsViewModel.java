package com.unikrew.faceoff.ABLPlugin.ui.current_account.personal_details;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.tin_unavailability_reasons.TinUnavailabilityReasonsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.tin_unavailability_reasons.TinUnavailabilityReasonsResponse;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodePostParams;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponse;
import com.unikrew.faceoff.ABLPlugin.model.current_account.countries.CountriesResponse;
import com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info.CurrentAccountTaxPostParams;
import com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info.CurrentAccountTaxResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalDetailsViewModel extends BaseViewModel {

    public MutableLiveData<CountriesResponse> countriesResponse = new MutableLiveData<>();
    public MutableLiveData<LookUpCodeResponse> tinUnavailabilityReasonsSuccessResponse = new MutableLiveData<>();
    public MutableLiveData<CurrentAccountTaxResponse> currentAccountTaxSuccessResponse = new MutableLiveData<>();


    public void getCountries() {
        Call<CountriesResponse> callableRes = AblApplication.apiInterface.getCountries();
        callableRes.enqueue(new Callback<CountriesResponse>() {
            @Override
            public void onResponse(Call<CountriesResponse> call, Response<CountriesResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getMessage().getStatus().equals("200")) {
                        countriesResponse.postValue(response.body());
                    } else {
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                } else {
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<CountriesResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void getTinUnavailabilityReasons(LookUpCodePostParams tinUnavailabilityReasonsPostParams) {
        Call<LookUpCodeResponse> callableRes = AblApplication.apiInterface.getLookUpResponse(tinUnavailabilityReasonsPostParams);
        callableRes.enqueue(new Callback<LookUpCodeResponse>() {
            @Override
            public void onResponse(Call<LookUpCodeResponse> call, Response<LookUpCodeResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getMessage().getStatus().equals("200")) {
                        tinUnavailabilityReasonsSuccessResponse.postValue(response.body());
                    } else {
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                } else {
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<LookUpCodeResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void postPersonalDetails(CurrentAccountTaxPostParams postParams, String accessToken) {
        Call<CurrentAccountTaxResponse> callableRes = AblApplication.apiInterface.postCurrentAccountTaxPersonalDetails(postParams, "Bearer " + accessToken);
        callableRes.enqueue(new Callback<CurrentAccountTaxResponse>() {
            @Override
            public void onResponse(Call<CurrentAccountTaxResponse> call, Response<CurrentAccountTaxResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getMessage().getStatus().equals("200")) {
                        currentAccountTaxSuccessResponse.postValue(response.body());
                    } else {
                        errorLiveData.postValue(getErrorDetail(response));
                    }
                } else {
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
