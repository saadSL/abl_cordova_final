package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_two;

import java.io.Serializable;

public class PersonalDetailsTwoConsumerListItemModel implements Serializable {
	private String emailAddress;
	private String landlineNumber;
	private boolean isPrimary;

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean primary) {
		isPrimary = primary;
	}

	public String getLandlineNumber() {
		return landlineNumber;
	}

	public void setLandlineNumber(String landlineNumber) {
		this.landlineNumber = landlineNumber;
	}

	private int rdaCustomerAccInfoId;
	private int rdaCustomerProfileId;

	public String getEmailAddress(){
		return emailAddress;
	}

	public int getRdaCustomerAccInfoId(){
		return rdaCustomerAccInfoId;
	}

	public int getRdaCustomerProfileId(){
		return rdaCustomerProfileId;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setRdaCustomerAccInfoId(int rdaCustomerAccInfoId) {
		this.rdaCustomerAccInfoId = rdaCustomerAccInfoId;
	}

	public void setRdaCustomerProfileId(int rdaCustomerProfileId) {
		this.rdaCustomerProfileId = rdaCustomerProfileId;
	}
}
