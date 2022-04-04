package com.unikrew.faceoff.ABLPlugin.model.phase2.get_drafted_apps_verify_otp;

import java.io.Serializable;

public class GetDraftedAppsVerfiyOtpPostParams implements Serializable {

    public GetDraftedAppsVerfiyOtpPostData data = new GetDraftedAppsVerfiyOtpPostData();
    public GetDraftedAppsVerfiyOtpPostPagination pagination = new GetDraftedAppsVerfiyOtpPostPagination();


    public GetDraftedAppsVerfiyOtpPostData getData() {
        return data;
    }

    public void setData(GetDraftedAppsVerfiyOtpPostData data) {
        this.data = data;
    }

    public GetDraftedAppsVerfiyOtpPostPagination getPagination() {
        return pagination;
    }

    public void setPagination(GetDraftedAppsVerfiyOtpPostPagination pagination) {
        this.pagination = pagination;
    }
}
