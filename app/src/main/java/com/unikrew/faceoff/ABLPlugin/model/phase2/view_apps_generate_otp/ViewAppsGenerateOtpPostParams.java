package com.unikrew.faceoff.ABLPlugin.model.phase2.view_apps_generate_otp;

import java.io.Serializable;

public class ViewAppsGenerateOtpPostParams implements Serializable {
    public ViewAppsGenerateOtpPostData viewAppsGenerateOtpPostData = new ViewAppsGenerateOtpPostData();

    public ViewAppsGenerateOtpPostData getViewAppsGenerateOtpPostData() {
        return viewAppsGenerateOtpPostData;
    }

    public void setViewAppsGenerateOtpPostData(ViewAppsGenerateOtpPostData viewAppsGenerateOtpPostData) {
        this.viewAppsGenerateOtpPostData = viewAppsGenerateOtpPostData;
    }
}
