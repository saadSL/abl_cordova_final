package com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info;

import java.io.Serializable;

public class CurrentAccountTaxPostNationality implements Serializable {
    private int rdaCustomerId;
    private String nationalityId;
    private String idNumber;

    public int getRdaCustomerId() {
        return rdaCustomerId;
    }

    public void setRdaCustomerId(int rdaCustomerId) {
        this.rdaCustomerId = rdaCustomerId;
    }

    public String getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(String nationalityId) {
        this.nationalityId = nationalityId;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
