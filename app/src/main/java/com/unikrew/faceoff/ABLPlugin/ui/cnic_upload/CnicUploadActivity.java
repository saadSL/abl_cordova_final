package com.unikrew.faceoff.ABLPlugin.ui.cnic_upload;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.ABLPlugin.BaseClass;
import com.unikrew.faceoff.ABLPlugin.model.phase2.view_apps_generate_otp.ViewAppsGenerateOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.phase2.view_apps_generate_otp.ViewAppsGenerateOtpResponse;
import com.unikrew.faceoff.Config;

public class CnicUploadActivity extends BaseClass implements View.OnClickListener{

    /* variables */
    private ImageView imgCnicFront;
    private ImageView imgCnicBack;
    private Button btnCnicUploadNext;
    private Button btnCnicUploadCancel;

    private ViewAppsGenerateOtpPostParams postParams;
    private CnicUploadViewModel viewModel;

    Bitmap cnicFrontPic;
    Bitmap cnicBackPic;

    /* The purpose of below variables are just for placement of pictures in correct position. */
    Boolean frontPic = false;
    Boolean backPic = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cnic_upload_before);

        bind();
        set();
        setViewModel();
        observeData();

    }

    private void observeData() {
        viewModel.responseLiveData.observe(this,new Observer<ViewAppsGenerateOtpResponse>() {
            @Override
            public void onChanged(ViewAppsGenerateOtpResponse viewAppsGenerateOtpResponse) {
                showAlert(Config.successType, "Response recieved successfully !!!");


            }
        });

        viewModel.responseErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showAlert(Config.errorType,s);
            }
        });
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

    private void set(){
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
                showAlert(Config.successType,"Cancel Clicked");
                break;
        }
    }

    /* View Apps Generate Otp Post Method*/
    private void postData() {

        postParams.getData().setCustomerTypeId(Config.customerTypeId);
        postParams.getData().setIdNumber(getIntent().getStringExtra(Config.CNIC_NUMBER));
        postParams.getData().setMobileNo(getIntent().getStringExtra(Config.MOBILE_NUMBER));
//        postParams.getViewAppsGenerateOtpPostData().setMobileNetwork(getIntent().getStringExtra(Config.MOBILE_NETWORK));
//        postParams.getViewAppsGenerateOtpPostData().setPortedMobileNetwork(false);

        viewModel.postData(postParams);

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
            if (cnicFrontPic == null && frontPic){
                cnicFrontPic = (Bitmap) data.getExtras().get("data");
                imgCnicFront.setImageBitmap(cnicFrontPic);

                showCnicImageResource(
                        findViewById(R.id.img_rescan_cnic_front),
                        findViewById(R.id.tv_rescan_cnic_front),
                        findViewById(R.id.tick_cnic_front)
                );

            }else if (cnicBackPic == null && backPic){
                cnicBackPic = (Bitmap) data.getExtras().get("data");
                imgCnicBack.setImageBitmap(cnicBackPic);

                showCnicImageResource(
                        findViewById(R.id.img_rescan_cnic_back),
                        findViewById(R.id.tv_rescan_cnic_back),
                        findViewById(R.id.tick_cnic_back)
                );
            }

        }
    }

    private void showCnicImageResource(View viewById, View viewById1, View viewById2) {
        viewById.setVisibility(View.VISIBLE);
        viewById1.setVisibility(View.VISIBLE);
        viewById2.setVisibility(View.VISIBLE);
    }

}
