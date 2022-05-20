package com.unikrew.faceoff.ABLPlugin.ui.current_account.nationality;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivitySelectNationalityBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;

public class NationalityActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener,INationality {
    private ActivitySelectNationalityBinding nationalityBinding;
    private NationalityAdapter nationalityAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setNationalityAdapter();
        setClicks();

    }



    private void setClicks() {
        nationalityBinding.fabNationality.setOnClickListener(this);
        nationalityBinding.swNationality.setOnCheckedChangeListener(this);
        nationalityBinding.btnContainer.btnNext.setOnClickListener(this);
        nationalityBinding.btnContainer.btBack.setOnClickListener(this);
    }

    private void setNationalityAdapter() {

        nationalityAdapter = new NationalityAdapter(1,this, this);

        nationalityBinding.rvNationality.setLayoutManager(new LinearLayoutManager(this));
        nationalityBinding.rvNationality.setAdapter(nationalityAdapter);
    }

    private void setBinding() {
        nationalityBinding = ActivitySelectNationalityBinding.inflate(getLayoutInflater());
        setContentView(nationalityBinding.getRoot());
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.fab_nationality:
                addNewNationality();
                break;
            case R.id.btn_next:
                showAlert(Config.successType,"Next Screen");
                break;
            case R.id.bt_back:
                finish();
                break;
        }
    }

    private void addNewNationality() {
        nationalityAdapter.setList();
    }

    @Override
    public void onCheckedChanged(CompoundButton switchButton, boolean isChecked) {
        switch (switchButton.getId()){
            case R.id.sw_nationality:
                if (isChecked){
                    openNationalityLayout();
                }else{
                    closeNationalityLayout();
                }
                break;
        }
    }

    private void closeNationalityLayout() {
        nationalityBinding.llNationalityRvFab.setVisibility(View.GONE);
    }

    private void openNationalityLayout() {
        nationalityBinding.llNationalityRvFab.setVisibility(View.VISIBLE);
    }

    @Override
    public void getNationality(/*Nationality selectedNationality*/) {
//        this.selectedNationality = selectedNationality;
    }
}
