package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VerifyOtpData implements Serializable {
	private List<ConsumerListItemVerifyOtp> consumerList = new ArrayList<>();
	private int noOfJointApplicatns;

	public List<ConsumerListItemVerifyOtp> getConsumerList(){
		return consumerList;
	}

	public int getNoOfJointApplicatns(){
		return noOfJointApplicatns;
	}

	public void setConsumerList(List<ConsumerListItemVerifyOtp> consumerList) {
		this.consumerList = consumerList;
	}

	public void setNoOfJointApplicatns(int noOfJointApplicatns) {
		this.noOfJointApplicatns = noOfJointApplicatns;
	}

	@Override
	public String toString() {
		return "VerifyOtpData{" +
				"consumerList=" + consumerList +
				", noOfJointApplicatns=" + noOfJointApplicatns +
				'}';
	}
}