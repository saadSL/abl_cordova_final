package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode.BranchesModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode.GetBranchPostModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectBankingModeViewModel extends BaseViewModel {

    public MutableLiveData<BranchesModel> branchesLiveData = new MutableLiveData<>();

    public void getBranches(GetBranchPostModel getBranchPostModel) {

        Call<BranchesModel> callableRes = AblApplication.apiInterface.getBranches(getBranchPostModel);
        callableRes.enqueue(new Callback<BranchesModel>() {
            @Override
            public void onResponse(Call<BranchesModel> call, Response<BranchesModel> response) {
                if (response.code() == 200) {
                    branchesLiveData.postValue(response.body());
                } else if (response.code() == 403) {
                    errorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<BranchesModel> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });

    }

}
