package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode;

import java.io.Serializable;

public class GetBranchDataModel implements Serializable {
	private String categoryType;

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	private String distance;
	private String latitude;
	private String branchName;
	private String longitude;

	public String getCategoryType(){
		return categoryType;
	}

	public String getDistance(){
		return distance;
	}

	public String getLatitude(){
		return latitude;
	}

	public String getBranchName(){
		return branchName;
	}

	public String getLongitude(){
		return longitude;
	}
}
