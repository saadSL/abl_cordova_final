package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.mobile_network;

import java.io.Serializable;

public class MobileNetworkResponseMessage implements Serializable {
    public String status;
    public String description;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}