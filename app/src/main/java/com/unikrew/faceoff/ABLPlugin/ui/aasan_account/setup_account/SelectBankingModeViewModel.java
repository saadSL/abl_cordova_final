package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode.BranchesModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode.GetBranchPostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.mobile_network.MobileNetworkPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.mobile_network.MobileNetworkResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectBankingModeViewModel extends BaseViewModel {

    public MutableLiveData<BranchesModel> branchesLiveData = new MutableLiveData<>();
    public MutableLiveData<MobileNetworkResponse> purposeOfAccountLiveData = new MutableLiveData<>();

    public void getBranches(GetBranchPostModel getBranchPostModel) {

        Call<BranchesModel> callableRes = AblApplication.apiInterface.getBranches(getBranchPostModel);
        callableRes.enqueue(new Callback<BranchesModel>() {
            @Override
            public void onResponse(Call<BranchesModel> call, Response<BranchesModel> response) {
                if (response.code() == 200) {
                    branchesLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<BranchesModel> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });

    }

    public void getPurposeOfAccount(MobileNetworkPostParams mobileNetworkPostParams) {

        Call<MobileNetworkResponse> callableRes = AblApplication.apiInterface.getPurposeOfAccount(mobileNetworkPostParams);
        callableRes.enqueue(new Callback<MobileNetworkResponse>() {
            @Override
            public void onResponse(Call<MobileNetworkResponse> call, Response<MobileNetworkResponse> response) {
                if (response.code() == 200) {
                    purposeOfAccountLiveData.postValue(response.body());
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

}
