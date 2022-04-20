package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_three;

import java.io.Serializable;

public class PersonalDetailsThreeConsumerListItemModel implements Serializable {
	private int occupationId;
	private boolean isPrimary;
	private int rdaCustomerAccInfoId;
	private int rdaCustomerProfileId;
	private int professionId;

	public void setOccupationId(int occupationId) {
		this.occupationId = occupationId;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean primary) {
		isPrimary = primary;
	}

	public void setRdaCustomerAccInfoId(int rdaCustomerAccInfoId) {
		this.rdaCustomerAccInfoId = rdaCustomerAccInfoId;
	}

	public void setRdaCustomerProfileId(int rdaCustomerProfileId) {
		this.rdaCustomerProfileId = rdaCustomerProfileId;
	}

	public void setProfessionId(int professionId) {
		this.professionId = professionId;
	}

	public int getOccupationId(){
		return occupationId;
	}

	public boolean isIsPrimary(){
		return isPrimary;
	}

	public int getRdaCustomerAccInfoId(){
		return rdaCustomerAccInfoId;
	}

	public int getRdaCustomerProfileId(){
		return rdaCustomerProfileId;
	}

	public int getProfessionId(){
		return professionId;
	}
}
