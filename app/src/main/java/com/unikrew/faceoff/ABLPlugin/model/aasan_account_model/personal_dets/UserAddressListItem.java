package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets;

public class UserAddressListItem {
	private int rdaCustomerId;
	private String customerAddress;
	private String nearestLandMark;
	private String city;
	private int addressTypeId;
	private int countryId;

	public int getRdaCustomerId(){
		return rdaCustomerId;
	}

	public String getCustomerAddress(){
		return customerAddress;
	}

	public String getNearestLandMark(){
		return nearestLandMark;
	}

	public String getCity(){
		return city;
	}

	public int getAddressTypeId(){
		return addressTypeId;
	}

	public int getCountryId(){
		return countryId;
	}
}
