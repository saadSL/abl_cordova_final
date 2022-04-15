package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostUserAddressDataItem implements Serializable {
	private boolean isPrimary;
	private List<PostUserAddressListItem> addressesList = new ArrayList<>();

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean primary) {
		isPrimary = primary;
	}

	public void setAddressesList(List<PostUserAddressListItem> addressesList) {
		this.addressesList = addressesList;
	}

	public boolean isIsPrimary(){
		return isPrimary;
	}

	public List<PostUserAddressListItem> getAddressesList(){
		return addressesList;
	}
}