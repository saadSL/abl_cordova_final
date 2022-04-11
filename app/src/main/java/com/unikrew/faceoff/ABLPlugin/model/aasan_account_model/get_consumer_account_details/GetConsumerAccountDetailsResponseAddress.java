package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details;

import java.io.Serializable;

public class GetConsumerAccountDetailsResponseAddress implements Serializable {
    public int rdaCustomerProfileAddrId;
    public int rdaCustomerId;
    public Object postalCode;
    public Object phone;
    public Object nearestLandMark;
    public Object mobileNo;
    public Object customerTown;
    public Object customerAddress;
    public Object countryCodeMobile;
    public Object city;
    public Object countryId;
    public Object country;
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

    public Object getNearestLandMark() {
        return nearestLandMark;
    }

    public void setNearestLandMark(Object nearestLandMark) {
        this.nearestLandMark = nearestLandMark;
    }

    public Object getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Object mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Object getCustomerTown() {
        return customerTown;
    }

    public void setCustomerTown(Object customerTown) {
        this.customerTown = customerTown;
    }

    public Object getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(Object customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Object getCountryCodeMobile() {
        return countryCodeMobile;
    }

    public void setCountryCodeMobile(Object countryCodeMobile) {
        this.countryCodeMobile = countryCodeMobile;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getCountryId() {
        return countryId;
    }

    public void setCountryId(Object countryId) {
        this.countryId = countryId;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
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
