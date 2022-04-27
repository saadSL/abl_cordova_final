package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.fatca_details;

import java.io.Serializable;

public class FatcaResponseDtoList implements Serializable {
    public int rdaCustomerFatcaInfoId;
    public int attorneyInd;
    public int birthUSInd;
    public int careAddrInd;
    public int greenCardHolderInd;
    public int rdaCustomerId;
    public Object standingInsInd;
    public int usCitizenInd;
    public int usMailAddrInd;
    public int usTaxResidentInd;
    public Object provideCertificateLaterInd;

    public int getRdaCustomerFatcaInfoId() {
        return rdaCustomerFatcaInfoId;
    }

    public void setRdaCustomerFatcaInfoId(int rdaCustomerFatcaInfoId) {
        this.rdaCustomerFatcaInfoId = rdaCustomerFatcaInfoId;
    }

    public int getAttorneyInd() {
        return attorneyInd;
    }

    public void setAttorneyInd(int attorneyInd) {
        this.attorneyInd = attorneyInd;
    }

    public int getBirthUSInd() {
        return birthUSInd;
    }

    public void setBirthUSInd(int birthUSInd) {
        this.birthUSInd = birthUSInd;
    }

    public int getCareAddrInd() {
        return careAddrInd;
    }

    public void setCareAddrInd(int careAddrInd) {
        this.careAddrInd = careAddrInd;
    }

    public int getGreenCardHolderInd() {
        return greenCardHolderInd;
    }

    public void setGreenCardHolderInd(int greenCardHolderInd) {
        this.greenCardHolderInd = greenCardHolderInd;
    }

    public int getRdaCustomerId() {
        return rdaCustomerId;
    }

    public void setRdaCustomerId(int rdaCustomerId) {
        this.rdaCustomerId = rdaCustomerId;
    }

    public Object getStandingInsInd() {
        return standingInsInd;
    }

    public void setStandingInsInd(Object standingInsInd) {
        this.standingInsInd = standingInsInd;
    }

    public int getUsCitizenInd() {
        return usCitizenInd;
    }

    public void setUsCitizenInd(int usCitizenInd) {
        this.usCitizenInd = usCitizenInd;
    }

    public int getUsMailAddrInd() {
        return usMailAddrInd;
    }

    public void setUsMailAddrInd(int usMailAddrInd) {
        this.usMailAddrInd = usMailAddrInd;
    }

    public int getUsTaxResidentInd() {
        return usTaxResidentInd;
    }

    public void setUsTaxResidentInd(int usTaxResidentInd) {
        this.usTaxResidentInd = usTaxResidentInd;
    }

    public Object getProvideCertificateLaterInd() {
        return provideCertificateLaterInd;
    }

    public void setProvideCertificateLaterInd(Object provideCertificateLaterInd) {
        this.provideCertificateLaterInd = provideCertificateLaterInd;
    }
}
