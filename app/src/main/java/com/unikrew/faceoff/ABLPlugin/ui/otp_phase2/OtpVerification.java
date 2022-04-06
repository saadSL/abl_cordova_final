package com.unikrew.faceoff.ABLPlugin.ui.otp_phase2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.phase2.get_drafted_apps_verify_otp.GetDraftedAppsVerfiyOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.phase2.get_drafted_apps_verify_otp.GetDraftedAppsVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.ui.account_application.AccountApplicationActivity;
import com.unikrew.faceoff.Config;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class OtpVerification extends BaseActivity implements View.OnClickListener {

    /* Variables */
//    TextView mobileNum;

    private EditText otp1;
    private EditText otp2;
    private EditText otp3;
    private EditText otp4;
    private EditText otp5;
    private EditText otp6;

    private TextView otpLink;

    private ImageView btnBack;
    private Button btnVerify;

    OtpVerificationViewModel viewModel;
    GetDraftedAppsVerfiyOtpPostParams postParams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_account_otp_verification);

        bind();
        set();
        setViewModel();
        setAutoFocusForOtp(otp1,otp2,otp3,otp4,otp5,otp6);
        observe();
//        setTimer();
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(OtpVerificationViewModel.class);
        postParams = new GetDraftedAppsVerfiyOtpPostParams();
    }


    private void observe() {
        otp6LiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                /* aBoolean works if last field for otp is filled. */
                if (aBoolean){
                    postOtp();
                }
            }
        });

        viewModel.otpVerificationResponseLiveData.observe(this, new Observer<GetDraftedAppsVerifyOtpResponse>() {
            @Override
            public void onChanged(GetDraftedAppsVerifyOtpResponse getDraftedAppsVerifyOtpResponse) {
                if (getDraftedAppsVerifyOtpResponse.getData().getAppList().size() > 0){
                    openAccountApplication(getDraftedAppsVerifyOtpResponse);
                }else{
                    showAlert(Config.successType,"Drafted Application Not Available !!!");
                }

                loader.dismiss();
            }
        });

        viewModel.otpVerificationErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showAlert(Config.errorType,s);
                loader.dismiss();
            }
        });
    }

    private void openAccountApplication(GetDraftedAppsVerifyOtpResponse getDraftedAppsVerifyOtpResponse) {
        Intent intent = new Intent(this, AccountApplicationActivity.class);

        intent.putExtra( Config.APP_LIST,getDraftedAppsVerifyOtpResponse );
        intent.putExtra(Config.ACCESS_TOKEN,getDraftedAppsVerifyOtpResponse.getData().getAccessToken());

        startActivity(intent);
    }


    private void set() {
        otpLink.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnVerify.setOnClickListener(this);
    }

    private void bind() {
//        mobileNum = findViewById(R.id.tv_mobile_num);

        otp1 = findViewById(R.id.et_otp1);
        otp2 = findViewById(R.id.et_otp2);
        otp3 = findViewById(R.id.et_otp3);
        otp4 = findViewById(R.id.et_otp4);
        otp5 = findViewById(R.id.et_otp5);
        otp6 = findViewById(R.id.et_otp6);

        otpLink = findViewById(R.id.linkForOTP);

        btnVerify = findViewById(R.id.btn_verify_otp);
        btnBack = findViewById(R.id.iv_back);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_verify_otp:
                postOtp();
                break;
            case R.id.linkForOTP:
                reSendOtp();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void reSendOtp() {

    }

    private void postOtp() {
        setPostParams();
        viewModel.getDraftedAppsVerifyOtp(postParams);
        showLoading();
        loader.show();
    }

    private void setPostParams() {
        postParams.getData().setCustomerTypeId(Config.CUSTOMER_TYPE_ID);
        postParams.getData().setIdNumber( getIntent().getStringExtra(Config.CNIC_NUMBER) );
        postParams.getData().setMobileNo( getIntent().getStringExtra(Config.MOBILE_NUMBER) );
        postParams.getData().setOtp( getEncryptedOtp( getOtp() ) );

        postParams.getPagination().setPage(Config.PAGE_NUMBER);
        postParams.getPagination().setSize(Config.PAGE_SIZE);
        postParams.getPagination().setTotalPages(null);
        postParams.getPagination().setTotalElements(null);
        postParams.getPagination().setSortOrder(null);
        postParams.getPagination().setSortBy(null);
    }

    private String getEncryptedOtp(String otp) {
        String initVector = "0000000000000000";
        String key = "4dweqdxcerfvc3rw";

        IvParameterSpec ivParameterSpec = null;
        ivParameterSpec = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec secretKeySpec = null;
        secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), Config.ALGO);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(Config.ALGO);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            if (cipher != null) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            }
        } catch (InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] encrypted = new byte[0];
        try {
            if (cipher != null) {
                encrypted = cipher.doFinal(otp.getBytes());
            }
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(encrypted, Base64.NO_WRAP);
    }

    private String getOtp() {
        return otp1.getText().toString()+
                    otp2.getText().toString()+
                    otp3.getText().toString()+
                    otp4.getText().toString()+
                    otp5.getText().toString()+
                    otp6.getText().toString();
    }
}
