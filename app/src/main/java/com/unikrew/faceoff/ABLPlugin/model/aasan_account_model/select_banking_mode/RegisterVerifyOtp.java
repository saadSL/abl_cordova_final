package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode;

import java.io.Serializable;

public class RegisterVerifyOtp implements Serializable {
	private VerifyOtpData data = new VerifyOtpData();

	public VerifyOtpData getData(){
		return data;
	}

	@Override
	public String toString() {
		return "RegisterVerifyOtp{" +
				"data=" + data +
				'}';
	}
}
