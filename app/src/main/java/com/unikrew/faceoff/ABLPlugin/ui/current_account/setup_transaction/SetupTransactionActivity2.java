package com.unikrew.faceoff.ABLPlugin.ui.current_account.setup_transaction;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ofss.digx.mobile.android.allied.databinding.LayoutCurrentAccountSetupTransactionBinding;
import com.ofss.digx.mobile.android.allied.databinding.LayoutSetupTransaction2Binding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;

public class SetupTransactionActivity2 extends BaseActivity {
    private LayoutSetupTransaction2Binding setupTransactionBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
    }

    private void setBinding() {
        setupTransactionBinding = LayoutSetupTransaction2Binding.inflate(getLayoutInflater());
        setContentView(setupTransactionBinding.getRoot());
    }
}
