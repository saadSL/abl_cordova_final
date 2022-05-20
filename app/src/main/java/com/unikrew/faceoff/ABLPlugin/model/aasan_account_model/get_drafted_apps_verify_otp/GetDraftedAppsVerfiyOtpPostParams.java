package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp;

import java.io.Serializable;

public class GetDraftedAppsVerfiyOtpPostParams implements Serializable {

    private GetDraftedAppsVerfiyOtpPostData data = new GetDraftedAppsVerfiyOtpPostData();
    private GetDraftedAppsVerfiyOtpPostPagination pagination = new GetDraftedAppsVerfiyOtpPostPagination();


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

    @Override
    public String toString() {
        return "GetDraftedAppsVerfiyOtpPostParams{" +
                "data=" + data +
                ", pagination=" + pagination +
                '}';
    }
}
