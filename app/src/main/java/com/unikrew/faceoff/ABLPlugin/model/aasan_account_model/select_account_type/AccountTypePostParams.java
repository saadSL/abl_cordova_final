package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type;

import java.io.Serializable;

public class AccountTypePostParams implements Serializable {
	private AccountTypeData data = new AccountTypeData();

	public AccountTypeData getData(){
		return data;
	}
}
