package com.unikrew.faceoff.ABLPlugin.model.phase2.change_mobile_number;

import java.io.Serializable;

public class ChangeMobileNumberPostData implements Serializable {
    public String mobileNo;
    public int rdaCustomerProfileId;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getRdaCustomerProfileId() {
        return rdaCustomerProfileId;
    }

    public void setRdaCustomerProfileId(int rdaCustomerProfileId) {
        this.rdaCustomerProfileId = rdaCustomerProfileId;
    }
}
