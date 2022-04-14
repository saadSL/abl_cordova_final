package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode;

import java.io.Serializable;

public class RegisterVerifyOtpResponse implements Serializable {
	private RegisterOtpDataResponse data;
	private RegisterOtpMessageResponse message;

	public RegisterOtpDataResponse getData(){
		return data;
	}

	public RegisterOtpMessageResponse getMessage(){
		return message;
	}

	@Override
	public String toString() {
		return "RegisterVerifyOtpResponse{" +
				"data=" + data +
				", message=" + message +
				'}';
	}
}
