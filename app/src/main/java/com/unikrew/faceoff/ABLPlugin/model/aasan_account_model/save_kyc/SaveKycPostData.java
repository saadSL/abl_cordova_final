package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc;

import java.io.Serializable;

public class SaveKycPostData implements Serializable {
    public int rdaCustomerAccInfoId;
    public int rdaCustomerProfileId;
    public int averageMonthlySalary;

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

    public int getAverageMonthlySalary() {
        return averageMonthlySalary;
    }

    public void setAverageMonthlySalary(int averageMonthlySalary) {
        this.averageMonthlySalary = averageMonthlySalary;
    }
}
