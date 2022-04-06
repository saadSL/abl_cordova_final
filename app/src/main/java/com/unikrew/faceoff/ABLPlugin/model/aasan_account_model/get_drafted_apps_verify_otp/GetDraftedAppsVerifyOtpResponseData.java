package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp;

import java.io.Serializable;
import java.util.ArrayList;

public class GetDraftedAppsVerifyOtpResponseData implements Serializable {
    public ArrayList<Object> appList;
    public Object accessToken;

    public ArrayList<Object> getAppList() {
        return appList;
    }

    public void setAppList(ArrayList<Object> appList) {
        this.appList = appList;
    }

    public Object getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(Object accessToken) {
        this.accessToken = accessToken;
    }
}
