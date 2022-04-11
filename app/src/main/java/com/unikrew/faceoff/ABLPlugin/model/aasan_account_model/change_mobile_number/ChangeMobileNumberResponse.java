package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.change_mobile_number;

import java.io.Serializable;

public class ChangeMobileNumberResponse implements Serializable {
    ChangeMobileNumberResponseData data;
    ChangeMobileNumberResponseMessage message;

    public ChangeMobileNumberResponseData getData() {
        return data;
    }

    public void setData(ChangeMobileNumberResponseData data) {
        this.data = data;
    }

    public ChangeMobileNumberResponseMessage getMessage() {
        return message;
    }

    public void setMessage(ChangeMobileNumberResponseMessage message) {
        this.message = message;
    }
}
