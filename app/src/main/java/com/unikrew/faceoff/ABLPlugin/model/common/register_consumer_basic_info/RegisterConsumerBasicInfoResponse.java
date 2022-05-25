package com.unikrew.faceoff.ABLPlugin.model.common.register_consumer_basic_info;

import java.io.Serializable;

public class RegisterConsumerBasicInfoResponse implements Serializable {
    public RegisterConsumerBasicInfoResponseData data;
    public RegisterConsumerBasicInfoResponseMessage message;


    public RegisterConsumerBasicInfoResponseData getData() {
        return data;
    }

    public void setData(RegisterConsumerBasicInfoResponseData data) {
        this.data = data;
    }

    public RegisterConsumerBasicInfoResponseMessage getMessage() {
        return message;
    }

    public void setMessage(RegisterConsumerBasicInfoResponseMessage message) {
        this.message = message;
    }
}
