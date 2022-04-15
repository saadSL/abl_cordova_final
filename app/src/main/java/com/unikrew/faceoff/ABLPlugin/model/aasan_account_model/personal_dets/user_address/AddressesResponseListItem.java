package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address;

import java.io.Serializable;

public class AddressesResponseListItem implements Serializable {
	private String customerAddress;
	private Object country;
	private String nearestLandMark;
	private String city;
	private Object addressTypeForeignInd;
	private Object postalCode;
	private Object mobileNo;
	private int countryId;
	private int rdaCustomerId;
	private Object countryCodeMobile;
	private Object phone;
	private int addressTypeId;
	private Object customerTown;
	private int rdaCustomerProfileAddrId;

	public String getCustomerAddress(){
		return customerAddress;
	}

	public Object getCountry(){
		return country;
	}

	public String getNearestLandMark(){
		return nearestLandMark;
	}

	public String getCity(){
		return city;
	}

	public Object getAddressTypeForeignInd(){
		return addressTypeForeignInd;
	}

	public Object getPostalCode(){
		return postalCode;
	}

	public Object getMobileNo(){
		return mobileNo;
	}

	public int getCountryId(){
		return countryId;
	}

	public int getRdaCustomerId(){
		return rdaCustomerId;
	}

	public Object getCountryCodeMobile(){
		return countryCodeMobile;
	}

	public Object getPhone(){
		return phone;
	}

	public int getAddressTypeId(){
		return addressTypeId;
	}

	public Object getCustomerTown(){
		return customerTown;
	}

	public int getRdaCustomerProfileAddrId(){
		return rdaCustomerProfileAddrId;
	}
}
