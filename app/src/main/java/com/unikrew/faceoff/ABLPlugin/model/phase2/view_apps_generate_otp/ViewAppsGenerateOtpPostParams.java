package com.unikrew.faceoff.ABLPlugin.model.phase2.view_apps_generate_otp;

import java.io.Serializable;

public class ViewAppsGenerateOtpPostParams implements Serializable {

    public ViewAppsGenerateOtpPostData data = new ViewAppsGenerateOtpPostData();

    public ViewAppsGenerateOtpPostData getData() {
        return data;
    }

    public void setData(ViewAppsGenerateOtpPostData data) {
        this.data = data;
    }

}
