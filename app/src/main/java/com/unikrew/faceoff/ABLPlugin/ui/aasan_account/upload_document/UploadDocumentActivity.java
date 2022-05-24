package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.upload_document;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.UploadDocumentsBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.nature_of_account.SaveNatureOfAccountPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.nature_of_account.SaveNatureOfAccountResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_attachment.SaveAttachmentPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_attachment.SaveAttachmentResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.AccountInformationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.ConsumerListItemResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.joint_account_model.joint_applicant.JointApplicant;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.additional_applicant.AdditionalApplicantActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.review_documents.ReviewDocumentActivity;
import com.unikrew.faceoff.Config;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UploadDocumentActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private UploadDocumentsBinding uploadDocumentsBinding;
    private boolean livePic = false;
    private boolean sigPic = false;
    private String livePicString = "";
    private String liveSigString = "";
    private int natureOfAccountId = 0;

    private SaveAttachmentPostParams attachmentPostParams;
    private UploadDocumentViewModel uploadDocumentViewModel;
    private SaveNatureOfAccountPostParams natureOfAccountPostParams;

    private JointApplicant selectedJointApplicant;
    private Boolean savingForPic = true;
    private Boolean savingForSig = false;
    private List<ConsumerListItemResponse> consumerList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setLayout();
        setClicks();
        getSharedPrefData();
        setViewModel();
        observe();
        setLogoLayout(uploadDocumentsBinding.logoToolbar.tvDate);
    }

    private void getSharedPrefData() {
        //flow for new application

        if (getSerializableFromPref(Config.GET_CONSUMER_RESPONSE, GetConsumerAccountDetailsResponse.class) == null) {
            RegisterVerifyOtpResponse registerVerifyOtpResponse = (RegisterVerifyOtpResponse) getSerializableFromPref(Config.REG_OTP_RESPONSE, RegisterVerifyOtpResponse.class);
            consumerList = registerVerifyOtpResponse.getData().getConsumerList();
        }
        //flow for drafted application
        else {
            GetConsumerAccountDetailsResponse getConsumerAccountDetailsResponse = (GetConsumerAccountDetailsResponse) getSerializableFromPref(Config.GET_CONSUMER_RESPONSE, GetConsumerAccountDetailsResponse.class);
            consumerList = getConsumerAccountDetailsResponse.getData().getConsumerList();
        }
        if (consumerList.size() > 1) {
            uploadDocumentsBinding.liNature.setVisibility(View.GONE);
        }
    }

    private void setLayout() {
        uploadDocumentsBinding.steps.screenHeader.stepsHeading1.setText("Upload");
        uploadDocumentsBinding.steps.screenHeader.stepsHeading2.setText("Document");
        uploadDocumentsBinding.steps.step1.setBackground(this.getDrawable(R.color.custom_blue));
        uploadDocumentsBinding.steps.step2.setBackground(this.getDrawable(R.color.custom_blue));
        uploadDocumentsBinding.steps.step3.setBackground(this.getDrawable(R.color.custom_blue));
        uploadDocumentsBinding.steps.step4.setBackground(this.getDrawable(R.color.custom_blue));
    }

    private void observe() {
        uploadDocumentViewModel.saveAttachmentResponseMutableLiveData.observe(this, new Observer<SaveAttachmentResponse>() {
            @Override
            public void onChanged(SaveAttachmentResponse saveAttachmentResponse) {
                if (savingForPic) {
                    savingForPic = false;
                    savingForSig = true;
                    uploadDocuments();
                } else if (savingForSig) {
                    savingForSig = false;
                    uploadDocuments();
                } else {
                    saveNatureOfAccount();
                }
                dismissLoading();
            }
        });

        uploadDocumentViewModel.saveAttachmentErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                dismissLoading();
            }
        });


        uploadDocumentViewModel.saveNatureOfAccountResponseMutableLiveData.observe(this, new Observer<SaveNatureOfAccountResponse>() {
            @Override
            public void onChanged(SaveNatureOfAccountResponse saveNatureOfAccountResponse) {
                dismissLoading();
                moveNext();
            }
        });

        uploadDocumentViewModel.saveNatureOfAccountErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                dismissLoading();
                showAlert(Config.errorType, errMsg);
            }
        });
    }

    private void moveNext() {

        if (getIntFromPref(Config.NO_OF_JOINT_APPLICANTS) == 0 || consumerList.size() - 1 == getIntFromPref(Config.NO_OF_JOINT_APPLICANTS)) {
            openReviewDetailsActivity();
        } else {
            openAdditionalApplicantActivity();
        }
    }

    private void openReviewDetailsActivity() {
        Intent intent = new Intent(this, ReviewDocumentActivity.class);
        startActivity(intent);
    }

    private void saveNatureOfAccount() {
        setNatureOfAccountPostParams();
        uploadDocumentViewModel.saveNatureOfAccount(natureOfAccountPostParams, getStringFromPref(Config.ACCESS_TOKEN));
        showLoading();
    }

    private void setNatureOfAccountPostParams() {
        AccountInformationResponse accountInformation = consumerList.get(consumerList.size() - 1).getAccountInformation();
        natureOfAccountPostParams.getData().setCustomerTypeId(consumerList.get(consumerList.size() - 1).getCustomerTypeId());
        natureOfAccountPostParams.getData().setRdaCustomerAccInfoId(accountInformation.getRdaCustomerAccInfoId());
        natureOfAccountPostParams.getData().setRdaCustomerId(accountInformation.getRdaCustomerId());

        if (selectedJointApplicant == null) {
            natureOfAccountPostParams.getData().setNoOfJointApplicatns(getIntFromPref(Config.NO_OF_JOINT_APPLICANTS));
        } else {
            saveIntInPref(Config.NO_OF_JOINT_APPLICANTS, selectedJointApplicant.getNumber());
            natureOfAccountPostParams.getData().setNoOfJointApplicatns(selectedJointApplicant.getNumber());
        }
        natureOfAccountPostParams.getData().setCustomerTypeId(Config.CUSTOMER_TYPE_ID);
        if (consumerList.size() == 1) {
            natureOfAccountPostParams.getData().setNatureOfAccountId(natureOfAccountId);
        }

    }

    private void setViewModel() {

        uploadDocumentViewModel = new ViewModelProvider(this).get(UploadDocumentViewModel.class);
        attachmentPostParams = new SaveAttachmentPostParams();
        natureOfAccountPostParams = new SaveNatureOfAccountPostParams();
    }

    private void setClicks() {
        uploadDocumentsBinding.llLivePic.setOnClickListener(this);
        uploadDocumentsBinding.llLiveSig.setOnClickListener(this);
        uploadDocumentsBinding.btnContainer.btnNext.setOnClickListener(this);
        uploadDocumentsBinding.btnContainer.btBack.setOnClickListener(this);
        uploadDocumentsBinding.rbSingle.setOnCheckedChangeListener(this);
        uploadDocumentsBinding.rbJoint.setOnCheckedChangeListener(this);
        uploadDocumentsBinding.rbMinor.setOnCheckedChangeListener(this);
    }

    private void setBinding() {
        uploadDocumentsBinding = UploadDocumentsBinding.inflate(getLayoutInflater());
        setContentView(uploadDocumentsBinding.getRoot());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                uploadDocuments();
                break;
            case R.id.bt_back:
                finish();
                break;
            case R.id.ll_live_pic:
                livePic = true;
                sigPic = false;
                getPicFromCamera();
                break;
            case R.id.ll_live_sig:
                livePic = false;
                sigPic = true;
                getPicFromCamera();
                break;
        }
    }

    private void getPicFromCamera() {
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
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Config.CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Config.CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap image = null;
            if (image == null && livePic) {

                image = (Bitmap) data.getExtras().get("data");
                uploadDocumentsBinding.imgLivePic.setImageBitmap(image);

                hideResources(uploadDocumentsBinding.tvLivePic,
                        uploadDocumentsBinding.tvLivePicLink);

                showResources(uploadDocumentsBinding.imgLivePic,
                        uploadDocumentsBinding.scanAgainLivePic);

                livePicString = convertToBase64(image);

            } else if (image == null && sigPic) {
                image = (Bitmap) data.getExtras().get("data");
                uploadDocumentsBinding.imgLiveSig.setImageBitmap(image);

                hideResources(uploadDocumentsBinding.tvLiveSig,
                        uploadDocumentsBinding.tvLiveSigLink);

                showResources(uploadDocumentsBinding.imgLiveSig,
                        uploadDocumentsBinding.scanAgainLiveSig);

                liveSigString = convertToBase64(image);
            }

        }
    }

    private void hideResources(TextView tv, TextView tvLink) {
        tv.setVisibility(View.GONE);
        tvLink.setVisibility(View.GONE);
    }

    private void showResources(ImageView img, TextView scanAgain) {
        img.setVisibility(View.VISIBLE);
        scanAgain.setVisibility(View.VISIBLE);
    }

    private String convertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    private void uploadDocuments() {
        if (livePicString.equals("")) {
            showAlert(Config.errorType, "Please Upload Your Live Picture !!!");
            return;
        } else if (liveSigString.equals("")) {
            showAlert(Config.errorType, "Please Upload Your Signature Picture !!!");
            return;
        } else if (consumerList.size() == 1 && natureOfAccountId == 0) {
            showAlert(Config.errorType, "Please Select Nature Of Account !!!");
            return;
        } else {
            setAttachmentPostParams();
            uploadDocumentViewModel.saveAttachment(
                    attachmentPostParams,
                    getStringFromPref(Config.ACCESS_TOKEN)
            );
            showLoading();
        }
    }

    private void setAttachmentPostParams() {
        if (savingForPic) {
            attachmentPostParams.getData().setAttachmentTypeId(Config.LIVE_PHOTO);
            attachmentPostParams.getData().setFileName("PHOTO");
            attachmentPostParams.getData().setBase64Content(livePicString);

        } else if (savingForSig) {
            attachmentPostParams.getData().setAttachmentTypeId(Config.SIGNATURE_TYPE_ID);
            attachmentPostParams.getData().setFileName("SIGNATURE");
            attachmentPostParams.getData().setBase64Content(liveSigString);
        }
        attachmentPostParams.getData().setMimeType("");
        attachmentPostParams.getData().setPath("");
        attachmentPostParams.getData().setEntityId(consumerList.get(consumerList.size() - 1).getRdaCustomerProfileId());
        attachmentPostParams.getData().setRdaCustomerAccInfoId(consumerList.get(consumerList.size() - 1).getAccountInformation().getRdaCustomerAccInfoId());
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.rb_single:
                if (isChecked) {
                    hideAdditionalApplicantLayout();
                    natureOfAccountId = Config.SINGLE;
                }
                break;
            case R.id.rb_joint:
                if (isChecked) {
                    showAdditionalApplicantLayout();
                    natureOfAccountId = Config.JOINT;
                }
                break;
            case R.id.rb_minor:
                if (isChecked) {
                    hideAdditionalApplicantLayout();
                    natureOfAccountId = Config.MINOR;
                }
                break;
        }
    }

    private void showAdditionalApplicantLayout() {
        uploadDocumentsBinding.llJoint.setVisibility(View.VISIBLE);
        setAdditionalApplicantDropDown();
    }

    private void hideAdditionalApplicantLayout() {
        selectedJointApplicant = null;
        uploadDocumentsBinding.llJoint.setVisibility(View.GONE);
        uploadDocumentsBinding.spAdditionalApplicant.setAdapter(null);
    }

    private void setAdditionalApplicantDropDown() {
        ArrayList<JointApplicant> _additionalApplicant = new ArrayList<>();

        JointApplicant jointApplicant0 = new JointApplicant();
        JointApplicant jointApplicant1 = new JointApplicant();
        JointApplicant jointApplicant2 = new JointApplicant();
        JointApplicant jointApplicant3 = new JointApplicant();
        JointApplicant jointApplicant4 = new JointApplicant();
        JointApplicant jointApplicant5 = new JointApplicant();

        jointApplicant0.setNumber(0);
        jointApplicant0.setDescription("Choose Additional Applicant");

        jointApplicant1.setNumber(1);
        jointApplicant1.setDescription("Additional Applicant");

        jointApplicant2.setNumber(2);
        jointApplicant2.setDescription("Additional Applicant");

        jointApplicant3.setNumber(3);
        jointApplicant3.setDescription("Additional Applicant");

        jointApplicant4.setNumber(4);
        jointApplicant4.setDescription("Additional Applicant");

        jointApplicant5.setNumber(5);
        jointApplicant5.setDescription("Additional Applicant");


        _additionalApplicant.add(jointApplicant0);
        _additionalApplicant.add(jointApplicant1);
        _additionalApplicant.add(jointApplicant2);
        _additionalApplicant.add(jointApplicant3);
        _additionalApplicant.add(jointApplicant4);
        _additionalApplicant.add(jointApplicant5);

        uploadDocumentsBinding.spAdditionalApplicant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    selectedJointApplicant = (JointApplicant) adapterView.getSelectedItem();

                } else {
                    TextView textView = (TextView) view;
                    textView.setTextColor(Color.GRAY);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<JointApplicant> dataAdapter = new ArrayAdapter<JointApplicant>(this, android.R.layout.simple_spinner_item, _additionalApplicant) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uploadDocumentsBinding.spAdditionalApplicant.setAdapter(dataAdapter);
    }

    private void openAdditionalApplicantActivity() {
        Intent intent = new Intent(this, AdditionalApplicantActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setLayout();
    }
}
