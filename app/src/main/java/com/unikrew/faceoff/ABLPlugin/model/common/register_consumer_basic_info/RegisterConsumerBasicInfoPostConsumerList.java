package com.unikrew.faceoff.ABLPlugin.model.common.register_consumer_basic_info;

import java.io.Serializable;
import java.util.ArrayList;

public class RegisterConsumerBasicInfoPostConsumerList implements Serializable {
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
    public String occupationId;
    public Object professionId;
    public Object customerNtn;
    public Object rdaCustomerCountryId;
    public String kinName;
    public String kinCnic;
    public String kinMobile;
    public int nationalityTypeId;
    public ArrayList<RegisterConsumerBasicInfoPostNationality> nationalities;
    public ArrayList<RegisterConsumerBasicInfoPostResidentCountries> residentCountries;

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

    public String getOccupationId() {
        return occupationId;
    }

    public void setOccupationId(String occupationId) {
        this.occupationId = occupationId;
    }

    public Object getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Object professionId) {
        this.professionId = professionId;
    }

    public Object getCustomerNtn() {
        return customerNtn;
    }

    public void setCustomerNtn(Object customerNtn) {
        this.customerNtn = customerNtn;
    }

    public Object getRdaCustomerCountryId() {
        return rdaCustomerCountryId;
    }

    public void setRdaCustomerCountryId(Object rdaCustomerCountryId) {
        this.rdaCustomerCountryId = rdaCustomerCountryId;
    }

    public String getKinName() {
        return kinName;
    }

    public void setKinName(String kinName) {
        this.kinName = kinName;
    }

    public String getKinCnic() {
        return kinCnic;
    }

    public void setKinCnic(String kinCnic) {
        this.kinCnic = kinCnic;
    }

    public String getKinMobile() {
        return kinMobile;
    }

    public void setKinMobile(String kinMobile) {
        this.kinMobile = kinMobile;
    }

    public int getNationalityTypeId() {
        return nationalityTypeId;
    }

    public void setNationalityTypeId(int nationalityTypeId) {
        this.nationalityTypeId = nationalityTypeId;
    }

    public ArrayList<RegisterConsumerBasicInfoPostNationality> getNationalities() {
        return nationalities;
    }

    public void setNationalities(ArrayList<RegisterConsumerBasicInfoPostNationality> nationalities) {
        this.nationalities = nationalities;
    }

    public ArrayList<RegisterConsumerBasicInfoPostResidentCountries> getResidentCountries() {
        return residentCountries;
    }

    public void setResidentCountries(ArrayList<RegisterConsumerBasicInfoPostResidentCountries> residentCountries) {
        this.residentCountries = residentCountries;
    }
}
