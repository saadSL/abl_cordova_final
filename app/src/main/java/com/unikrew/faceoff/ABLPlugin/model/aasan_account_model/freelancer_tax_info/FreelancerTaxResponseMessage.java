package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.freelancer_tax_info;

import java.io.Serializable;

public class FreelancerTaxResponseMessage implements Serializable {
    private String status;
    private String description;

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
