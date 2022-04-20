package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details;

import java.io.Serializable;

public class GetConsumerAccountDetailsResponseAddress implements Serializable {
    public int rdaCustomerProfileAddrId;
    public int rdaCustomerId;
    public Object postalCode;
    public Object phone;
    public String nearestLandMark;
    public Object mobileNo;
    public String customerTown;
    public String customerAddress;
    public Object countryCodeMobile;
    public String city;
    public Integer countryId;
    public String country;
    public Object addressTypeForeignInd;
    public int addressTypeId;

    public int getRdaCustomerProfileAddrId() {
        return rdaCustomerProfileAddrId;
    }

    public void setRdaCustomerProfileAddrId(int rdaCustomerProfileAddrId) {
        this.rdaCustomerProfileAddrId = rdaCustomerProfileAddrId;
    }

    public int getRdaCustomerId() {
        return rdaCustomerId;
    }

    public void setRdaCustomerId(int rdaCustomerId) {
        this.rdaCustomerId = rdaCustomerId;
    }

    public Object getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Object postalCode) {
        this.postalCode = postalCode;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public String getNearestLandMark() {
        return nearestLandMark;
    }

    public void setNearestLandMark(String nearestLandMark) {
        this.nearestLandMark = nearestLandMark;
    }

    public Object getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Object mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCustomerTown() {
        return customerTown;
    }

    public void setCustomerTown(String customerTown) {
        this.customerTown = customerTown;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Object getCountryCodeMobile() {
        return countryCodeMobile;
    }

    public void setCountryCodeMobile(Object countryCodeMobile) {
        this.countryCodeMobile = countryCodeMobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Object getAddressTypeForeignInd() {
        return addressTypeForeignInd;
    }

    public void setAddressTypeForeignInd(Object addressTypeForeignInd) {
        this.addressTypeForeignInd = addressTypeForeignInd;
    }

    public int getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(int addressTypeId) {
        this.addressTypeId = addressTypeId;
    }
}
