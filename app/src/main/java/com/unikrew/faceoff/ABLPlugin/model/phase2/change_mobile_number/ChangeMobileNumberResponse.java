package com.unikrew.faceoff.ABLPlugin.model.phase2.change_mobile_number;

import java.io.Serializable;

public class ChangeMobileNumberResponse implements Serializable {
    public ChangeMobileNumberResponseData data;
    public ChangeMobileNumberResponseMessage message;

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
