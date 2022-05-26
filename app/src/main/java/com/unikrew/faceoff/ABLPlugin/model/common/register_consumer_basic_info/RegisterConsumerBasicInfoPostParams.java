package com.unikrew.faceoff.ABLPlugin.model.common.register_consumer_basic_info;

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
