package com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info;

import java.io.Serializable;

public class CurrentAccountTaxPostResidentCountries implements Serializable {
    private Object countryOfTaxResidenceId;
    private int rdaCustomerId;
    private Long taxIdentityNo;
    private int tinReasonId;
    private String explanation;

    public Object getCountryOfTaxResidenceId() {
        return countryOfTaxResidenceId;
    }

    public void setCountryOfTaxResidenceId(Object countryOfTaxResidenceId) {
        this.countryOfTaxResidenceId = countryOfTaxResidenceId;
    }

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

    public Long getTaxIdentityNo() {
        return taxIdentityNo;
    }

    public void setTaxIdentityNo(Long taxIdentityNo) {
        this.taxIdentityNo = taxIdentityNo;
    }

    public int getTinReasonId() {
        return tinReasonId;
    }

    public void setTinReasonId(int tinReasonId) {
        this.tinReasonId = tinReasonId;
    }
}
