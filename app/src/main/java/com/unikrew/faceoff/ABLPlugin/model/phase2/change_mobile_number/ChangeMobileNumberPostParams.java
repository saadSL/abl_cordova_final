package com.unikrew.faceoff.ABLPlugin.model.phase2.change_mobile_number;

import java.io.Serializable;

public class ChangeMobileNumberPostParams implements Serializable {
    public ChangeMobileNumberPostData data = new ChangeMobileNumberPostData();

    public ChangeMobileNumberPostData getData() {
        return data;
    }

    public void setData(ChangeMobileNumberPostData data) {
        this.data = data;
    }
}
