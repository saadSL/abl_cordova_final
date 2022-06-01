package com.unikrew.faceoff.ABLPlugin.model.common.register_consumer_basic_info;

import java.io.Serializable;

public class RegisterConsumerBasicInfoResponseMessage implements Serializable {
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
