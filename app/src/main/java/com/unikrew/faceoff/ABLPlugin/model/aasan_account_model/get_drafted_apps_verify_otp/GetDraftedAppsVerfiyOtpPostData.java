package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp;

import java.io.Serializable;

public class GetDraftedAppsVerfiyOtpPostData implements Serializable {
    private int customerTypeId;
    private String idNumber;
    private String mobileNo;
    private String otp;


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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "GetDraftedAppsVerfiyOtpPostData{" +
                "customerTypeId=" + customerTypeId +
                ", idNumber='" + idNumber + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", otp='" + otp + '\'' +
                '}';
    }
}
