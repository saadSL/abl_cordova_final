package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.employment_details;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ofss.digx.mobile.android.allied.databinding.EmploymentDetailsBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;

public class EmploymentDetailsActivity extends BaseActivity {

    EmploymentDetailsBinding employmentDetailsBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
    }

    private void setBinding() {
        employmentDetailsBinding = EmploymentDetailsBinding.inflate(getLayoutInflater());
        setContentView(employmentDetailsBinding.getRoot());
    }
}
