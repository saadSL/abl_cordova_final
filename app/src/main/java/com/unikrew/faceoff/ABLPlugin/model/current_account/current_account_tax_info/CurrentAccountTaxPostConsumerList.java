package com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info;

import java.io.Serializable;
import java.util.ArrayList;

public class CurrentAccountTaxPostConsumerList implements Serializable {
    public int rdaCustomerProfileId;
    public int rdaCustomerAccInfoId;
    public int customerTypeId;
    public String fullName;
    public String fatherHusbandName;
    public String motherMaidenName;
    public String cityOfBirth;
    public boolean isPrimary;
    public String emailAddress;
    public int taxResidentInd;
    public Integer occupationId;
    public Integer professionId;
    public String customerNtn;
    public Object rdaCustomerCountryId;
    public Object kinName;
    public Object kinCnic;
    public Object kinMobile;
    public int nationalityTypeId;
    public ArrayList<CurrentAccountTaxPostNationality> nationalities;
    private ArrayList<CurrentAccountTaxPostResidentCountries> residentCountries;

    public int getRdaCustomerProfileId() {
        return rdaCustomerProfileId;
    }

    public void setRdaCustomerProfileId(int rdaCustomerProfileId) {
        this.rdaCustomerProfileId = rdaCustomerProfileId;
    }

    public int getRdaCustomerAccInfoId() {
        return rdaCustomerAccInfoId;
    }

    public void setRdaCustomerAccInfoId(int rdaCustomerAccInfoId) {
        this.rdaCustomerAccInfoId = rdaCustomerAccInfoId;
    }

    public int getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(int customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFatherHusbandName() {
        return fatherHusbandName;
    }

    public void setFatherHusbandName(String fatherHusbandName) {
        this.fatherHusbandName = fatherHusbandName;
    }

    public String getMotherMaidenName() {
        return motherMaidenName;
    }

    public void setMotherMaidenName(String motherMaidenName) {
        this.motherMaidenName = motherMaidenName;
    }

    public String getCityOfBirth() {
        return cityOfBirth;
    }

    public void setCityOfBirth(String cityOfBirth) {
        this.cityOfBirth = cityOfBirth;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getTaxResidentInd() {
        return taxResidentInd;
    }

    public void setTaxResidentInd(int taxResidentInd) {
        this.taxResidentInd = taxResidentInd;
    }

    public Integer getOccupationId() {
        return occupationId;
    }

    public void setOccupationId(Integer occupationId) {
        this.occupationId = occupationId;
    }

    public Integer getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Integer professionId) {
        this.professionId = professionId;
    }

    public String getCustomerNtn() {
        return customerNtn;
    }

    public void setCustomerNtn(String customerNtn) {
        this.customerNtn = customerNtn;
    }

    public Object getRdaCustomerCountryId() {
        return rdaCustomerCountryId;
    }

    public void setRdaCustomerCountryId(Object rdaCustomerCountryId) {
        this.rdaCustomerCountryId = rdaCustomerCountryId;
    }

    public Object getKinName() {
        return kinName;
    }

    public void setKinName(Object kinName) {
        this.kinName = kinName;
    }

    public Object getKinCnic() {
        return kinCnic;
    }

    public void setKinCnic(Object kinCnic) {
        this.kinCnic = kinCnic;
    }

    public Object getKinMobile() {
        return kinMobile;
    }

    public void setKinMobile(Object kinMobile) {
        this.kinMobile = kinMobile;
    }

    public int getNationalityTypeId() {
        return nationalityTypeId;
    }

    public void setNationalityTypeId(int nationalityTypeId) {
        this.nationalityTypeId = nationalityTypeId;
    }

    public ArrayList<CurrentAccountTaxPostNationality> getNationalities() {
        return nationalities;
    }

    public void setNationalities(ArrayList<CurrentAccountTaxPostNationality> nationalities) {
        this.nationalities = nationalities;
    }

    public ArrayList<CurrentAccountTaxPostResidentCountries> getResidentCountries() {
        return residentCountries;
    }

    public void setResidentCountries(ArrayList<CurrentAccountTaxPostResidentCountries> residentCountries) {
        this.residentCountries = residentCountries;
    }
}
