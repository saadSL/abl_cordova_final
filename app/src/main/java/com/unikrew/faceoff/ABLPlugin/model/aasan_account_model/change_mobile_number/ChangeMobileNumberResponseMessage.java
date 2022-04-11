package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.change_mobile_number;

import java.io.Serializable;

public class ChangeMobileNumberResponseMessage implements Serializable {
    public String status;
    public String description;
    public String errorDetail;


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

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
