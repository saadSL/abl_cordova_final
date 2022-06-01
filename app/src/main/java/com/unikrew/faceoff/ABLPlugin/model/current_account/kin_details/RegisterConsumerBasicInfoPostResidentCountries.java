package com.unikrew.faceoff.ABLPlugin.model.common.register_consumer_basic_info;

import java.io.Serializable;

public class RegisterConsumerBasicInfoPostResidentCountries implements Serializable {
    public String countryOfTaxResidenceId;
    public int rdaCustomerId;
    public Object taxIdentityNo;
    public String tinReasonId;
    public Object explanation;

    public String getCountryOfTaxResidenceId() {
        return countryOfTaxResidenceId;
    }

    public void setCountryOfTaxResidenceId(String countryOfTaxResidenceId) {
        this.countryOfTaxResidenceId = countryOfTaxResidenceId;
    }

    public int getRdaCustomerId() {
        return rdaCustomerId;
    }

    public void setRdaCustomerId(int rdaCustomerId) {
        this.rdaCustomerId = rdaCustomerId;
    }

    public Object getTaxIdentityNo() {
        return taxIdentityNo;
    }

    public void setTaxIdentityNo(Object taxIdentityNo) {
        this.taxIdentityNo = taxIdentityNo;
    }

    public String getTinReasonId() {
        return tinReasonId;
    }

    public void setTinReasonId(String tinReasonId) {
        this.tinReasonId = tinReasonId;
    }

    public Object getExplanation() {
        return explanation;
    }

    public void setExplanation(Object explanation) {
        this.explanation = explanation;
    }
}
