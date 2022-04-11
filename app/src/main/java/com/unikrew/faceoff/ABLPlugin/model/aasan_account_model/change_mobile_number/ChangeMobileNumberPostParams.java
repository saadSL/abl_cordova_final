package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.change_mobile_number;

import java.io.Serializable;

public class ChangeMobileNumberPostParams implements Serializable {
    private ChangeMobileNumberPostData data = new ChangeMobileNumberPostData();

    public ChangeMobileNumberPostData getData() {
        return data;
    }

    public void setData(ChangeMobileNumberPostData data) {
        this.data = data;
    }
}
