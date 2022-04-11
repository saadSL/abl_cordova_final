package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.ofss.digx.mobile.android.allied.databinding.ActivitySelectAccountTypeBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.mobile_network.MobileNetworkPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.mobile_network.MobileNetworkResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.mobile_network.MobileNetworkResponseData;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;
import java.util.List;

public class SelectAccountTypeActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private ActivitySelectAccountTypeBinding accountTypeBinding;
    private SelectBankingModeViewModel selectBankingModeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setViewModel();
        getPurposeOfAccount();
        setObservers();
        clicks();
    }

    private void clicks() {
    }

    private void setObservers() {
        selectBankingModeViewModel.purposeOfAccountLiveData.observe(this, new Observer<MobileNetworkResponse>() {
            @Override
            public void onChanged(MobileNetworkResponse mobileNetworkResponse) {
                Log.d("branchesResponse", "onChanged: " + mobileNetworkResponse);
                dismissLoading();
                setSpinner(mobileNetworkResponse);
            }
        });

        selectBankingModeViewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                dismissLoading();
            }
        });
    }

    private void setSpinner(MobileNetworkResponse mobileNetworkResponse) {
        ArrayList<MobileNetworkResponseData> purposeArray = mobileNetworkResponse.getData();
        List<String> _allPurposes = new ArrayList<>();


        if (purposeArray.size() > 0) {
            // Spinner click listener
            accountTypeBinding.spinnerAllPurposes.setOnItemSelectedListener(this);
//            _allBranches.add(getString(R.string.choose_prefered_branch));
            // Spinner Drop down elements
            for (int i = 0; i < purposeArray.size(); i++) {
                _allPurposes.add(purposeArray.get(i).getName());
            }

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, _allPurposes);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            accountTypeBinding.spinnerAllPurposes.setAdapter(dataAdapter);
        }
    }

    private void getPurposeOfAccount() {
        showLoading();
        MobileNetworkPostParams mobileNetworkPostParams = new MobileNetworkPostParams();
        mobileNetworkPostParams.getData().codeTypeId = 1081;
        selectBankingModeViewModel.getPurposeOfAccount(mobileNetworkPostParams);
    }

    private void setBinding() {
        accountTypeBinding = ActivitySelectAccountTypeBinding.inflate(getLayoutInflater());
        setContentView(accountTypeBinding.getRoot());
    }

    private void setViewModel() {
        selectBankingModeViewModel = new ViewModelProvider(this).get(SelectBankingModeViewModel.class);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}