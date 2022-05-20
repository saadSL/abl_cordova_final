package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode;

import java.io.Serializable;

public class GetBranchPostModel implements Serializable {
	public void setData(GetBranchDataModel data) {
		this.data = data;
	}

	private GetBranchDataModel data = new GetBranchDataModel();

	@Override
	public String toString() {
		return "GetBranchPostModel{" +
				"data=" + data +
				'}';
	}

	public GetBranchDataModel getData(){
		return data;
	}
}
