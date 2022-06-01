package com.unikrew.faceoff.ABLPlugin.model.current_account.kin_details;

import java.io.Serializable;

public class RegisterConsumerBasicInfoResponseResidentCountries implements Serializable {
    public int rdaCustomerCountryId;
    public int rdaCustomerId;
    public Object taxIdentityNo;
    public int tinReasonId;
    public int countryOfTaxResidenceId;
    public Object explanation;

    public int getRdaCustomerCountryId() {
        return rdaCustomerCountryId;
    }

    public void setRdaCustomerCountryId(int rdaCustomerCountryId) {
        this.rdaCustomerCountryId = rdaCustomerCountryId;
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

    public int getTinReasonId() {
        return tinReasonId;
    }

    public void setTinReasonId(int tinReasonId) {
        this.tinReasonId = tinReasonId;
    }

    public int getCountryOfTaxResidenceId() {
        return countryOfTaxResidenceId;
    }

    public void setCountryOfTaxResidenceId(int countryOfTaxResidenceId) {
        this.countryOfTaxResidenceId = countryOfTaxResidenceId;
    }

    public Object getExplanation() {
        return explanation;
    }

    public void setExplanation(Object explanation) {
        this.explanation = explanation;
    }
}
