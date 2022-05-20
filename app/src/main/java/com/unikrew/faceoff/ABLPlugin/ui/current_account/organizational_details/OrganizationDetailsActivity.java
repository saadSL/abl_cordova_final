package com.unikrew.faceoff.ABLPlugin.ui.current_account.organizational_details;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ofss.digx.mobile.android.allied.databinding.OrganizationDetailsBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;

public class OrganizationDetailsActivity extends BaseActivity {

    private OrganizationDetailsBinding organizationDetailsBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
    }

    private void setBinding() {
        organizationDetailsBinding = OrganizationDetailsBinding.inflate(getLayoutInflater());
        setContentView(organizationDetailsBinding.getRoot());
    }
}
