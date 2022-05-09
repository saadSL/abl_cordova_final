package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.additional_applicant;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.LayoutAdditionalApplicantBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.Config;

public class AdditionalApplicantActivity extends BaseActivity implements View.OnClickListener {

    LayoutAdditionalApplicantBinding layoutAdditionalApplicantBinding;
    AdditionalApplicantAdapter additionalApplicantAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setAdapter();
        setClicks();
    }

    private void setClicks() {
        layoutAdditionalApplicantBinding.btnContainer.btnNext.setOnClickListener(this);
        layoutAdditionalApplicantBinding.btnContainer.btBack.setOnClickListener(this);
    }

    private void setAdapter() {
        RecyclerView recyclerView = findViewById(R.id.rv_additional_applicant);
        additionalApplicantAdapter = new AdditionalApplicantAdapter(
                getIntent().getIntExtra(Config.JOINT_APPLICANTS_NUMBER,0),
                this
        );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(additionalApplicantAdapter);
    }

    private void setBinding() {
        layoutAdditionalApplicantBinding = LayoutAdditionalApplicantBinding.inflate(getLayoutInflater());
        setContentView(layoutAdditionalApplicantBinding.getRoot());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:

                break;
            case R.id.bt_back:
                finish();
                break;
        }
    }
}
