package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.delete_drafted_application;

import java.io.Serializable;

public class DeleteDraftedApplicationPostData implements Serializable {
    public int customerProfileId;
    public int customerAccountInfoId;

    public int getCustomerProfileId() {
        return customerProfileId;
    }

    public void setCustomerProfileId(int customerProfileId) {
        this.customerProfileId = customerProfileId;
    }

    public int getCustomerAccountInfoId() {
        return customerAccountInfoId;
    }

    public void setCustomerAccountInfoId(int customerAccountInfoId) {
        this.customerAccountInfoId = customerAccountInfoId;
    }
}
