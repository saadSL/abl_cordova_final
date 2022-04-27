package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.cnic_upload;

import static androidx.navigation.ViewKt.findNavController;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.CnicUploadBeforeBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.base.BaseFragment;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpPostAttachment;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpResponse;
import com.unikrew.faceoff.Config;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CnicUploadActivity extends BaseFragment implements View.OnClickListener{


    public ArrayList<ViewAppsGenerateOtpPostAttachment> attachments = new ArrayList<ViewAppsGenerateOtpPostAttachment>();


    private ViewAppsGenerateOtpPostParams postParams;
    private CnicUploadViewModel viewModel;

    String cnicFrontPic = "";
    String cnicBackPic = "";

    /* The purpose of below variables are just for placement of pictures in correct position. */
    Boolean frontPic = false;
    Boolean backPic = false;

    private CnicUploadBeforeBinding cnicUploadBeforeBinding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cnicUploadBeforeBinding =     CnicUploadBeforeBinding.inflate(inflater, container, false);
        setListeners();
        setViewModel();
        observeData();


        return cnicUploadBeforeBinding.getRoot();
    }

    private void observeData() {
        viewModel.responseLiveData.observe(getViewLifecycleOwner(),new Observer<ViewAppsGenerateOtpResponse>() {
            @Override
            public void onChanged(ViewAppsGenerateOtpResponse viewAppsGenerateOtpResponse) {
                openOtpVerificationActivity(viewAppsGenerateOtpResponse);
                dismissLoading();
            }
        });

        viewModel.responseErrorLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType,errMsg);
               dismissLoading();
            }
        });
    }

    private void openOtpVerificationActivity(ViewAppsGenerateOtpResponse response) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(Config.RESPONSE, response);
//        findNavController(cnicUploadBeforeBinding.getRoot()).navigate(R.id.openOtpVerfication, bundle);
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(CnicUploadViewModel.class);
        postParams = new ViewAppsGenerateOtpPostParams();
    }

    private void setListeners(){

        cnicUploadBeforeBinding.cnicUploadFront.setOnClickListener(this);
        cnicUploadBeforeBinding.cnicUploadBack.setOnClickListener(this);
        cnicUploadBeforeBinding.genButtonLayout.btnNext.setOnClickListener(this);
        cnicUploadBeforeBinding.genButtonLayout.btnCancel.setOnClickListener(this);
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
//                finish();
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
        postParams.getData().setMobileNo( getArguments().getString(Config.MOBILE_NUMBER) );
        postParams.getData().setGenerateOtp( true );
        postParams.getData().setPortedMobileNetwork( getArguments().getBoolean(Config.PORTED_MOBILE_NETWORK,false) );
        postParams.getData().setAttachments(attachments);
    }


    private void getPicFromCamera(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(mContext,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
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
                Toast.makeText(mContext, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Config.CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(mContext, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap image = null;
            if (image == null && frontPic){

                image = (Bitmap) data.getExtras().get("data");
                cnicUploadBeforeBinding.cnicUploadFront.setImageBitmap(image);
                showCnicImageResource(
                        cnicUploadBeforeBinding.imgRescanCnicFront,
                        cnicUploadBeforeBinding.tvRescanCnicFront,
                        cnicUploadBeforeBinding.tickCnicFront
                );
                cnicFrontPic = convertToBase64(image);

            }else if (image == null && backPic){
                image  = (Bitmap) data.getExtras().get("data");
                cnicUploadBeforeBinding.cnicUploadBack.setImageBitmap(image);

                showCnicImageResource(
                        cnicUploadBeforeBinding.imgRescanCnicBack,
                        cnicUploadBeforeBinding.tvRescanCnicBack,
                        cnicUploadBeforeBinding.tickCnicBack
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
