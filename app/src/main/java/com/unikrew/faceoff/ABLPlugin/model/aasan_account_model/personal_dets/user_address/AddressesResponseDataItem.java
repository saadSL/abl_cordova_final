package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address;

import java.io.Serializable;
import java.util.List;

public class AddressesResponseDataItem implements Serializable {
	private boolean isPrimary;
	private List<AddressesResponseListItem> addressesList;

	public boolean isIsPrimary(){
		return isPrimary;
	}

	public List<AddressesResponseListItem> getAddressesList(){
		return addressesList;
	}
}