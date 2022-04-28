package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.freelancer_tax_info;

import java.io.Serializable;
import java.util.ArrayList;

public class FreelancerTaxPostConsumerList implements Serializable {
    private int customerTypeId;
    private int occupationId;
    private int professionId;
    private int rdaCustomerAccInfoId;
    private int rdaCustomerProfileId;
    private String fatherHusbandName;
    private String fullName;
    private String motherMaidenName;
    private Boolean isPrimary;
    private ArrayList<FreelancerTaxPostResidentCountries> residentCountries;

    public int getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(int customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public int getOccupationId() {
        return occupationId;
    }

    public void setOccupationId(int occupationId) {
        this.occupationId = occupationId;
    }

    public int getProfessionId() {
        return professionId;
    }

    public void setProfessionId(int professionId) {
        this.professionId = professionId;
    }

    public int getRdaCustomerAccInfoId() {
        return rdaCustomerAccInfoId;
    }

    public void setRdaCustomerAccInfoId(int rdaCustomerAccInfoId) {
        this.rdaCustomerAccInfoId = rdaCustomerAccInfoId;
    }

    public int getRdaCustomerProfileId() {
        return rdaCustomerProfileId;
    }

    public void setRdaCustomerProfileId(int rdaCustomerProfileId) {
        this.rdaCustomerProfileId = rdaCustomerProfileId;
    }

    public String getFatherHusbandName() {
        return fatherHusbandName;
    }

    public void setFatherHusbandName(String fatherHusbandName) {
        this.fatherHusbandName = fatherHusbandName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMotherMaidenName() {
        return motherMaidenName;
    }

    public void setMotherMaidenName(String motherMaidenName) {
        this.motherMaidenName = motherMaidenName;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }

    public ArrayList<FreelancerTaxPostResidentCountries> getResidentCountries() {
        return residentCountries;
    }

    public void setResidentCountries(ArrayList<FreelancerTaxPostResidentCountries> residentCountries) {
        this.residentCountries = residentCountries;
    }
}
