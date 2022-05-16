package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc;

import java.io.Serializable;

public class SaveKycPostData implements Serializable {
    private int rdaCustomerAccInfoId;
    private int rdaCustomerProfileId;
    private Long averageMonthlySalary = null;
    private Integer relationCode1 = null;

    public Integer getRelationCode1() {
        return relationCode1;
    }

    public void setRelationCode1(Integer relationCode1) {
        this.relationCode1 = relationCode1;
    }

    public int getRdaCustomerAccInfoId() {
        return rdaCustomerAccInfoId;
    }

    public void setRdaCustomerAccInfoId(int rdaCustomerAccInfoId) {
        this.rdaCustomerAccInfoId = rdaCustomerAccInfoId;
    }

    public int getRdaCustomerProfileId() {
        return rdaCustomerProfileId;
    }

    public void setRdaCustomerProfileId(int rdaCustomerProfileId) {
        this.rdaCustomerProfileId = rdaCustomerProfileId;
    }

    public Long getAverageMonthlySalary() {
        return averageMonthlySalary;
    }

    public void setAverageMonthlySalary(Long averageMonthlySalary) {
        this.averageMonthlySalary = averageMonthlySalary;
    }
}
