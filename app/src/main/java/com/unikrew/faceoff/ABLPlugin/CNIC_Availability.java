package com.unikrew.faceoff.ABLPlugin;


import com.unikrew.faceoff.ABLPlugin.model.CnicPostParams;
import com.unikrew.faceoff.ABLPlugin.model.ResponseDTO;
import com.unikrew.faceoff.ABLPlugin.ui.cnicavailability.CnicAvailabilityFragment;

import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.ABLPlugin.ui.fingerprintverification.FingerPrintFragment;
import com.unikrew.faceoff.ABLPlugin.ui.otpverification.OtpVerificationFragment;
import com.unikrew.faceoff.Config;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class CNIC_Availability extends AppCompatActivity {

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cnic_frame);

        fm = getSupportFragmentManager();
        setDefaultFragment();
    }

    private void setDefaultFragment() {

        FragmentTransaction ft = fm.beginTransaction();
        CnicAvailabilityFragment cnicAvailabilityFragment = new CnicAvailabilityFragment();
        ft.add(R.id.fragment_container, cnicAvailabilityFragment);
        ft.commit();

    }

    public void replaceOtpFragment(Bundle bundle) {
        FragmentTransaction ft = fm.beginTransaction();
        OtpVerificationFragment otpVerificationFragment = new OtpVerificationFragment();
        otpVerificationFragment.setArguments(bundle);
        ft.replace(R.id.fragment_container, otpVerificationFragment);
        ft.commit();
    }

    public void replaceFingerPrintFragment(Bundle bundle) {
        FragmentTransaction ft = fm.beginTransaction();
        FingerPrintFragment fingerPrintFragment = new FingerPrintFragment();
        fingerPrintFragment.setArguments(bundle);
        ft.replace(R.id.fragment_container, fingerPrintFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

}