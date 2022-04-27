package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.mobile_number;

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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.MobileNumberAvailabilityBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseFragment;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpPostAttachment;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpResponse;
import com.unikrew.faceoff.Config;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MobileNumberFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    /* variables  */
    private MobileNumberViewModel viewModel;
    private ViewAppsGenerateOtpPostParams postParams;

    private Boolean isPortedMobileNetwork = false;
    private Boolean generateOtp = false;

    private ImageView imgCnicFront;
    private ImageView imgCnicBack;
    String cnicFrontPic = "";
    String cnicBackPic = "";
    Boolean frontPic = false;
    Boolean backPic = false;
    Bitmap image;
    Boolean alreadyExist = false;
    public ArrayList<ViewAppsGenerateOtpPostAttachment> attachments = new ArrayList<ViewAppsGenerateOtpPostAttachment>();
    private MobileNumberAvailabilityBinding mobileNumberAvailabilityBinding;
    private boolean returnedFromNextScreen = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mobileNumberAvailabilityBinding = MobileNumberAvailabilityBinding.inflate(inflater, container, false);


        setListeners();
        setViewModel();
        observeData();

        return mobileNumberAvailabilityBinding.getRoot();
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


    private void observeData() {
        viewModel.responseLiveData.observe(getViewLifecycleOwner(), new Observer<ViewAppsGenerateOtpResponse>() {
            @Override
            public void onChanged(ViewAppsGenerateOtpResponse viewAppsGenerateOtpResponse) {
                alreadyExist = viewAppsGenerateOtpResponse.getData().isAlreadyExist();

                if (alreadyExist) {
                    if (generateOtp) {
                        returnedFromNextScreen = true;
                        openOtpVerificationActivity(viewAppsGenerateOtpResponse);
                    } else {
                        showCnic(viewAppsGenerateOtpResponse);
                    }
                } else {
                    if (generateOtp) {
                        returnedFromNextScreen = true;
                        openOtpVerificationActivity(viewAppsGenerateOtpResponse);
                    } else {
                        openCnicUploadActivity();
                    }

                }
                dismissLoading();
            }
        });

        viewModel.errorLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType, errMsg);
                dismissLoading();
            }
        });

    }

    private void openOtpVerificationActivity(ViewAppsGenerateOtpResponse response) {
        saveInLocal();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Config.RESPONSE, response);
        Navigation.findNavController(requireView()).navigate(R.id.openOtpVerfication,bundle);
    }

    private void saveInLocal() {
        saveStringInPref(Config.CNIC_NUMBER, mobileNumberAvailabilityBinding.etCnicNumber.getText().toString());
        saveStringInPref(Config.MOBILE_NUMBER, mobileNumberAvailabilityBinding.etMobileNum.getText().toString());
    }

    /* The method below will work when mobile number is not registered with cnic. */
    private void openCnicUploadActivity() {
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
            mobileNumberAvailabilityBinding.etCnicNumber.setText(response.getData().getIdNumber());
        } else {
            mobileNumberAvailabilityBinding.etCnicNumber.setText("");
        }

        generateOtp = true;
    }


    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(MobileNumberViewModel.class);
        postParams = new ViewAppsGenerateOtpPostParams();
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
                getActivity().finish();
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
        if (isPermission()) {
            getImageFromCamera();
        } else {
            showAlert(Config.errorType, "Permission Not Granted !!!");
        }
    }

    private void getCnicFrontImage() {
//        mobileNumberAvailabilityBinding.imgCnicFrontSmall.setVisibility(View.VISIBLE);
//        mobileNumberAvailabilityBinding.llCnicFront.setVisibility(View.VISIBLE);
        if (isPermission()) {
            getImageFromCamera();
        } else {
            showAlert(Config.errorType, "Permission Not Granted !!!");
        }

    }

    private boolean isPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, Config.MY_CAMERA_PERMISSION_CODE);
            } else {
                return true;
            }
        }
        return false;
    }

    private void getImageFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, Config.CAMERA_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Config.MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Config.CAMERA_REQUEST);
            } else {
                Toast.makeText(mContext, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
        if (generateOtp) {
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
        postParams.getData().setGenerateOtp(generateOtp);

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
                    isPortedMobileNetwork = true;
                } else {
                    isPortedMobileNetwork = false;
                }
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setLayout();
    }
}