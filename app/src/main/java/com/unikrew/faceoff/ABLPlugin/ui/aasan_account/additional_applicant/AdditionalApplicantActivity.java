package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.additional_applicant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.LayoutAdditionalApplicantBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodePostParams;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponse;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponseData;
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
    private LookUpCodePostParams relationshipPostParams;
    private ArrayList<LookUpCodeResponseData> relationshipResponseData;
    public static int SELECTED_RELATION_CODE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setLayout();
        setViewModel();
        setClicks();
        getRelationships();
        observe();
        setLogoLayout(layoutAdditionalApplicantBinding.logoToolbar.tvDate);
    }

    private void observe() {
        applicantViewModel.relationshipSuccessResponse.observe(this, new Observer<LookUpCodeResponse>() {
            @Override
            public void onChanged(LookUpCodeResponse relationshipResponse) {
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

    private void setRelationshipSpinner(LookUpCodeResponse relationshipResponse) {
        relationshipResponseData = relationshipResponse.getData();
        setSpinner();
    }

    private void setSpinner() {

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
            layoutAdditionalApplicantBinding.spRelationshipName.setAdapter(dataAdapter);
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
        relationshipPostParams = new LookUpCodePostParams();
    }

    private void setClicks() {
        layoutAdditionalApplicantBinding.btnContainer.btnNext.setOnClickListener(this);
        layoutAdditionalApplicantBinding.btnContainer.btBack.setOnClickListener(this);
    }


    private void setBinding() {
        layoutAdditionalApplicantBinding = LayoutAdditionalApplicantBinding.inflate(getLayoutInflater());
        setContentView(layoutAdditionalApplicantBinding.getRoot());
    }


    private void setLayout() {
        layoutAdditionalApplicantBinding.steps.screenHeader.stepsHeading1.setText("Additional");
        layoutAdditionalApplicantBinding.steps.screenHeader.stepsHeading2.setText("Applicant");
        layoutAdditionalApplicantBinding.steps.step1.setBackground(this.getDrawable(R.color.custom_blue));
        layoutAdditionalApplicantBinding.steps.step2.setBackground(this.getDrawable(R.color.custom_blue));
        layoutAdditionalApplicantBinding.steps.step3.setBackground(this.getDrawable(R.color.light_gray));
        layoutAdditionalApplicantBinding.steps.step4.setBackground(this.getDrawable(R.color.light_gray));
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
        SELECTED_RELATION_CODE =  relationshipResponseData.get(layoutAdditionalApplicantBinding.spRelationshipName.getSelectedItemPosition()).getId();
        Intent intent = new Intent(this, MobileNumberActivity.class);
        intent.putExtra("isJoint",true);
        startActivity(intent);
    }
}
