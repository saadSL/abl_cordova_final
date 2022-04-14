package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode;

import java.io.Serializable;
import java.util.List;

public class RegisterOtpDataResponse implements Serializable {
	private Object pdaRemitterDetailList;
	private List<ConsumerListItemResponse> consumerList;
	private int sessionTimeout;
	private int noOfJointApplicatns;
	private Object channelId;

	public Object getPdaRemitterDetailList(){
		return pdaRemitterDetailList;
	}

	public List<ConsumerListItemResponse> getConsumerList(){
		return consumerList;
	}

	public int getSessionTimeout(){
		return sessionTimeout;
	}

	public int getNoOfJointApplicatns(){
		return noOfJointApplicatns;
	}

	public Object getChannelId(){
		return channelId;
	}

	@Override
	public String toString() {
		return "RegisterOtpDataResponse{" +
				"pdaRemitterDetailList=" + pdaRemitterDetailList +
				", consumerList=" + consumerList +
				", sessionTimeout=" + sessionTimeout +
				", noOfJointApplicatns=" + noOfJointApplicatns +
				", channelId=" + channelId +
				'}';
	}
}