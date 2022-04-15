package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address;

import java.io.Serializable;

public class AddressesResponseMessage implements Serializable {
	private String description;
	private String status;

	public String getDescription(){
		return description;
	}

	public String getStatus(){
		return status;
	}
}
