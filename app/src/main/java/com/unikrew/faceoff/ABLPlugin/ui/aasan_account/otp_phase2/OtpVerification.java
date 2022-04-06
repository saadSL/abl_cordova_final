package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.otp_phase2;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerfiyOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerifyOtpResponse;
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
                if (aBoolean){
                    postOtp();
                }
            }
        });

        viewModel.otpVerificationResponseLiveData.observe(this, new Observer<GetDraftedAppsVerifyOtpResponse>() {
            @Override
            public void onChanged(GetDraftedAppsVerifyOtpResponse getDraftedAppsVerifyOtpResponse) {
                showAlert(Config.successType,getDraftedAppsVerifyOtpResponse.getMessage().getStatus()+"\n"
                +getDraftedAppsVerifyOtpResponse.getMessage().getDescription()+"\n"
                +getDraftedAppsVerifyOtpResponse.getMessage().getErrorDetail());
            }
        });

        viewModel.otpVerificationErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showAlert(Config.errorType,s);
            }
        });
    }


    private void set() {
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


        btnVerify = findViewById(R.id.btn_verify_otp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_verify_otp:
                postOtp();
                break;
        }
    }

    private void postOtp() {
        setPostParams();
        viewModel.getDraftedAppsVerifyOtp(postParams,this);
    }

    private void setPostParams() {
        postParams.getData().setCustomerTypeId(Config.customerTypeId);
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
        secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), Config.algorithm);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(Config.algorithm);
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
