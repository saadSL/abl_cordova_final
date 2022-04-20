package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_one;

import java.io.Serializable;

public class PersonalDetailsOneConsumerListItemModel implements Serializable {
	private Integer rdaCustomerProfileId = null;
	private Integer rdaCustomerAccInfoId = null;
	private boolean isPrimary;
	private String fullName;
	private String fatherHusbandName;
	private String motherMaidenName;

	public String getPlaceofBirth() {
		return placeofBirth;
	}

	public void setPlaceofBirth(String placeofBirth) {
		this.placeofBirth = placeofBirth;
	}

	private String placeofBirth;

	public Integer getRdaCustomerProfileId() {
		return rdaCustomerProfileId;
	}

	public void setRdaCustomerProfileId(Integer rdaCustomerProfileId) {
		this.rdaCustomerProfileId = rdaCustomerProfileId;
	}

	public Integer getRdaCustomerAccInfoId() {
		return rdaCustomerAccInfoId;
	}

	public void setRdaCustomerAccInfoId(Integer rdaCustomerAccInfoId) {
		this.rdaCustomerAccInfoId = rdaCustomerAccInfoId;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean primary) {
		isPrimary = primary;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFatherHusbandName() {
		return fatherHusbandName;
	}

	public void setFatherHusbandName(String fatherHusbandName) {
		this.fatherHusbandName = fatherHusbandName;
	}

	public String getMotherMaidenName() {
		return motherMaidenName;
	}

	public void setMotherMaidenName(String motherMaidenName) {
		this.motherMaidenName = motherMaidenName;
	}
}
