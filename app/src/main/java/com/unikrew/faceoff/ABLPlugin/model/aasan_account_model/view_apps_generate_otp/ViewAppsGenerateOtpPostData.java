package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewAppsGenerateOtpPostData implements Serializable {
    public int customerTypeId;
    public String mobileNo;
    public String idNumber;
    public boolean generateOtp;
    public int portedMobileNetwork;
    public ArrayList<ViewAppsGenerateOtpPostAttachment> attachments;

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public int isPortedMobileNetwork() {
        return portedMobileNetwork;
    }

    public void setPortedMobileNetwork(int portedMobileNetwork) {
        this.portedMobileNetwork = portedMobileNetwork;
    }



    public int getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(int customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public boolean isGenerateOtp() {
        return generateOtp;
    }

    public void setGenerateOtp(boolean generateOtp) {
        this.generateOtp = generateOtp;
    }

    public ArrayList<ViewAppsGenerateOtpPostAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<ViewAppsGenerateOtpPostAttachment> attachments) {
        this.attachments = attachments;
    }
}
