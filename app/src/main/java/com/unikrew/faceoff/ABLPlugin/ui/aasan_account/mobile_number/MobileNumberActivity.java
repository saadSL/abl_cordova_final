package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.mobile_number;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.MobileNumberAvailabilityBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycPostData;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.ConsumerListItemResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.ConsumerListItemVerifyOtp;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtp;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpPostAttachment;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpResponse;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.additional_applicant.AdditionalApplicantActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.otp_phase2.OtpVerification;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details.PersonalDetailsOneActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details.PersonalDetailsViewModel;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account.SelectBankingModeViewModel;
import com.unikrew.faceoff.Config;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MobileNumberActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    /* variables  */
    private MobileNumberViewModel viewModel;
    private SelectBankingModeViewModel selectBankingModeViewModel;
    private PersonalDetailsViewModel personalDetailsViewModel;
    private ViewAppsGenerateOtpPostParams postParams;

    private int isPortedMobileNetwork = 0;
    private Boolean generateOtp = false;

    private String cnicFrontPic = "";
    private String cnicBackPic = "";
    private Boolean frontPic = false;
    private Boolean backPic = false;
    private Bitmap image;
    private Boolean alreadyExist = false;
    private ArrayList<ViewAppsGenerateOtpPostAttachment> attachments = new ArrayList<ViewAppsGenerateOtpPostAttachment>();
    private MobileNumberAvailabilityBinding mobileNumberAvailabilityBinding;
    private boolean returnedFromNextScreen = false;
    private boolean IS_JOINT = false;
    private RegisterVerifyOtpResponse registerVerifyOtpResponse;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setLayout();
        getIntentData();
        setListeners();
        setViewModel();
        observeData();
    }

    private void getIntentData() {
        //for joint account
        if (getIntent().hasExtra("isJoint")) {
            IS_JOINT = true;
        }

    }


    private void postBankingModeToNetwork() {
        showLoading();
        selectBankingModeViewModel.postBankingMethod(getBankingModeParams());
    }

    private RegisterVerifyOtp getBankingModeParams() {
        RegisterVerifyOtp registerVerifyOtp = new RegisterVerifyOtp();
        registerVerifyOtpResponse= (RegisterVerifyOtpResponse) getSerializableFromPref(Config.REG_OTP_RESPONSE, RegisterVerifyOtpResponse.class);
        List<ConsumerListItemResponse> consumerList  = registerVerifyOtpResponse.getData().getConsumerList();;
        bindOldGenericData(consumerList,registerVerifyOtp);
        bindNewGenericData(consumerList,registerVerifyOtp);
        registerVerifyOtp.getData().setNoOfJointApplicatns(getIntFromPref(Config.NO_OF_JOINT_APPLICANTS));
        return registerVerifyOtp;

    }

    private void bindNewGenericData(List<ConsumerListItemResponse> consumerList, RegisterVerifyOtp registerVerifyOtp) {
        //adding new item in the list for api call
        ConsumerListItemVerifyOtp  consumerListItemVerifyOtp = new ConsumerListItemVerifyOtp();
        consumerListItemVerifyOtp.setPrimary(false);
        consumerListItemVerifyOtp.setDateOfBirth(getStringFromPref(Config.DATE_OF_BIRTH));
        consumerListItemVerifyOtp.setDateOfIssue(getStringFromPref(Config.DATE_OF_ISSUE));
        consumerListItemVerifyOtp.setBankingModeId(consumerList.get(0).getAccountInformation().getBankingModeId());
        consumerListItemVerifyOtp.setCustomerBranch(consumerList.get(0).getCustomerBranch());
        consumerListItemVerifyOtp.setCustomerTypeId(Config.CUSTOMER_TYPE_ID);
        consumerListItemVerifyOtp.setMobileNo(getStringFromPref(Config.MOBILE_NUMBER));
        consumerListItemVerifyOtp.setIdNumber(getStringFromPref(Config.CNIC_NUMBER));
        consumerListItemVerifyOtp.setRdaCustomerAccInfoId(consumerList.get(0).getAccountInformation().getRdaCustomerAccInfoId());
        registerVerifyOtp.getData().getConsumerList().add(consumerListItemVerifyOtp);
    }

    private void bindOldGenericData(List<ConsumerListItemResponse> consumerList, RegisterVerifyOtp registerVerifyOtp) {
        //adding all the previously saved items in the list for api call
        for (int i = 0; i < consumerList.size(); i++) {
            ConsumerListItemVerifyOtp  consumerListItemVerifyOtp = new ConsumerListItemVerifyOtp();
            consumerListItemVerifyOtp.setPrimary(consumerList.get(i).isPrimary());
            consumerListItemVerifyOtp.setDateOfBirth(consumerList.get(i).getDateOfBirth());
            consumerListItemVerifyOtp.setDateOfIssue(consumerList.get(i).getDateOfIssue());
            consumerListItemVerifyOtp.setBankingModeId(consumerList.get(i).getAccountInformation().getBankingModeId());
            consumerListItemVerifyOtp.setCustomerBranch(consumerList.get(i).getCustomerBranch());
            consumerListItemVerifyOtp.setCustomerTypeId(Config.CUSTOMER_TYPE_ID);
            consumerListItemVerifyOtp.setMobileNo(consumerList.get(i).getMobileNo());
            consumerListItemVerifyOtp.setIdNumber(consumerList.get(i).getIdNumber());
            consumerListItemVerifyOtp.setRdaCustomerAccInfoId(consumerList.get(i).getAccountInformation().getRdaCustomerAccInfoId());
            registerVerifyOtp.getData().getConsumerList().add(consumerListItemVerifyOtp);
        }
    }

    private void setLayout() {
        mobileNumberAvailabilityBinding.screenHeader.stepsHeading1.setText("Let's");
        mobileNumberAvailabilityBinding.screenHeader.stepsHeading2.setText("Get Started");
        mobileNumberAvailabilityBinding.btnContainer.btBack.setVisibility(View.GONE);
        if (returnedFromNextScreen) {
            mobileNumberAvailabilityBinding.llCnic.setVisibility(View.GONE);
            mobileNumberAvailabilityBinding.llCnicUploadFront.setVisibility(View.GONE);
            mobileNumberAvailabilityBinding.llCnicUploadBack.setVisibility(View.GONE);
            mobileNumberAvailabilityBinding.llCnicFront.setVisibility(View.GONE);
            mobileNumberAvailabilityBinding.llCnicBack.setVisibility(View.GONE);
            mobileNumberAvailabilityBinding.imgCnicFrontSmall.setVisibility(View.GONE);
            mobileNumberAvailabilityBinding.imgCnicFrontSmall.setImageBitmap(null);
            mobileNumberAvailabilityBinding.imgCnicBackSmall.setVisibility(View.GONE);
            mobileNumberAvailabilityBinding.imgCnicBackSmall.setImageBitmap(null);
            generateOtp = false;
            returnedFromNextScreen = false;
        }
        setLogoLayout(mobileNumberAvailabilityBinding.logoToolbar.tvDate);
    }


    private void setBinding() {
        mobileNumberAvailabilityBinding = MobileNumberAvailabilityBinding.inflate(getLayoutInflater());
        setContentView(mobileNumberAvailabilityBinding.getRoot());
    }


    private void observeData() {
        viewModel.generateOtpResponseLiveData.observe(this, new Observer<ViewAppsGenerateOtpResponse>() {
            @Override
            public void onChanged(ViewAppsGenerateOtpResponse viewAppsGenerateOtpResponse) {
                alreadyExist = viewAppsGenerateOtpResponse.getData().isAlreadyExist();
                if (alreadyExist) {
                    if (generateOtp) {
                        goToNext(viewAppsGenerateOtpResponse);
                    } else {
                        showCnic(viewAppsGenerateOtpResponse);
                    }
                } else {
                    if (generateOtp) {
                        goToNext(viewAppsGenerateOtpResponse);
                    } else {
                        showCnicFields();
                    }

                }
                loader.dismiss();
            }
        });

        viewModel.generateOtpErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                loader.dismiss();
            }
        });

        //for joint account
        selectBankingModeViewModel.registerOtpLiveData.observe(this, new Observer<RegisterVerifyOtpResponse>() {
            @Override
            public void onChanged(RegisterVerifyOtpResponse registerVerifyOtpResponse) {
                dismissLoading();
                saveSerializableInPref(Config.REG_OTP_RESPONSE, registerVerifyOtpResponse);
                saveSerializableInPref(Config.GET_CONSUMER_RESPONSE, null);
                postKycToNetwork();
            }
        });

        selectBankingModeViewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                dismissLoading();
            }
        });

        //for joint account
        personalDetailsViewModel.saveKycResponseMutableLiveData.observe(this, new Observer<SaveKycResponse>() {
            @Override
            public void onChanged(SaveKycResponse saveKycResponse) {
                dismissLoading();
                goToPersonalDetailsOne();
            }
        });

        personalDetailsViewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                dismissLoading();
                showAlert(Config.errorType, errMsg);
            }
        });

    }

    private void goToPersonalDetailsOne() {
        openActivity(PersonalDetailsOneActivity.class);
    }

    private void postKycToNetwork() {
        showLoading();
        personalDetailsViewModel.saveKyc(getKycParams(), getStringFromPref(Config.ACCESS_TOKEN));
    }

    private SaveKycPostParams getKycParams() {
        SaveKycPostParams saveKycPostParams = new SaveKycPostParams();
        SaveKycPostData saveKycPostData;

        List<ConsumerListItemResponse> consumerList =registerVerifyOtpResponse.getData().getConsumerList();

        for (int i = 0; i < consumerList.size(); i++) {
            saveKycPostData = new SaveKycPostData();
            if (i == consumerList.size() - 1 && AdditionalApplicantActivity.SELECTED_RELATION_CODE != 0) {
                saveKycPostData.setRelationCode1(AdditionalApplicantActivity.SELECTED_RELATION_CODE);
            }else{
                saveKycPostData.setRelationCode1(null);
            }
            saveKycPostData.setRdaCustomerAccInfoId(consumerList.get(i).getAccountInformation().getRdaCustomerAccInfoId());
            saveKycPostData.setRdaCustomerProfileId(consumerList.get(i).getRdaCustomerProfileId());
            saveKycPostData.setAverageMonthlySalary(consumerList.get(i).getAccountInformation().getAverageMonthlySalary());
            saveKycPostParams.getData().add(saveKycPostData);
        }
        return saveKycPostParams;
    }

    private void goToNext(ViewAppsGenerateOtpResponse viewAppsGenerateOtpResponse) {
        returnedFromNextScreen = true;
        saveDates(viewAppsGenerateOtpResponse);
        if (IS_JOINT) {
            postBankingModeToNetwork();
        } else {
            openOtpVerificationActivity(viewAppsGenerateOtpResponse);
        }
    }

    private void saveDates(ViewAppsGenerateOtpResponse viewAppsGenerateOtpResponse) {
        saveStringInPref(Config.DATE_OF_BIRTH, viewAppsGenerateOtpResponse.getData().getDateOfBirth());
        saveStringInPref(Config.DATE_OF_ISSUE, viewAppsGenerateOtpResponse.getData().getDateOfIssue());
        saveStringInPref(Config.CNIC_NUMBER, viewAppsGenerateOtpResponse.getData().getIdNumber());
        saveStringInPref(Config.MOBILE_NUMBER, viewAppsGenerateOtpResponse.getData().getMobileNo());
    }

    private void openOtpVerificationActivity(ViewAppsGenerateOtpResponse response) {
        Intent intent = new Intent(this, OtpVerification.class);
        intent.putExtra(Config.RESPONSE, response);
        startActivity(intent);
    }

    /* The method below will work when mobile number is not registered with cnic. */
    private void showCnicFields() {
        mobileNumberAvailabilityBinding.llCnic.setVisibility(View.GONE);
        mobileNumberAvailabilityBinding.llCnicUploadFront.setVisibility(View.VISIBLE);
        mobileNumberAvailabilityBinding.llCnicUploadBack.setVisibility(View.VISIBLE);
        generateOtp = true;
    }

    /* The method below will work when mobile number is already registered with cnic. */
    private void showCnic(ViewAppsGenerateOtpResponse response) {
        mobileNumberAvailabilityBinding.llCnicFront.setVisibility(View.GONE);
        mobileNumberAvailabilityBinding.llCnicBack.setVisibility(View.GONE);
        mobileNumberAvailabilityBinding.llCnic.setVisibility(View.VISIBLE);
        if (response.getData().getIdNumber() != null) {
            if (response.getData().getIdNumber().contains("-")){
                mobileNumberAvailabilityBinding.etCnicNumber.setText(response.getData().getIdNumber().replace("-","").trim());
            }else {
                mobileNumberAvailabilityBinding.etCnicNumber.setText(response.getData().getIdNumber());
            }

        } else {
            mobileNumberAvailabilityBinding.etCnicNumber.setText("");
        }

        generateOtp = true;
    }


    private void setViewModel() {
        selectBankingModeViewModel = new ViewModelProvider(this).get(SelectBankingModeViewModel.class);
        personalDetailsViewModel = new ViewModelProvider(this).get(PersonalDetailsViewModel.class);
        viewModel = new ViewModelProvider(this).get(MobileNumberViewModel.class);
        postParams = new ViewAppsGenerateOtpPostParams();
        Log.d("baseUrl", Config.BASE_URL);
    }


    private void setListeners() {
        /* Mobile Number Availability */
        mobileNumberAvailabilityBinding.portedMobileNetworkSwitch.setOnCheckedChangeListener(this);
        mobileNumberAvailabilityBinding.btnContainer.btnNext.setOnClickListener(this);
        mobileNumberAvailabilityBinding.btnContainer.btBack.setOnClickListener(this);
        mobileNumberAvailabilityBinding.llCnicUploadFront.setOnClickListener(this);
        mobileNumberAvailabilityBinding.llCnicUploadBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                if (!validateMobileNumActivity()) {
                    return;
                } else {
                    viewAppsGenerateOtpPostData();
                }
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.ll_cnic_upload_front:
                frontPic = true;
                backPic = false;
                getCnicFrontImage();
                hideCnicImageResources(
                        mobileNumberAvailabilityBinding.llCnicBack,
                        mobileNumberAvailabilityBinding.imgCnicBackSmall
                );
                break;
            case R.id.ll_cnic_upload_back:
                backPic = true;
                frontPic = false;
                getCnicBackImage();
                hideCnicImageResources(
                        mobileNumberAvailabilityBinding.llCnicFront,
                        mobileNumberAvailabilityBinding.imgCnicFrontSmall
                );
                break;
        }
    }

    private void hideCnicImageResources(LinearLayout layout, ImageView img) {
        layout.setVisibility(View.GONE);
        img.setVisibility(View.VISIBLE);
        img.setImageBitmap(image);
    }

    private void getCnicBackImage() {
//        mobileNumberAvailabilityBinding.imgCnicBackSmall.setVisibility(View.VISIBLE);
//        mobileNumberAvailabilityBinding.llCnicBack.setVisibility(View.VISIBLE);
        getImageFromCamera();
    }

    private void getCnicFrontImage() {
//        mobileNumberAvailabilityBinding.imgCnicFrontSmall.setVisibility(View.VISIBLE);
//        mobileNumberAvailabilityBinding.llCnicFront.setVisibility(View.VISIBLE);
        getImageFromCamera();


    }


    private void getImageFromCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, Config.MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Config.CAMERA_REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Config.MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Config.CAMERA_REQUEST);
            } else {
                showAlert(Config.errorType, "Allow Camera permissions to continue.");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Config.CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            image = null;
            if (image == null && frontPic) {

                image = (Bitmap) data.getExtras().get("data");
                mobileNumberAvailabilityBinding.imgCnicFront.setImageBitmap(image);
                showCnicImageResource(
                        mobileNumberAvailabilityBinding.llCnicFront
                );
                mobileNumberAvailabilityBinding.llCnicFront.setVisibility(View.VISIBLE);
                cnicFrontPic = convertToBase64(image);

            } else if (image == null && backPic) {
                image = (Bitmap) data.getExtras().get("data");
                mobileNumberAvailabilityBinding.imgCnicBack.setImageBitmap(image);

                showCnicImageResource(
                        mobileNumberAvailabilityBinding.llCnicBack);

                cnicBackPic = convertToBase64(image);
            }

        }
    }

    private void showCnicImageResource(LinearLayout layout) {
        layout.setVisibility(View.VISIBLE);
    }

    private String convertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    private void viewAppsGenerateOtpPostData() {
        if (!alreadyExist && generateOtp) {
            setAttachmentObject();
        }
        setPostParams();
        viewModel.viewAppsGenerateOtpPostData(postParams);
        showLoading();
    }

    private void setAttachmentObject() {

        /* For CNIC Front Picture */
        ViewAppsGenerateOtpPostAttachment viewAppsGenerateOtpPostAttachment1 = new ViewAppsGenerateOtpPostAttachment();
        viewAppsGenerateOtpPostAttachment1.setFileName(Config.CNIC_FRONT_FILE_NAME);
        viewAppsGenerateOtpPostAttachment1.setBase64Content(cnicFrontPic);
        viewAppsGenerateOtpPostAttachment1.setAttachmentTypeId(Config.ATTACHMENT_TYPE_ID_FRONT);
        attachments.add(viewAppsGenerateOtpPostAttachment1);

        /* For CNIC Back Picture */
        ViewAppsGenerateOtpPostAttachment viewAppsGenerateOtpPostAttachment2 = new ViewAppsGenerateOtpPostAttachment();
        viewAppsGenerateOtpPostAttachment2.setFileName(Config.CNIC_BACK_FILE_NAME);
        viewAppsGenerateOtpPostAttachment2.setBase64Content(cnicBackPic);
        viewAppsGenerateOtpPostAttachment2.setAttachmentTypeId(Config.ATTACHMENT_TYPE_ID_BACK);
        attachments.add(viewAppsGenerateOtpPostAttachment2);
    }

    private void setPostParams() {

        postParams.getData().setCustomerTypeId(Config.CUSTOMER_TYPE_ID);
        postParams.getData().setMobileNo(mobileNumberAvailabilityBinding.etMobileNum.getText().toString());
        // when we are filling up data for secondary user of a joint account then there is no need of otp
        //hence generateOtp will be false
        if (IS_JOINT) {
            postParams.getData().setGenerateOtp(false);
        } else {
            postParams.getData().setGenerateOtp(generateOtp);
        }


        if (generateOtp) {
            if (alreadyExist) {
                postParams.getData().setIdNumber(mobileNumberAvailabilityBinding.etCnicNumber.getText().toString());
            } else {
                postParams.getData().setAttachments(attachments);
            }

            postParams.getData().setPortedMobileNetwork(isPortedMobileNetwork);
        }

    }

    private Boolean validateMobileNumActivity() {
        if (isEmpty(mobileNumberAvailabilityBinding.etMobileNum)) {

            showAlert(Config.errorType, "Mobile Number Is Empty !!!");
            return false;

        } else if (mobileNumberAvailabilityBinding.etMobileNum.getText().toString().length() < Config.MOBILE_NUMBER_LENGTH) {

            showAlert(Config.errorType, "Mobile Number Length Not Valid !!!");
            return false;

        } else if (alreadyExist && isEmpty(mobileNumberAvailabilityBinding.etCnicNumber)) {

            showAlert(Config.errorType, "CNIC Number Is Empty !!!");
            return false;

        } else if (alreadyExist && mobileNumberAvailabilityBinding.etCnicNumber.getText().toString().length() < Config.CNIC_LENGTH) {

            showAlert(Config.errorType, "CNIC Number Length Not Valid !!!");
            return false;

        } else if (!alreadyExist && generateOtp && cnicFrontPic.equals("")) {

            showAlert(Config.errorType, "CNIC Front Pic Not Uploaded !!!");
            return false;

        } else if (!alreadyExist && generateOtp && cnicBackPic.equals("")) {

            showAlert(Config.errorType, "CNIC Back Pic Not Uploaded !!!");
            return false;

        } else if (!wifiAvailable()) {

            showAlert(Config.errorType, "Network Is Not Available !!!");
            return false;

        } else {
            return true;
        }
    }


    /* Checking Ported network to true or false.  */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.ported_mobile_network_switch:
                if (isChecked) {
                    showMobileInfoDialogue();
                    isPortedMobileNetwork = 1;
                } else {
                    isPortedMobileNetwork = 0;
                }
                break;
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setLayout();
    }
}