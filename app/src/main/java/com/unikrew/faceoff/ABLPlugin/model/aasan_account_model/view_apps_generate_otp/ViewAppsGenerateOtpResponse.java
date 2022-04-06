package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp;

import java.io.Serializable;

public class ViewAppsGenerateOtpResponse implements Serializable {

    public ViewAppsGenerateOtpResponseData data;
    public ViewAppsGenerateOtpResponseMessage message;

    public ViewAppsGenerateOtpResponseData getData() {
        return data;
    }

    public void setData(ViewAppsGenerateOtpResponseData data) {
        this.data = data;
    }

    public ViewAppsGenerateOtpResponseMessage getMessage() {
        return message;
    }

    public void setMessage(ViewAppsGenerateOtpResponseMessage message) {
        this.message = message;
    }

}
