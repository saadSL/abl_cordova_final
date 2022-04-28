package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.remitter_details;

import java.io.Serializable;

public class RemitterDetailsPostModel implements Serializable {
	private RemitterDetailsDataModel data = new RemitterDetailsDataModel();

	public RemitterDetailsDataModel getData(){
		return data;
	}
}
