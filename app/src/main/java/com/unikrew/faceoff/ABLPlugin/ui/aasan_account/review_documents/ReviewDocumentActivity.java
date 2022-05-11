package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.review_documents;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ReviewDetailsBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponseAccountInformation;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponseConsumerList;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AccountInformationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.ConsumerListItemResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.Config;

import java.util.List;

public class ReviewDocumentActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ReviewDetailsBinding reviewDetailsBinding;
    private boolean termsAndConditions = false;
    private boolean declaration = false;
    private Boolean IS_RESUMED;

    private GetConsumerAccountDetailsPostParams accountDetailsPostParams;
    private ReviewDocumentViewModel reviewDocumentViewModel;
    private RegisterVerifyOtpResponse registerVerifyOtpResponse;
    private GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setLayout();
        setClicks();
        getSharedPrefData();
        setViewModel();
        getAccountDetails();
        setLogoLayout(reviewDetailsBinding.logoToolbar.tvDate);
        observe();
    }

    private void observe() {
        reviewDocumentViewModel.accDetailsSuccess.observe(this, new Observer<GetConsumerAccountDetailsResponse>() {
            @Override
            public void onChanged(GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse) {
                dismissLoading();
                setResponse(getConsumerAccountDetailsResponse);
            }
        });

        reviewDocumentViewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                dismissLoading();
                showAlert(Config.errorType,errMsg);
            }
        });
    }

    private void getAccountDetails() {
        setAccountDetailsPostParams();
        reviewDocumentViewModel.getAccountDetails(accountDetailsPostParams,getStringFromPref(Config.ACCESS_TOKEN));
        showLoading();
    }

    private void setAccountDetailsPostParams() {
        if (IS_RESUMED){
            accountDetailsPostParams.getData().setCustomerTypeId(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getCustomerTypeId());
            accountDetailsPostParams.getData().setRdaCustomerAccInfoId(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getAccountInformation().getRdaCustomerAccInfoId());
            accountDetailsPostParams.getData().setRdaCustomerProfileId(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getRdaCustomerProfileId());
        }else{
            accountDetailsPostParams.getData().setCustomerTypeId(registerVerifyOtpResponse.getData().getConsumerList().get(0).getCustomerTypeId());
            accountDetailsPostParams.getData().setRdaCustomerAccInfoId(registerVerifyOtpResponse.getData().getConsumerList().get(0).getAccountInformation().getRdaCustomerAccInfoId());
            accountDetailsPostParams.getData().setRdaCustomerProfileId(registerVerifyOtpResponse.getData().getConsumerList().get(0).getRdaCustomerProfileId());
        }
    }

    private void setViewModel() {
        reviewDocumentViewModel = new ViewModelProvider(this).get(ReviewDocumentViewModel.class);
        accountDetailsPostParams = new GetConsumerAccountDetailsPostParams();
    }

    private void setResponse(GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse) {
        setSummary(getConsumerAccountDetailsResponse);
        setAccountDetails(getConsumerAccountDetailsResponse);
        setYourDetails(getConsumerAccountDetailsResponse);
        setCurrentMailingAddress(getConsumerAccountDetailsResponse);
        setTransactionDetails(getConsumerAccountDetailsResponse);
        setImages(getConsumerAccountDetailsResponse);
    }

    private void setImages(GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse) {
        for (int i = 0; i < getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getAttachments().size() ; i++){
            if(getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getAttachments().get(i).getAttachmentTypeId() == Config.CNIC_FRONT_TYPE_ID){
                Glide.with(this).load(getConsumerAccountDetailsResponse.
                        getData().
                        getConsumerList().get(0).
                        getAttachments().get(i).
                        getPath()).
                        placeholder(R.drawable.no_image).
                        into(reviewDetailsBinding.imgCnicFront);
            }else if (getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getAttachments().get(i).getAttachmentTypeId() == Config.CNIC_BACK_TYPE_ID){
                Glide.with(this).load(getConsumerAccountDetailsResponse.
                        getData().
                        getConsumerList().get(0).
                        getAttachments().get(i).
                        getPath()).
                        placeholder(R.drawable.no_image).
                        into(reviewDetailsBinding.imgCnicBack);
            }else if (getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getAttachments().get(i).getAttachmentTypeId() == Config.SIGNATURE_TYPE_ID){
                Glide.with(this).load(getConsumerAccountDetailsResponse.
                        getData().
                        getConsumerList().get(0).
                        getAttachments().get(i).
                        getPath()).
                        placeholder(R.drawable.no_image).
                        into(reviewDetailsBinding.imgSignature);
            }
        }
    }

    private void setTransactionDetails(GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse) {

        GetConsumerAccountDetailsResponseConsumerList consumerDetails = getConsumerAccountDetailsResponse.getData().getConsumerList().get(0);

        if (consumerDetails.getAccountInformation().getAtmType() != null){
            reviewDetailsBinding.tvDebitCard.setText(consumerDetails.getAccountInformation().getAtmType().toString());
            reviewDetailsBinding.etDebitCard.setText(consumerDetails.getAccountInformation().getAtmType().toString());
        }else{
            reviewDetailsBinding.tvDebitCard.setText("---");
            reviewDetailsBinding.etDebitCard.setText("---");
        }

        if (consumerDetails.getAccountInformation().getChequeBookReqInd() != null){
            reviewDetailsBinding.tvChequeBok.setText(consumerDetails.getAccountInformation().getChequeBookReqInd().toString());
            reviewDetailsBinding.etChequeBok.setText(consumerDetails.getAccountInformation().getChequeBookReqInd().toString());
        }else{
            reviewDetailsBinding.tvChequeBok.setText("---");
            reviewDetailsBinding.etChequeBok.setText("---");
        }

        if (consumerDetails.getAccountInformation().getTransactionalAlert() != null){
            reviewDetailsBinding.tvTransactionAlerts.setText(consumerDetails.getAccountInformation().getTransactionalAlert().toString());
            reviewDetailsBinding.etTransactionAlerts.setText(consumerDetails.getAccountInformation().getTransactionalAlert().toString());
        }else{
            reviewDetailsBinding.tvTransactionAlerts.setText("---");
            reviewDetailsBinding.etTransactionAlerts.setText("---");
        }
    }

    private void setSummary(GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse) {

        GetConsumerAccountDetailsResponseConsumerList consumerDetails = getConsumerAccountDetailsResponse.getData().consumerList.get(0);
        if (consumerDetails.getAccountInformation().getBankingMode()!=null){
            reviewDetailsBinding.tvBankingMode.setText(consumerDetails.getAccountInformation().getBankingMode());
        }else{
            reviewDetailsBinding.tvBankingMode.setText("---");
        }

        if (consumerDetails.getAccountInformation().getCustomerBranch()!=null){
            reviewDetailsBinding.branch.setText(consumerDetails.getAccountInformation().getCustomerBranch());
        }else{
            reviewDetailsBinding.branch.setText("---");
        }

        if (consumerDetails.getAccountInformation().getCustomerAccountType()!=null){
            reviewDetailsBinding.accountType.setText(consumerDetails.getAccountInformation().getCustomerAccountType().toString());
        }else{
            reviewDetailsBinding.accountType.setText("---");
        }

        if (consumerDetails.getAccountInformation().getCurrencyType()!=null){
            reviewDetailsBinding.currency.setText(consumerDetails.getAccountInformation().getCurrencyType().toString());
        }else{
            reviewDetailsBinding.currency.setText("---");
        }

        if (consumerDetails.getAccountInformation().getAccountVariant()!=null){
            reviewDetailsBinding.account.setText(consumerDetails.getAccountInformation().getAccountVariant().toString());
        }else{
            reviewDetailsBinding.account.setText("---");
        }

        if (consumerDetails.getAccountInformation().getNatureOfAccount()!=null){
            reviewDetailsBinding.accountNature.setText(consumerDetails.getAccountInformation().getNatureOfAccount().toString());
        }else{
            reviewDetailsBinding.accountNature.setText("---");
        }

        if (consumerDetails.getMobileNo()!=null){
            reviewDetailsBinding.mobileNumber.setText(consumerDetails.getMobileNo());
        }else{
            reviewDetailsBinding.mobileNumber.setText("---");
        }

        if (consumerDetails.getIdNumber()!=null){
            reviewDetailsBinding.cnic.setText(consumerDetails.getIdNumber());
        }else{
            reviewDetailsBinding.cnic.setText("---");
        }

        if (consumerDetails.getDateOfBirth()!=null){
            reviewDetailsBinding.dob.setText(consumerDetails.getDateOfBirth().toString());
        }else{
            reviewDetailsBinding.dob.setText("---");
        }

        if (consumerDetails.getDateOfIssue()!=null){
            reviewDetailsBinding.cnicIssueDate.setText(consumerDetails.getDateOfIssue().toString());
        }else{
            reviewDetailsBinding.cnicIssueDate.setText("---");
        }

        if (consumerDetails.getExpiryDate()!=null){
            reviewDetailsBinding.cnicExpiryDate.setText(consumerDetails.getExpiryDate().toString());
        }else{
            reviewDetailsBinding.cnicExpiryDate.setText("---");
        }

        if (consumerDetails.getMotherMaidenName()!=null){
            reviewDetailsBinding.motherName.setText(consumerDetails.getMotherMaidenName());
        }else{
            reviewDetailsBinding.motherName.setText("---");
        }

        if (consumerDetails.getAddresses().get(0).getCountry()!=null){
            reviewDetailsBinding.country.setText(consumerDetails.getAddresses().get(0).getCountry().toString());
        }else{
            reviewDetailsBinding.country.setText("---");
        }
    }

    private void setCurrentMailingAddress(GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse) {

        GetConsumerAccountDetailsResponseConsumerList consumerDetails = getConsumerAccountDetailsResponse.getData().consumerList.get(0);
        if (consumerDetails.getAddresses().get(0).getCustomerAddress()!=null){
            reviewDetailsBinding.tvAddress.setText(consumerDetails.getAddresses().get(0).getCustomerAddress());
            reviewDetailsBinding.etAddress.setText(consumerDetails.getAddresses().get(0).getCustomerAddress());
        }else{
            reviewDetailsBinding.tvAddress.setText("---");
            reviewDetailsBinding.etAddress.setText("---");
        }

        if (consumerDetails.getAddresses().get(0).getNearestLandMark()!=null){
            reviewDetailsBinding.tvNearestLandmark.setText(consumerDetails.getAddresses().get(0).getNearestLandMark());
            reviewDetailsBinding.etNearestLandmark.setText(consumerDetails.getAddresses().get(0).getNearestLandMark());
        }else{
            reviewDetailsBinding.tvNearestLandmark.setText("---");
            reviewDetailsBinding.etNearestLandmark.setText("---");
        }

        if (consumerDetails.getAddresses().get(0).getCustomerTown()!=null){
            reviewDetailsBinding.tvTehsilTown.setText(consumerDetails.getAddresses().get(0).getCustomerTown());
            reviewDetailsBinding.etTehsilTown.setText(consumerDetails.getAddresses().get(0).getCustomerTown());
        }else{
            reviewDetailsBinding.tvTehsilTown.setText("---");
            reviewDetailsBinding.etTehsilTown.setText("---");
        }

        if (consumerDetails.getAddresses().get(0).getCity()!=null){
            reviewDetailsBinding.tvCity.setText(consumerDetails.getAddresses().get(0).getCity());
            reviewDetailsBinding.etCity.setText(consumerDetails.getAddresses().get(0).getCity());
        }else{
            reviewDetailsBinding.etCity.setText("----");
            reviewDetailsBinding.tvCity.setText("----");
        }

        if (consumerDetails.getAddresses().get(0).getCountry()!=null){
            reviewDetailsBinding.tvCountry.setText(consumerDetails.getAddresses().get(0).getCountry());
            reviewDetailsBinding.etCountry.setText(consumerDetails.getAddresses().get(0).getCountry());
        }else{
            reviewDetailsBinding.tvCountry.setText("---");
            reviewDetailsBinding.etCountry.setText("---");
        }

        if (consumerDetails.getEmailAddress()!=null){
            reviewDetailsBinding.tvEmailAddress.setText(consumerDetails.getEmailAddress());
            reviewDetailsBinding.etEmailAddress.setText(consumerDetails.getEmailAddress());
        }else{
            reviewDetailsBinding.tvEmailAddress.setText("---");
            reviewDetailsBinding.etEmailAddress.setText("---");
        }
    }

    private void setYourDetails(GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse) {

        GetConsumerAccountDetailsResponseConsumerList consumerDetails = getConsumerAccountDetailsResponse.getData().getConsumerList().get(0);

        if (consumerDetails.getCustomerTitle()!=null){
            reviewDetailsBinding.tvTitle.setText(consumerDetails.getCustomerTitle().toString());
            reviewDetailsBinding.etTitle.setText(consumerDetails.getCustomerTitle().toString());
        }else{
            reviewDetailsBinding.tvTitle.setText("---");
            reviewDetailsBinding.etTitle.setText("---");
        }

        if (consumerDetails.getFullName()!=null){
            reviewDetailsBinding.tvFullName.setText(consumerDetails.getFullName());
            reviewDetailsBinding.etFullName.setText(consumerDetails.getFullName());
        }else{
            reviewDetailsBinding.tvFullName.setText("---");
            reviewDetailsBinding.etFullName.setText("---");
        }

        if (consumerDetails.getGender()!=null){
            reviewDetailsBinding.tvGender.setText(consumerDetails.getGender().toString());
            reviewDetailsBinding.etGender.setText(consumerDetails.getGender().toString());
        }else{
            reviewDetailsBinding.tvGender.setText("---");
            reviewDetailsBinding.etGender.setText("---");
        }

        if (consumerDetails.getCityOfBirth()!=null){
            reviewDetailsBinding.tvPlaceOfBirth.setText(consumerDetails.getCityOfBirth());
            reviewDetailsBinding.etPlaceOfBirth.setText(consumerDetails.getCityOfBirth());
        }else{
            reviewDetailsBinding.tvPlaceOfBirth.setText("---");
            reviewDetailsBinding.etPlaceOfBirth.setText("---");
        }

        if (consumerDetails.getFatherHusbandName()!=null){
            reviewDetailsBinding.tvFatherHusbandName.setText(consumerDetails.getFatherHusbandName());
            reviewDetailsBinding.etFatherHusbandName.setText(consumerDetails.getFatherHusbandName());
        }else{
            reviewDetailsBinding.tvFatherHusbandName.setText("---");
            reviewDetailsBinding.etFatherHusbandName.setText("---");
        }

        if (consumerDetails.getProfession()!=null){
            reviewDetailsBinding.tvProfession.setText(consumerDetails.getProfession().toString());
            reviewDetailsBinding.etProfession.setText(consumerDetails.getProfession().toString());
        }else{
            reviewDetailsBinding.tvProfession.setText("---");
            reviewDetailsBinding.etProfession.setText("---");
        }

        if (consumerDetails.getOccupation()!=null){
            reviewDetailsBinding.tvOccupation.setText(consumerDetails.getOccupation().toString());
            reviewDetailsBinding.etOccupation.setText(consumerDetails.getOccupation().toString());
        }else{
            reviewDetailsBinding.tvOccupation.setText("---");
            reviewDetailsBinding.etOccupation.setText("---");
        }

        if (consumerDetails.getAccountInformation().getAverageMonthlySalary()!=null){
            reviewDetailsBinding.tvExpectedSalary.setText(consumerDetails.getAccountInformation().getAverageMonthlySalary().toString());
            reviewDetailsBinding.etExpectedSalary.setText(consumerDetails.getAccountInformation().getAverageMonthlySalary().toString());
        }else{
            reviewDetailsBinding.tvExpectedSalary.setText("---");
            reviewDetailsBinding.etExpectedSalary.setText("---");
        }
    }

    private void setAccountDetails(GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse) {

        GetConsumerAccountDetailsResponseAccountInformation accountInformation = getConsumerAccountDetailsResponse.getData().getConsumerList().get(0).getAccountInformation();

        if (accountInformation.getPurposeOfAccount()!=null){
            reviewDetailsBinding.tvAccountPurpose.setText(accountInformation.getPurposeOfAccount().toString());
            reviewDetailsBinding.etAccountPurpose.setText(accountInformation.getPurposeOfAccount().toString());
        }else{
            reviewDetailsBinding.tvAccountPurpose.setText("---");
            reviewDetailsBinding.etAccountPurpose.setText("---");
        }

        if (accountInformation.getProofOfIncomeInd()!=null){
            reviewDetailsBinding.tvProofOfIncome.setText(accountInformation.getProofOfIncomeInd().toString());
            reviewDetailsBinding.etProofOfIncome.setText(accountInformation.getProofOfIncomeInd().toString());
        }else{
            reviewDetailsBinding.tvProofOfIncome.setText("---");
            reviewDetailsBinding.etProofOfIncome.setText("---");
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
