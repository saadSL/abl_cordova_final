package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_one;

import java.io.Serializable;

public class PersonalDetailsOnePostModel implements Serializable {
	private PersonalDetailsOneDataModel data = new PersonalDetailsOneDataModel();

	public void setData(PersonalDetailsOneDataModel data) {
		this.data = data;
	}

	public PersonalDetailsOneDataModel getData(){
		return data;
	}
}
