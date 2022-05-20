package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_three;

import java.io.Serializable;

public class PersonalDetailsThreeConsumerListItemModel implements Serializable {
	private Integer occupationId;
	private boolean isPrimary;
	private int rdaCustomerAccInfoId;
	private int rdaCustomerProfileId;
	private Integer professionId;

	public void setOccupationId(Integer occupationId) {
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

	public void setProfessionId(Integer professionId) {
		this.professionId = professionId;
	}

	public Integer getOccupationId(){
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

	public Integer getProfessionId(){
		return professionId;
	}
}
