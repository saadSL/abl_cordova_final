package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.additional_applicant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
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
import java.util.List;

public class AdditionalApplicantActivity extends BaseActivity implements View.OnClickListener {

    private LayoutAdditionalApplicantBinding layoutAdditionalApplicantBinding;
    private AdditionalApplicantViewModel applicantViewModel;
    private RelationshipPostParams relationshipPostParams;
    private ArrayList<RelationshipResponseData> relationshipResponseData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setViewModel();
        setClicks();
        getRelationships();
        observe();
        setLogoLayout(layoutAdditionalApplicantBinding.logoToolbar.tvDate);
    }

    private void observe() {
        applicantViewModel.relationshipSuccessResponse.observe(this, new Observer<RelationshipResponse>() {
            @Override
            public void onChanged(RelationshipResponse relationshipResponse) {
                dismissLoading();
                setRelationshipSpinner(relationshipResponse);
            }
        });

        applicantViewModel.relationshipErrorResponse.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                showAlert(Config.successType, s);
            }
        });
    }

    private void setRelationshipSpinner(RelationshipResponse relationshipResponse) {
        relationshipResponseData = relationshipResponse.getData();
        setSpinner(relationshipResponseData, layoutAdditionalApplicantBinding.spRelationshipName);
    }

    private void setSpinner(ArrayList<RelationshipResponseData> relationshipResponseData, AppCompatSpinner spRelationshipName) {

        List<String> _allItemsArray = new ArrayList<>();
        if (relationshipResponseData.size() > 0) {
            // Spinner Drop down elements
            for (int i = 0; i < relationshipResponseData.size(); i++) {
                _allItemsArray.add(relationshipResponseData.get(i).getName());
            }

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, _allItemsArray);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spRelationshipName.setAdapter(dataAdapter);
        }
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


    private void setBinding() {
        layoutAdditionalApplicantBinding = LayoutAdditionalApplicantBinding.inflate(getLayoutInflater());
        setContentView(layoutAdditionalApplicantBinding.getRoot());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                openMobileNumberActivity();
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
