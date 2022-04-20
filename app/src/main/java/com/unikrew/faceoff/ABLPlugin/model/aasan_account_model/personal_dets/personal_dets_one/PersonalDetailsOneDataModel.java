package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_one;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonalDetailsOneDataModel implements Serializable {
	private List<PersonalDetailsOneConsumerListItemModel> consumerList = new ArrayList<>();

	public void setConsumerList(List<PersonalDetailsOneConsumerListItemModel> consumerList) {
		this.consumerList = consumerList;
	}

	public List<PersonalDetailsOneConsumerListItemModel> getConsumerList(){
		return consumerList;
	}
}