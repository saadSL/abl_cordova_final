package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostUserAddressModel implements Serializable {
	private List<PostUserAddressDataItem> data = new ArrayList<>();

	public List<PostUserAddressDataItem> getData(){
		return data;
	}

	@Override
	public String toString() {
		return "PostUserAddressModel{" +
				"data=" + data +
				'}';
	}
}