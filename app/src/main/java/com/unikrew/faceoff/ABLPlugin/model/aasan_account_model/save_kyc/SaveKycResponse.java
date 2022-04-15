package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc;

import java.io.Serializable;

public class SaveKycResponse implements Serializable {
    public SaveKycResponseData data;
    public SaveKycResponseMessage message;

    public SaveKycResponseData getData() {
        return data;
    }

    public void setData(SaveKycResponseData data) {
        this.data = data;
    }

    public SaveKycResponseMessage getMessage() {
        return message;
    }

    public void setMessage(SaveKycResponseMessage message) {
        this.message = message;
    }
}
