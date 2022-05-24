package com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info;

import java.io.Serializable;

public class CurrentAccountTaxResponseNationality implements Serializable {
    public int rdaCustomerNationalityId;
    public int rdaCustomerId;
    public int nationalityId;
    public Object idNumber;

    public int getRdaCustomerNationalityId() {
        return rdaCustomerNationalityId;
    }

    public void setRdaCustomerNationalityId(int rdaCustomerNationalityId) {
        this.rdaCustomerNationalityId = rdaCustomerNationalityId;
    }

    public int getRdaCustomerId() {
        return rdaCustomerId;
    }

    public void setRdaCustomerId(int rdaCustomerId) {
        this.rdaCustomerId = rdaCustomerId;
    }

    public int getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(int nationalityId) {
        this.nationalityId = nationalityId;
    }

    public Object getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Object idNumber) {
        this.idNumber = idNumber;
    }
}
