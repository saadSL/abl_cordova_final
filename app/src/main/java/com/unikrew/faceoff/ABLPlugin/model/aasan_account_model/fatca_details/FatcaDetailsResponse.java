package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.fatca_details;

import java.io.Serializable;

public class FatcaDetailsResponse implements Serializable {
    public FatcaDetailsResponseData data;
    public FatcaDetailsResponseMessage message;

    public FatcaDetailsResponseData getData() {
        return data;
    }

    public void setData(FatcaDetailsResponseData data) {
        this.data = data;
    }

    public FatcaDetailsResponseMessage getMessage() {
        return message;
    }

    public void setMessage(FatcaDetailsResponseMessage message) {
        this.message = message;
    }
}
