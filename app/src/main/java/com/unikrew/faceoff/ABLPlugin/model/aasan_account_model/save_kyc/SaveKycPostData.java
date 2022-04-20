package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc;

import java.io.Serializable;

public class SaveKycPostData implements Serializable {
    public int rdaCustomerAccInfoId;
    public int rdaCustomerProfileId;
    public Long averageMonthlySalary;

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
