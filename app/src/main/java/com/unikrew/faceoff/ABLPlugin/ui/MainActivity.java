package com.unikrew.faceoff.ABLPlugin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.ABLPlugin.CNIC_Availability;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void cnicActivity(View view) {
        Intent i = new Intent(view.getContext(), CNIC_Availability.class);
        startActivity(i);
    }

}