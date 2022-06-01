package com.unikrew.faceoff.ABLPlugin.model.current_account.kin_details;

import java.io.Serializable;

public class RegisterConsumerBasicInfoPostParams implements Serializable {
    public RegisterConsumerBasicInfoPostData data = new RegisterConsumerBasicInfoPostData();

    public RegisterConsumerBasicInfoPostData getData() {
        return data;
    }

    public void setData(RegisterConsumerBasicInfoPostData data) {
        this.data = data;
    }
}
