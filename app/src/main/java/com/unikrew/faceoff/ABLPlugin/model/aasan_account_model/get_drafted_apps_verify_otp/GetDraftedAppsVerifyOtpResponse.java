package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp;

import java.io.Serializable;

public class GetDraftedAppsVerifyOtpResponse implements Serializable{
    public GetDraftedAppsVerifyOtpResponseData data;
    public GetDraftedAppsVerifyOtpResponseMessage message;

    public GetDraftedAppsVerifyOtpResponseData getData() {
        return data;
    }

    public void setData(GetDraftedAppsVerifyOtpResponseData data) {
        this.data = data;
    }

    public GetDraftedAppsVerifyOtpResponseMessage getMessage() {
        return message;
    }

    public void setMessage(GetDraftedAppsVerifyOtpResponseMessage message) {
        this.message = message;
    }
}
