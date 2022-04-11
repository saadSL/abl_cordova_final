package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details;

import java.io.Serializable;

public class GetConsumerAccountDetailsPostParams implements Serializable {
    public GetConsumerAccountDetailsPostData data = new GetConsumerAccountDetailsPostData();

    public GetConsumerAccountDetailsPostData getData() {
        return data;
    }

    public void setData(GetConsumerAccountDetailsPostData data) {
        this.data = data;
    }
}
