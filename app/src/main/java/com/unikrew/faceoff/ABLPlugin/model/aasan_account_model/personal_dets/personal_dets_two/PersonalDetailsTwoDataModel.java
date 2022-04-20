package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_two;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonalDetailsTwoDataModel implements Serializable {
	private List<PersonalDetailsTwoConsumerListItemModel> consumerList= new ArrayList<>();

	public List<PersonalDetailsTwoConsumerListItemModel> getConsumerList(){
		return consumerList;
	}

	public void setConsumerList(List<PersonalDetailsTwoConsumerListItemModel> consumerList) {
		this.consumerList = consumerList;
	}
}