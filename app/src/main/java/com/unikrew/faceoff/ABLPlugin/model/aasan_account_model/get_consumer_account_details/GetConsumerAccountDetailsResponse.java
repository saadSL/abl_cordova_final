package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details;

import java.io.Serializable;

public class GetConsumerAccountDetailsResponse implements Serializable {
    private GetConsumerAccountDetailsResponseData data = new GetConsumerAccountDetailsResponseData();
    private GetConsumerAccountDetailsResponseMessage message;

    public GetConsumerAccountDetailsResponseData getData() {
        return data;
    }

    public void setData(GetConsumerAccountDetailsResponseData data) {
        this.data = data;
    }

    public GetConsumerAccountDetailsResponseMessage getMessage() {
        return message;
    }

    public void setMessage(GetConsumerAccountDetailsResponseMessage message) {
        this.message = message;
    }
}
