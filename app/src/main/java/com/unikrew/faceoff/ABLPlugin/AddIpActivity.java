package com.unikrew.faceoff.ABLPlugin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ofss.digx.mobile.android.allied.databinding.ActivityAddIpBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.mobile_number.MobileNumberActivity;
import com.unikrew.faceoff.Config;

public class AddIpActivity extends BaseActivity {

    ActivityAddIpBinding addIpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setClicks();
    }

    private void setClicks() {
        addIpBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addIpBinding.etSetIp.getText().toString().isEmpty()) {
                    Toast.makeText(AddIpActivity.this, "No IP added, moving forward with the default one", Toast.LENGTH_SHORT).show();
                } else {
//                    Config.BASE_URL = addIpBinding.etSetIp.getText().toString();
                }
                startActivity(new Intent(AddIpActivity.this, MobileNumberActivity.class));
            }
        });
    }

    private void setBinding() {
        addIpBinding = ActivityAddIpBinding.inflate(getLayoutInflater());
        setContentView(addIpBinding.getRoot());
    }
}