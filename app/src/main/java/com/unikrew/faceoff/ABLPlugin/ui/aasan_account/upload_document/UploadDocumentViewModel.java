package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.upload_document;

import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.AblApplication;
import com.unikrew.faceoff.ABLPlugin.base.BaseViewModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.nature_of_account.SaveNatureOfAccountPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.nature_of_account.SaveNatureOfAccountResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_attachment.SaveAttachmentPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_attachment.SaveAttachmentResponse;
import com.unikrew.faceoff.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadDocumentViewModel extends BaseViewModel {

    MutableLiveData<SaveAttachmentResponse> saveAttachmentResponseMutableLiveData = new MutableLiveData<SaveAttachmentResponse>();
    MutableLiveData<String> saveAttachmentErrorLiveData = new MutableLiveData<String>();

    MutableLiveData<SaveNatureOfAccountResponse> saveNatureOfAccountResponseMutableLiveData = new MutableLiveData<SaveNatureOfAccountResponse>();
    MutableLiveData<String> saveNatureOfAccountErrorLiveData = new MutableLiveData<String>();

    public void saveAttachment(SaveAttachmentPostParams attachmentPostParams, String accessToken) {
        Call<SaveAttachmentResponse> callableRes = AblApplication.apiInterface.saveAttachment(attachmentPostParams,"Bearer "+accessToken);
        callableRes.enqueue(new Callback<SaveAttachmentResponse>() {
            @Override
            public void onResponse(Call<SaveAttachmentResponse> call, Response<SaveAttachmentResponse> response) {
                if (response.code() == 200){
                    saveAttachmentResponseMutableLiveData.postValue(response.body());
                }else{
                    saveAttachmentErrorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<SaveAttachmentResponse> call, Throwable t) {
                saveAttachmentErrorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void saveNatureOfAccount(SaveNatureOfAccountPostParams natureOfAccountPostParams, String accessToken) {
        Call<SaveNatureOfAccountResponse> callableRes = AblApplication.apiInterface.saveNatureOfAccount(natureOfAccountPostParams,"Bearer "+accessToken);
        callableRes.enqueue(new Callback<SaveNatureOfAccountResponse>() {
            @Override
            public void onResponse(Call<SaveNatureOfAccountResponse> call, Response<SaveNatureOfAccountResponse> response) {
                if (response.code() == 200){
                    if (response.body().getMessage().getStatus().equals("200")){
                        saveNatureOfAccountResponseMutableLiveData.postValue(response.body());
                    }else{
                        saveNatureOfAccountErrorLiveData.postValue(getErrorDetail(response));
                    }
                }else{
                    saveNatureOfAccountErrorLiveData.postValue(getErrorDetail(response));
                }
            }

            @Override
            public void onFailure(Call<SaveNatureOfAccountResponse> call, Throwable t) {
                saveNatureOfAccountErrorLiveData.postValue(t.getMessage());
            }
        });
    }
}
