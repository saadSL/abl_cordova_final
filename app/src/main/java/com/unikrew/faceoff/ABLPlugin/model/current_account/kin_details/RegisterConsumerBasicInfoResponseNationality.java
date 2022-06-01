package com.unikrew.faceoff.ABLPlugin.model.current_account.kin_details;

import java.io.Serializable;

public class RegisterConsumerBasicInfoResponseNationality implements Serializable {
    public int rdaCustomerNationalityId;
    public int rdaCustomerId;
    public int nationalityId;
    public String idNumber;

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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
