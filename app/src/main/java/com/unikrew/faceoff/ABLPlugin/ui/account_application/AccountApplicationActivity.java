package com.unikrew.faceoff.ABLPlugin.ui.account_application;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerifyOtpResponse;
import com.unikrew.faceoff.Config;

public class AccountApplicationActivity extends BaseActivity {
    GetDraftedAppsVerifyOtpResponse res;
    AccountApplicationAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_list_layout);

        res = (GetDraftedAppsVerifyOtpResponse) getIntent().getSerializableExtra(Config.APP_LIST);

        initializeAdapter();
    }

    private void initializeAdapter() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new AccountApplicationAdapter(res.getData().getAppList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
