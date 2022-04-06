package com.unikrew.faceoff.ABLPlugin.model.phase2.get_drafted_apps_verify_otp;

import java.io.Serializable;
import java.util.ArrayList;

public class GetDraftedAppsVerifyOtpResponseData implements Serializable {
    public ArrayList<GetDraftedAppsVerifyOtpResponseAppList> appList;
    public String accessToken;

    public ArrayList<GetDraftedAppsVerifyOtpResponseAppList> getAppList() {
        return appList;
    }

    public void setAppList(ArrayList<GetDraftedAppsVerifyOtpResponseAppList> appList) {
        this.appList = appList;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
