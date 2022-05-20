package com.unikrew.faceoff.ABLPlugin.ui.current_account.personal_details;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ofss.digx.mobile.android.allied.databinding.OrganizationDetailsBinding;
import com.ofss.digx.mobile.android.allied.databinding.TaxResidentDetailsBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;

public class PersonalDetailsActivity extends BaseActivity {
    private TaxResidentDetailsBinding taxResidentDetailsBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
    }

    private void setBinding() {
        taxResidentDetailsBinding = TaxResidentDetailsBinding.inflate(getLayoutInflater());
        setContentView(taxResidentDetailsBinding.getRoot());
    }
}
