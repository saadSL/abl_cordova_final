package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.fatca_details;

import java.io.Serializable;

public class FatcaReqPostDtoList implements Serializable {
    private int attorneyInd;
    private int birthUSInd;
    private int careAddrInd;
    private int greenCardHolderInd;
    private int rdaCustomerId;
    private int usCitizenInd;
    private int usMailAddrInd;
    private int usTaxResidentInd;


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
}
