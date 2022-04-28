package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.freelancer_tax_info;

import java.io.Serializable;

public class FreelancerTaxPostParams implements Serializable {
    private FreelancerTaxPostData data = new FreelancerTaxPostData();

    public FreelancerTaxPostData getData() {
        return data;
    }

    public void setData(FreelancerTaxPostData data) {
        this.data = data;
    }
}
