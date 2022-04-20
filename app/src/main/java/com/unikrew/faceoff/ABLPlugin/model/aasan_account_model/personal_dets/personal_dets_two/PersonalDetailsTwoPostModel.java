package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_two;

import java.io.Serializable;

public class PersonalDetailsTwoPostModel implements Serializable {
	private PersonalDetailsTwoDataModel data = new PersonalDetailsTwoDataModel();

	public PersonalDetailsTwoDataModel getData(){
		return data;
	}

	public void setData(PersonalDetailsTwoDataModel data) {
		this.data = data;
	}
}
