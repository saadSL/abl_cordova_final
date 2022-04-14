package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type;

import java.io.Serializable;

public class AccountTypeResponseMessage implements Serializable {
	private String description;
	private String status;

	public String getDescription(){
		return description;
	}

	public String getStatus(){
		return status;
	}

	@Override
	public String toString() {
		return "AccountTypeResponseMessage{" +
				"description='" + description + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
