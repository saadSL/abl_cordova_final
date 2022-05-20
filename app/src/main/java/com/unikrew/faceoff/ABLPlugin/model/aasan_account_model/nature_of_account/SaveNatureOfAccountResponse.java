package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.nature_of_account;

import java.io.Serializable;

public class SaveNatureOfAccountResponse implements Serializable {
    private SaveNatureOfAccountResponseData data;
    private SaveNatureOfAccountResponseMessage message;

    public SaveNatureOfAccountResponseData getData() {
        return data;
    }

    public void setData(SaveNatureOfAccountResponseData data) {
        this.data = data;
    }

    public SaveNatureOfAccountResponseMessage getMessage() {
        return message;
    }

    public void setMessage(SaveNatureOfAccountResponseMessage message) {
        this.message = message;
    }
}
