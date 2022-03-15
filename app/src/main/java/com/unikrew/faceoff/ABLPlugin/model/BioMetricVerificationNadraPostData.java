package com.unikrew.faceoff.ABLPlugin.model;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BioMetricVerificationNadraPostData implements Serializable {
    private String rdaCustomerProfileId = "";
    private String rdaCustomerAccountInfoId = "";
    private String cnic = "";

    public List<JSONObject> getFingerprints() {
        return fingerprints;
    }

    public void setFingerprints(List<JSONObject> fingerprints) {
        this.fingerprints = fingerprints;
    }

    /* saad's work for finger prints on line 11 and line 81 and line 18 - 24*/
    private List<JSONObject> fingerprints = new ArrayList<JSONObject>();
    private String templateType = "";
    private String contactNumber = "";
    private String areaName = "";
    private String accountType = "";


    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getRdaCustomerProfileId() {
        return rdaCustomerProfileId;
    }

    public void setRdaCustomerProfileId(String rdaCustomerProfileId) {
        this.rdaCustomerProfileId = rdaCustomerProfileId;
    }

    public String getRdaCustomerAccountInfoId() {
        return rdaCustomerAccountInfoId;
    }

    public void setRdaCustomerAccountInfoId(String rdaCustomerAccountInfoId) {
        this.rdaCustomerAccountInfoId = rdaCustomerAccountInfoId;
    }

    @Override
    public String toString() {
        return
                "BioMetricVerificationNadraPostData{" +
                        "rdaCustomerProfileId = '" + rdaCustomerProfileId + '\'' +
                        ",rdaCustomerAccountInfoId = '" + rdaCustomerAccountInfoId + '\'' +
                        ",cnic = '" + cnic + '\'' +
                        ",fingerprints = '" + fingerprints + '\'' +
                        ",templateType = '" + templateType + '\'' +
                        ",contactNumber = '" + contactNumber + '\'' +
                        ",areaName = '" + areaName + '\'' +
                        ",accountType = '" + accountType + '\'' +
                        "}";
    }
}