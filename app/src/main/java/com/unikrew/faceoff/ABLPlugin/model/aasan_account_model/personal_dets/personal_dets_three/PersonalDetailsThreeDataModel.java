package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_three;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonalDetailsThreeDataModel implements Serializable {
	private List<PersonalDetailsThreeConsumerListItemModel> consumerList = new ArrayList<>();

	public void setConsumerList(List<PersonalDetailsThreeConsumerListItemModel> consumerList) {
		this.consumerList = consumerList;
	}

	public List<PersonalDetailsThreeConsumerListItemModel> getConsumerList(){
		return consumerList;
	}
}