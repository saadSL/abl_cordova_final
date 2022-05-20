package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.additional_applicant;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.joint_account_model.relationship.RelationshipPostParams;
import com.unikrew.faceoff.ABLPlugin.model.joint_account_model.relationship.RelationshipResponse;
import com.unikrew.faceoff.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdditionalApplicantViewModel extends BaseViewModel {

    MutableLiveData<RelationshipResponse> relationshipSuccessResponse = new MutableLiveData<>();
    MutableLiveData<String> relationshipErrorResponse = new MutableLiveData<>();


    public void getRelationships(RelationshipPostParams relationshipPostParams) {
        Call<RelationshipResponse> callableRes = AblApplication.apiInterface.getRelationships(relationshipPostParams);
        callableRes.enqueue(new Callback<RelationshipResponse>() {
            @Override
            public void onResponse(Call<RelationshipResponse> call, Response<RelationshipResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        relationshipSuccessResponse.postValue(response.body());
                    }else{
                        relationshipErrorResponse.postValue(getErrorDetail(response));
                    }
                }else{
                    relationshipErrorResponse.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<RelationshipResponse> call, Throwable t) {
                relationshipErrorResponse.postValue(t.getMessage());
            }
        });
    }
}
