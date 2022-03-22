package com.unikrew.faceoff.ABLPlugin.model;

import java.io.Serializable;

public class VerifyOtpBioMetricVerificationPostData implements Serializable {
    private String otp;
    private String rdaCustomerProfileId;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getRdaCustomerProfileId() {
        return rdaCustomerProfileId;
    }

    public void setRdaCustomerProfileId(String rdaCustomerProfileId) {
        this.rdaCustomerProfileId = rdaCustomerProfileId;
    }

    @Override
    public String toString(){
        return
                "VerifyOtpBioMetricVerificationPostData{" +
                        "otp = '" + otp + '\'' +
                        ",rdaCustomerProfileId = '" + rdaCustomerProfileId + '\'' +
                        "}";
    }
}
