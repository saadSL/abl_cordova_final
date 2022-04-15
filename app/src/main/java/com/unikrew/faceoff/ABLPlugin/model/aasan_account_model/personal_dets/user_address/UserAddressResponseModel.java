package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address;

import java.io.Serializable;
import java.util.List;

public class UserAddressResponseModel implements Serializable {
	private List<AddressesResponseDataItem> data;
	private AddressesResponseMessage message;

	public List<AddressesResponseDataItem> getData(){
		return data;
	}

	public AddressesResponseMessage getMessage(){
		return message;
	}
}