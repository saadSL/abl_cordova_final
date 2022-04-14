package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type;

import java.io.Serializable;

public class AccountTypeResponse implements Serializable {
	private AccountTypeResponseData data;
	private AccountTypeResponseMessage message;

	public AccountTypeResponseData getData(){
		return data;
	}

	public AccountTypeResponseMessage getMessage(){
		return message;
	}

	@Override
	public String toString() {
		return "AccountTypeResponse{" +
				"data=" + data +
				", message=" + message +
				'}';
	}
}
