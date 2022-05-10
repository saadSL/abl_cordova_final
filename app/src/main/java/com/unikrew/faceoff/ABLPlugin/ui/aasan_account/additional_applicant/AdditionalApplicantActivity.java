package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.additional_applicant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.LayoutAdditionalApplicantBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.joint_account_model.joint_applicant.ApplicantModel;
import com.unikrew.faceoff.ABLPlugin.model.joint_account_model.relationship.RelationshipPostData;
import com.unikrew.faceoff.ABLPlugin.model.joint_account_model.relationship.RelationshipPostParams;
import com.unikrew.faceoff.ABLPlugin.model.joint_account_model.relationship.RelationshipResponse;
import com.unikrew.faceoff.ABLPlugin.model.joint_account_model.relationship.RelationshipResponseData;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.mobile_number.MobileNumberActivity;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;

public class AdditionalApplicantActivity extends BaseActivity implements View.OnClickListener {

    LayoutAdditionalApplicantBinding layoutAdditionalApplicantBinding;
    AdditionalApplicantAdapter additionalApplicantAdapter;

    private ArrayList<RelationshipResponseData> selectedRelationship;
    private AdditionalApplicantViewModel applicantViewModel;
    private RelationshipPostParams relationshipPostParams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setViewModel();
        setClicks();
        getRelationships();
        observe();
    }

    private void observe() {
        applicantViewModel.relationshipSuccessResponse.observe(this, new Observer<RelationshipResponse>() {
            @Override
            public void onChanged(RelationshipResponse relationshipResponse) {
                dismissLoading();
                setAdapter(relationshipResponse);
            }
        });

        applicantViewModel.relationshipErrorResponse.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                showAlert(Config.successType,s);
            }
        });
    }

    private void getRelationships() {
        setRelationshipPostParams();
        applicantViewModel.getRelationships(relationshipPostParams);
        showLoading();
    }

    private void setRelationshipPostParams() {
        relationshipPostParams.getData().setCodeTypeId(Config.RELATIONSHIP_ID);
    }

    private void setViewModel() {
        applicantViewModel = new ViewModelProvider(this).get(AdditionalApplicantViewModel.class);
        relationshipPostParams = new RelationshipPostParams();
    }

    private void setClicks() {
        layoutAdditionalApplicantBinding.btnContainer.btnNext.setOnClickListener(this);
        layoutAdditionalApplicantBinding.btnContainer.btBack.setOnClickListener(this);
    }

    private void setAdapter(RelationshipResponse relationshipResponse) {
        selectedRelationship = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.rv_additional_applicant);
        additionalApplicantAdapter = new AdditionalApplicantAdapter(
                getIntent().getIntExtra(Config.JOINT_APPLICANTS_NUMBER,0),
                this,
                selectedRelationship,
                relationshipResponse
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
                showAlert(Config.successType,selectedRelationship.toString());
                break;
            case R.id.bt_back:
                finish();
                break;
        }
    }

    private void openMobileNumberActivity() {
        Intent intent = new Intent(this, MobileNumberActivity.class);
        startActivity(intent);
    }
}
