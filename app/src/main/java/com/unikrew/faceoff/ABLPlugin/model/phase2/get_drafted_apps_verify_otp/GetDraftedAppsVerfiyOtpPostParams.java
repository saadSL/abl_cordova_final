package com.unikrew.faceoff.ABLPlugin.model.phase2.get_drafted_apps_verify_otp;

public class GetDraftedAppsVerfiyOtpPostParams {
    public GetDraftedAppsVerfiyOtpPostData data;
    public GetDraftedAppsVerfiyOtpPostPagination pagination;


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
