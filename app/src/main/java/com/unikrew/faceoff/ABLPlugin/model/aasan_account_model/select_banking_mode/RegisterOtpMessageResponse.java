package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode;

import java.io.Serializable;

public class RegisterOtpMessageResponse implements Serializable {
	private String description;
	private String status;

	public String getDescription(){
		return description;
	}

	public String getStatus(){
		return status;
	}
}
