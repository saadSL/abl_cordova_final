package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode;

public class SuggestBranchListItemModel {
	private String branchCode;
	private String tBranchCode;
	private String cityName;
	private double distance;
	private double latitude;
	private String branchName;
	private int id;
	private double longitude;

	public String getBranchCode(){
		return branchCode;
	}

	public String getTBranchCode(){
		return tBranchCode;
	}

	public String getCityName(){
		return cityName;
	}

	public double getDistance(){
		return distance;
	}

	public double getLatitude(){
		return latitude;
	}

	public String getBranchName(){
		return branchName;
	}

	public int getId(){
		return id;
	}

	public double getLongitude(){
		return longitude;
	}
}
