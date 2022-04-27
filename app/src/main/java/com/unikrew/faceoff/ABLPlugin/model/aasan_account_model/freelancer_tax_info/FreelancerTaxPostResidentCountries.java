package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.freelancer_tax_info;

import java.io.Serializable;

public class FreelancerTaxPostResidentCountries implements Serializable {
    private String explanation;
    private int rdaCustomerId;
    private int taxIdentityNo;
    private int tinReasonId;
    private int taxResidentInd;

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getRdaCustomerId() {
        return rdaCustomerId;
    }

    public void setRdaCustomerId(int rdaCustomerId) {
        this.rdaCustomerId = rdaCustomerId;
    }

    public int getTaxIdentityNo() {
        return taxIdentityNo;
    }

    public void setTaxIdentityNo(int taxIdentityNo) {
        this.taxIdentityNo = taxIdentityNo;
    }

    public int getTinReasonId() {
        return tinReasonId;
    }

    public void setTinReasonId(int tinReasonId) {
        this.tinReasonId = tinReasonId;
    }

    public int getTaxResidentInd() {
        return taxResidentInd;
    }

    public void setTaxResidentInd(int taxResidentInd) {
        this.taxResidentInd = taxResidentInd;
    }
}
