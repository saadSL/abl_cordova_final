package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.remitter_details;

import java.io.Serializable;

public class PdaRemitterDetailListItem implements Serializable {
	private int accountId;
	private int pdaRemitterDetailId;
	private String remitterName;
	private String relationshipWithRemitter;

	public int getAccountId(){
		return accountId;
	}

	public int getPdaRemitterDetailId(){
		return pdaRemitterDetailId;
	}

	public String getRemitterName(){
		return remitterName;
	}

	public String getRelationshipWithRemitter(){
		return relationshipWithRemitter;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public void setPdaRemitterDetailId(int pdaRemitterDetailId) {
		this.pdaRemitterDetailId = pdaRemitterDetailId;
	}

	public void setRemitterName(String remitterName) {
		this.remitterName = remitterName;
	}

	public void setRelationshipWithRemitter(String relationshipWithRemitter) {
		this.relationshipWithRemitter = relationshipWithRemitter;
	}

	@Override
	public String toString() {
		return "PdaRemitterDetailListItem{" +
				"accountId=" + accountId +
				", pdaRemitterDetailId=" + pdaRemitterDetailId +
				", remitterName='" + remitterName + '\'' +
				", relationshipWithRemitter='" + relationshipWithRemitter + '\'' +
				'}';
	}
}
