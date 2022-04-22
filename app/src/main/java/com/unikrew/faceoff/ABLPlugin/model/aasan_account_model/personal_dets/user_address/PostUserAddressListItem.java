package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address;

import java.io.Serializable;

public class PostUserAddressListItem implements Serializable {
	private int rdaCustomerId;
	private String customerAddress;
	private String nearestLandMark;

	public String getCustomerTown() {
		return customerTown;
	}

	public void setCustomerTown(String customerTown) {
		this.customerTown = customerTown;
	}

	private String city;
	private String customerTown;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	private String country;
	private int addressTypeId;
	private int countryId;

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

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
}
