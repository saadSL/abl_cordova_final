package com.unikrew.faceoff.ABLPlugin.ui.aasan_account;

import static androidx.navigation.ActivityKt.findNavController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.ofss.digx.mobile.android.allied.R;

public class AsaanAccountActivity extends AppCompatActivity {
    NavController navController ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asaan_account);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_nav_host_fragment);
         navController = navHostFragment.getNavController();
    }

    @Override
    public void onBackPressed() {
        if (navController.navigateUp() == false){
            //navigateUp() returns false if there are no more fragments to pop
            onBackPressed();
        }else {
            navController.navigateUp();
        }

    }
}