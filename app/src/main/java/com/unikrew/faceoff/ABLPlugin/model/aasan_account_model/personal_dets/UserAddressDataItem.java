package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets;

import java.util.List;

public class UserAddressDataItem {
	private boolean isPrimary;
	private List<UserAddressListItem> addressesList;

	public boolean isIsPrimary(){
		return isPrimary;
	}

	public List<UserAddressListItem> getAddressesList(){
		return addressesList;
	}
}