package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_three;

import java.io.Serializable;

public class PersonalDetailsThreePostModel implements Serializable {
	private PersonalDetailsThreeDataModel data = new  PersonalDetailsThreeDataModel();

	public void setData(PersonalDetailsThreeDataModel data) {
		this.data = data;
	}

	public PersonalDetailsThreeDataModel getData(){
		return data;
	}
}
