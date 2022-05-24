package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.cnic_upload;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpPostAttachment;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpResponse;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.otp_phase2.OtpVerification;
import com.unikrew.faceoff.Config;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CnicUploadActivity extends BaseActivity implements View.OnClickListener{

    /* variables */
    private ImageView imgCnicFront;
    private ImageView imgCnicBack;
    private Button btnCnicUploadNext;
    private Button btnCnicUploadCancel;



    public ArrayList<ViewAppsGenerateOtpPostAttachment> attachments = new ArrayList<ViewAppsGenerateOtpPostAttachment>();


    private ViewAppsGenerateOtpPostParams postParams;
    private CnicUploadViewModel viewModel;

    String cnicFrontPic = "";
    String cnicBackPic = "";

    /* The purpose of below variables are just for placement of pictures in correct position. */
    Boolean frontPic = false;
    Boolean backPic = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cnic_upload_before);

        bind();
        setListeners();
        setViewModel();
        observeData();

    }

    private void observeData() {
        viewModel.responseLiveData.observe(this,new Observer<ViewAppsGenerateOtpResponse>() {
            @Override
            public void onChanged(ViewAppsGenerateOtpResponse viewAppsGenerateOtpResponse) {
                openOtpVerificationActivity(viewAppsGenerateOtpResponse);
                loader.dismiss();
            }
        });

        viewModel.responseErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType,errMsg);
                loader.dismiss();
            }
        });
    }

    private void openOtpVerificationActivity(ViewAppsGenerateOtpResponse response) {
        Intent intent = new Intent(this, OtpVerification.class);

        intent.putExtra(Config.RESPONSE,response);

        startActivity(intent);
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(CnicUploadViewModel.class);
        postParams = new ViewAppsGenerateOtpPostParams();
    }

    private void bind() {
        imgCnicFront = findViewById(R.id.cnic_upload_front);
        imgCnicBack = findViewById(R.id.cnic_upload_back);
        btnCnicUploadNext = findViewById(R.id.btn_next);
        btnCnicUploadCancel = findViewById(R.id.btn_cancel);
    }

    private void setListeners(){
        imgCnicFront.setOnClickListener(this);
        imgCnicBack.setOnClickListener(this);
        btnCnicUploadNext.setOnClickListener(this);
        btnCnicUploadCancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cnic_upload_front:
                frontPic = true;
                backPic = false;
                getPicFromCamera();
                break;
            case R.id.cnic_upload_back:
                backPic = true;
                frontPic = false;
                getPicFromCamera();
                break;
            case R.id.btn_next:
                postData();
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    /* View Apps Generate Otp Post Method*/
    private void postData() {

        if (cnicFrontPic.equals("")){
            showAlert(Config.errorType,"Please Upload Cnic Front Pic !!!");
            return;
        }else if (cnicBackPic.equals("")){
            showAlert(Config.errorType,"Please Upload Cnic Back Pic !!!");
            return;
        }else{
            setAttachmentObject();
            setPostParams();
            viewModel.viewAppsGenerateOtpPostData(postParams);
            showLoading();
        }
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
        postParams.getData().setMobileNo( getIntent().getStringExtra(Config.MOBILE_NUMBER) );
        postParams.getData().setGenerateOtp( true );
//        postParams.getData().setPortedMobileNetwork( getIntent().getBooleanExtra(Config.PORTED_MOBILE_NETWORK,false) );
        postParams.getData().setAttachments(attachments);
    }


    private void getPicFromCamera(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, Config.MY_CAMERA_PERMISSION_CODE);
            }
            else
            {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Config.CAMERA_REQUEST);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Config.MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Config.CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Config.CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap image = null;
            if (image == null && frontPic){

                image = (Bitmap) data.getExtras().get("data");
                imgCnicFront.setImageBitmap(image);
                showCnicImageResource(
                        findViewById(R.id.img_rescan_cnic_front),
                        findViewById(R.id.tv_rescan_cnic_front),
                        findViewById(R.id.tick_cnic_front)
                );
               cnicFrontPic = convertToBase64(image);

            }else if (image == null && backPic){
                image  = (Bitmap) data.getExtras().get("data");
                imgCnicBack.setImageBitmap(image);

                showCnicImageResource(
                        findViewById(R.id.img_rescan_cnic_back),
                        findViewById(R.id.tv_rescan_cnic_back),
                        findViewById(R.id.tick_cnic_back)
                );

                cnicBackPic = convertToBase64(image);
            }

        }
    }

    private String convertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void showCnicImageResource(View viewById, View viewById1, View viewById2) {
        viewById.setVisibility(View.VISIBLE);
        viewById1.setVisibility(View.VISIBLE);
        viewById2.setVisibility(View.VISIBLE);
    }

}
