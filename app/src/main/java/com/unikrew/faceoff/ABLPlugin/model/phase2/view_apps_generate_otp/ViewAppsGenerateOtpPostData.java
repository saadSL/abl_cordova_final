package com.unikrew.faceoff.ABLPlugin.model.phase2.view_apps_generate_otp;

import java.io.Serializable;

public class ViewAppsGenerateOtpPostData implements Serializable {
    public int customerTypeId;
    public String idNumber;
    public String mobileNo;
    public String mobileNetwork;
    public Boolean isPortedMobileNetwork;

    public String getMobileNetwork() {
        return mobileNetwork;
    }

    public void setMobileNetwork(String mobileNetwork) {
        this.mobileNetwork = mobileNetwork;
    }

    public Boolean getPortedMobileNetwork() {
        return isPortedMobileNetwork;
    }

    public void setPortedMobileNetwork(Boolean portedMobileNetwork) {
        isPortedMobileNetwork = portedMobileNetwork;
    }

    public int getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(int customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
