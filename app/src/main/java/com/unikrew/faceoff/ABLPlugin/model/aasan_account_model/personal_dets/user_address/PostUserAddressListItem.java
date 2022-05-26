package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address;

import java.io.Serializable;

public class PostUserAddressListItem implements Serializable {
	private int rdaCustomerId;
	private String customerAddress;
	private String nearestLandMark;
	private String city;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	private String phone;
	private String customerTown;
	private String country;
	private int addressTypeId;
	private Integer countryId = null;

	public Integer getRdaCustomerProfileAddrId() {
		return rdaCustomerProfileAddrId;
	}

	public void setRdaCustomerProfileAddrId(Integer rdaCustomerProfileAddrId) {
		this.rdaCustomerProfileAddrId = rdaCustomerProfileAddrId;
	}

	private Integer rdaCustomerProfileAddrId = null;

	public String getCustomerTown() {
		return customerTown;
	}

	public void setCustomerTown(String customerTown) {
		this.customerTown = customerTown;
	}



	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}



	public int getRdaCustomerId() {
		return rdaCustomerId;
	}

	public void setRdaCustomerId(int rdaCustomerId) {
		this.rdaCustomerId = rdaCustomerId;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getNearestLandMark() {
		return nearestLandMark;
	}

	public void setNearestLandMark(String nearestLandMark) {
		this.nearestLandMark = nearestLandMark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getAddressTypeId() {
		return addressTypeId;
	}

	public void setAddressTypeId(int addressTypeId) {
		this.addressTypeId = addressTypeId;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	@Override
	public String toString() {
		return "PostUserAddressListItem{" +
				"rdaCustomerId=" + rdaCustomerId +
				", customerAddress='" + customerAddress + '\'' +
				", nearestLandMark='" + nearestLandMark + '\'' +
				", city='" + city + '\'' +
				", customerTown='" + customerTown + '\'' +
				", country='" + country + '\'' +
				", addressTypeId=" + addressTypeId +
				", countryId=" + countryId +
				'}';
	}
}
