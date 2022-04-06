package com.unikrew.faceoff.ABLPlugin.model.phase2.get_consumer_account_details;

import java.io.Serializable;

public class GetConsumerAccountDetailsPostParams implements Serializable {
    public GetConsumerAccountDetailsPostData data;

    public GetConsumerAccountDetailsPostData getData() {
        return data;
    }

    public void setData(GetConsumerAccountDetailsPostData data) {
        this.data = data;
    }
}
