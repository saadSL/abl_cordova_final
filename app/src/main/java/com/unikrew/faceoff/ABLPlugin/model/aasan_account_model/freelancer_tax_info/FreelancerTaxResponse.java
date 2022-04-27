package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.freelancer_tax_info;

import java.io.Serializable;

public class FreelancerTaxResponse implements Serializable {
    private FreelancerTaxResponseData data;
    private FreelancerTaxResponseMessage message;

    public FreelancerTaxResponseData getData() {
        return data;
    }

    public void setData(FreelancerTaxResponseData data) {
        this.data = data;
    }

    public FreelancerTaxResponseMessage getMessage() {
        return message;
    }

    public void setMessage(FreelancerTaxResponseMessage message) {
        this.message = message;
    }
}
