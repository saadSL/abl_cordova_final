package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.account_application;

import androidx.cardview.widget.CardView;

import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerifyOtpResponseAppList;

public interface AccountApplicationInterface {
    public void deleteAppListAt(GetDraftedAppsVerifyOtpResponseAppList getDraftedAppsVerifyOtpResponseAppList,int position);

    public void setSelectionAt(int position);

}
