package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.review_documents;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ReviewDetailsBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponseAccountInformation;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AccountInformationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.Config;

public class ReviewDocumentActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ReviewDetailsBinding reviewDetailsBinding;
    private boolean termsAndConditions = false;
    private boolean declaration = false;
    private Boolean IS_RESUMED;


    private RegisterVerifyOtpResponse registerVerifyOtpResponse;
    private GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setLayout();
        setClicks();
        getSharedPrefData();
        setResponse();
        setLogoLayout(reviewDetailsBinding.logoToolbar.tvDate);
    }

    private void setResponse() {
        if (IS_RESUMED){
            setAccountDetails();
        }else{

        }
    }

    private void setAccountDetails() {
        if (IS_RESUMED){
            GetConsumerAccountDetailsResponseAccountInformation accountInformation = getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getAccountInformation();
            reviewDetailsBinding.tvAccountPurpose.setText(accountInformation.getPurposeOfAccount().toString());
            reviewDetailsBinding.etAccountPurpose.setText(accountInformation.getPurposeOfAccount().toString());
            if (accountInformation.getProofOfIncomeInd() == "1"){
                reviewDetailsBinding.tvProofOfIncome.setText("Yes");
            }else{
                reviewDetailsBinding.tvProofOfIncome.setText("No");
            }

            if (accountInformation.getProofOfIncomeInd() == "1"){
                reviewDetailsBinding.etProofOfIncome.setText("Yes");
            }else{
                reviewDetailsBinding.etProofOfIncome.setText("No");
            }

        }else{

            AccountInformationResponse accountInformation = registerVerifyOtpResponse.getData().getConsumerList().get(0).getAccountInformation();
            reviewDetailsBinding.tvAccountPurpose.setText(accountInformation.getPurposeOfAccount().toString());
            reviewDetailsBinding.etAccountPurpose.setText(accountInformation.getPurposeOfAccount().toString());
            if (accountInformation.getProofOfIncomeInd() == "1"){
                reviewDetailsBinding.tvProofOfIncome.setText("Yes");
            }else{
                reviewDetailsBinding.tvProofOfIncome.setText("No");
            }

            if (accountInformation.getProofOfIncomeInd() == "1"){
                reviewDetailsBinding.etProofOfIncome.setText("Yes");
            }else{
                reviewDetailsBinding.etProofOfIncome.setText("No");
            }
        }
    }

    private void getSharedPrefData() {

        //flow for new application
        if (getSerializableFromPref(Config.GET_CONSUMER_RESPONSE, GetConsumerAccountDetailsResponse.class) == null) {
            IS_RESUMED = false;
            registerVerifyOtpResponse = (RegisterVerifyOtpResponse) getSerializableFromPref(Config.REG_OTP_RESPONSE, RegisterVerifyOtpResponse.class);
        }
        //flow for drafted application
        else {
            IS_RESUMED = true;
            getConsumerAccountDetailsResponse = (GetConsumerAccountDetailsResponse) getSerializableFromPref(Config.GET_CONSUMER_RESPONSE, GetConsumerAccountDetailsResponse.class);
        }

    }

    private void setClicks() {
        reviewDetailsBinding.accountDetailsEdit.setOnClickListener(this);
        reviewDetailsBinding.yourDetailsEdit.setOnClickListener(this);
        reviewDetailsBinding.addressEdit.setOnClickListener(this);
        reviewDetailsBinding.transactionDetailsEdit.setOnClickListener(this);
        reviewDetailsBinding.btnContainer.btnNext.setOnClickListener(this);
        reviewDetailsBinding.btnContainer.btBack.setOnClickListener(this);
        reviewDetailsBinding.termsAndConditions.cbTermsAndConditions.setOnCheckedChangeListener(this);
        reviewDetailsBinding.termsAndConditions.cbDeclaration.setOnCheckedChangeListener(this);
    }

    private void setLayout() {
        reviewDetailsBinding.screenHeader.stepsHeading1.setText("Review");
        reviewDetailsBinding.screenHeader.stepsHeading2.setText("Details");
    }

    private void setBinding() {
        reviewDetailsBinding = ReviewDetailsBinding.inflate(getLayoutInflater());
        setContentView(reviewDetailsBinding.getRoot());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.account_details_edit:
                enableAccountDetailsEditing();
                break;

            case R.id.your_details_edit:
                enableYourDetailsEditing();
                break;
            case R.id.address_edit:
                enableAddressDetailsEditing();
                break;
            case R.id.transaction_details_edit:
                enableTransactionDetailsEditing();
                break;
            case R.id.btn_next:
                submitApplication();
                break;
        }
    }

    private void submitApplication() {
        if (isValidateReviewDetails()){
            showApplicationSubmissionScreen();
        }
    }

    private void showApplicationSubmissionScreen() {
        reviewDetailsBinding.llReviewDetails.setVisibility(View.GONE);
        reviewDetailsBinding.llSubmissionScreen.setVisibility(View.VISIBLE);
    }

    private boolean isValidateReviewDetails() {
        if (    isEmpty(reviewDetailsBinding.etAccountPurpose) ||
                isEmpty(reviewDetailsBinding.etProofOfIncome) ||
                isEmpty(reviewDetailsBinding.etTitle) ||
                isEmpty(reviewDetailsBinding.etFullName) ||
                isEmpty(reviewDetailsBinding.etGender) ||
                isEmpty(reviewDetailsBinding.etPlaceOfBirth) ||
                isEmpty(reviewDetailsBinding.etFatherHusbandName) ||
                isEmpty(reviewDetailsBinding.etProfession) ||
                isEmpty(reviewDetailsBinding.etOccupation) ||
                isEmpty(reviewDetailsBinding.etExpectedSalary) ||
                isEmpty(reviewDetailsBinding.etAddress) ||
                isEmpty(reviewDetailsBinding.etNearestLandmark) ||
                isEmpty(reviewDetailsBinding.etTehsilTown) ||
                isEmpty(reviewDetailsBinding.etCity) ||
                isEmpty(reviewDetailsBinding.etCountry) ||
                isEmpty(reviewDetailsBinding.etEmailAddress) ||
                isEmpty(reviewDetailsBinding.etDebitCard) ||
                isEmpty(reviewDetailsBinding.etChequeBok) ||
                isEmpty(reviewDetailsBinding.etTransactionAlerts)
        ){
            showAlert(Config.errorType,"Some Field/s is Empty !!!");
            return false;
        }else if (!termsAndConditions){
            showAlert(Config.errorType,"Please Check Terms And Conditions !!!");
            return false;
        }else if (!declaration){
            showAlert(Config.errorType,"Please Check Declaration !!!");
            return false;
        }else{
            return true;
        }
    }

    private void enableTransactionDetailsEditing() {
        reviewDetailsBinding.tvDebitCard.setVisibility(View.GONE);
        reviewDetailsBinding.etDebitCard.setVisibility(View.VISIBLE);

        reviewDetailsBinding.tvChequeBok.setVisibility(View.GONE);
        reviewDetailsBinding.etChequeBok.setVisibility(View.VISIBLE);

        reviewDetailsBinding.tvTransactionAlerts.setVisibility(View.GONE);
        reviewDetailsBinding.etTransactionAlerts.setVisibility(View.VISIBLE);
    }

    private void enableAddressDetailsEditing() {
        reviewDetailsBinding.tvAddress.setVisibility(View.GONE);
        reviewDetailsBinding.etAddress.setVisibility(View.VISIBLE);

        reviewDetailsBinding.tvNearestLandmark.setVisibility(View.GONE);
        reviewDetailsBinding.etNearestLandmark.setVisibility(View.VISIBLE);

        reviewDetailsBinding.tvTehsilTown.setVisibility(View.GONE);
        reviewDetailsBinding.etTehsilTown.setVisibility(View.VISIBLE);

        reviewDetailsBinding.tvCity.setVisibility(View.GONE);
        reviewDetailsBinding.etCity.setVisibility(View.VISIBLE);

        reviewDetailsBinding.tvCountry.setVisibility(View.GONE);
        reviewDetailsBinding.etCountry.setVisibility(View.VISIBLE);

        reviewDetailsBinding.tvEmailAddress.setVisibility(View.GONE);
        reviewDetailsBinding.etEmailAddress.setVisibility(View.VISIBLE);
    }

    private void enableYourDetailsEditing() {
        reviewDetailsBinding.tvTitle.setVisibility(View.GONE);
        reviewDetailsBinding.etTitle.setVisibility(View.VISIBLE);

        reviewDetailsBinding.tvFullName.setVisibility(View.GONE);
        reviewDetailsBinding.etFullName.setVisibility(View.VISIBLE);

        reviewDetailsBinding.tvGender.setVisibility(View.GONE);
        reviewDetailsBinding.etGender.setVisibility(View.VISIBLE);

        reviewDetailsBinding.tvPlaceOfBirth.setVisibility(View.GONE);
        reviewDetailsBinding.etPlaceOfBirth.setVisibility(View.VISIBLE);

        reviewDetailsBinding.tvFatherHusbandName.setVisibility(View.GONE);
        reviewDetailsBinding.etFatherHusbandName.setVisibility(View.VISIBLE);

        reviewDetailsBinding.tvProfession.setVisibility(View.GONE);
        reviewDetailsBinding.etProfession.setVisibility(View.VISIBLE);

        reviewDetailsBinding.tvOccupation.setVisibility(View.GONE);
        reviewDetailsBinding.etOccupation.setVisibility(View.VISIBLE);

        reviewDetailsBinding.tvExpectedSalary.setVisibility(View.GONE);
        reviewDetailsBinding.etExpectedSalary.setVisibility(View.VISIBLE);
    }

    private void enableAccountDetailsEditing() {
        reviewDetailsBinding.tvAccountPurpose.setVisibility(View.GONE);
        reviewDetailsBinding.etAccountPurpose.setVisibility(View.VISIBLE);

        reviewDetailsBinding.tvProofOfIncome.setVisibility(View.GONE);
        reviewDetailsBinding.etProofOfIncome.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()){
            case R.id.cb_terms_and_conditions:
                if (isChecked){
                    termsAndConditions = true;
                }else{
                    termsAndConditions = false;
                }
                break;
            case R.id.cb_declaration:
                if (isChecked){
                    declaration = true;
                }else{
                    declaration = false;
                }
                break;
        }
    }
}
