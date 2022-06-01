package com.unikrew.faceoff.ABLPlugin.model.common.register_consumer_basic_info;

import java.io.Serializable;

public class RegisterConsumerBasicInfoPostNationality implements Serializable {
    public int rdaCustomerId;
    public String nationalityId;
    public String idNumber;

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
