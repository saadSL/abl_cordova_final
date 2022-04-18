package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.nature_of_account;

import java.io.Serializable;

public class SaveNatureOfAccountPostData implements Serializable {
    public int rdaCustomerAccInfoId;
    public int rdaCustomerId;
    public int customerTypeId;
    public int natureOfAccountId;
    public int noOfJointApplicatns;

    public int getRdaCustomerAccInfoId() {
        return rdaCustomerAccInfoId;
    }

    public void setRdaCustomerAccInfoId(int rdaCustomerAccInfoId) {
        this.rdaCustomerAccInfoId = rdaCustomerAccInfoId;
    }

    public int getRdaCustomerId() {
        return rdaCustomerId;
    }

    public void setRdaCustomerId(int rdaCustomerId) {
        this.rdaCustomerId = rdaCustomerId;
    }

    public int getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(int customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public int getNatureOfAccountId() {
        return natureOfAccountId;
    }

    public void setNatureOfAccountId(int natureOfAccountId) {
        this.natureOfAccountId = natureOfAccountId;
    }

    public int getNoOfJointApplicatns() {
        return noOfJointApplicatns;
    }

    public void setNoOfJointApplicatns(int noOfJointApplicatns) {
        this.noOfJointApplicatns = noOfJointApplicatns;
    }
}
