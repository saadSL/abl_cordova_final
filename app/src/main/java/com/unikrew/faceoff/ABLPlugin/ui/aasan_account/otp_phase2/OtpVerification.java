package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.otp_phase2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.OpenAccountOtpVerificationBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerfiyOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpResponse;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.account_application.AccountApplicationActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.change_mobile_number.ChangeMobileNumberActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.employment_details.EmploymentDetailsActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account.SelectBankingModeActivity;
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


    OtpVerificationViewModel viewModel;
    GetDraftedAppsVerfiyOtpPostParams postParams;


    ViewAppsGenerateOtpResponse res;
    private OpenAccountOtpVerificationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setListeners();
        setViewModel();
        setAutoFocusForOtp(binding.etOtp1, binding.etOtp2, binding.etOtp3, binding.etOtp4, binding.etOtp5, binding.etOtp6);
        observe();
    }

    private void setBinding() {
        binding = OpenAccountOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(OtpVerificationViewModel.class);
        postParams = new GetDraftedAppsVerfiyOtpPostParams();
        res = (ViewAppsGenerateOtpResponse) getIntent().getSerializableExtra(Config.RESPONSE);
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
                    saveStringInPref(Config.ACCESS_TOKEN,getDraftedAppsVerifyOtpResponse.getData().accessToken);
                }else{
                    setupAccount();
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

    private void setupAccount() {
        Intent intent = new Intent(this, SelectBankingModeActivity.class);
        startActivity(intent);
    }

    private void openAccountApplication(GetDraftedAppsVerifyOtpResponse getDraftedAppsVerifyOtpResponse) {
        Intent intent = new Intent(this, AccountApplicationActivity.class);

        intent.putExtra( Config.RESPONSE,getDraftedAppsVerifyOtpResponse );

        startActivity(intent);
    }


    private void setListeners() {
        binding.linkForOTP.setOnClickListener(this);
        binding.navToolbar.ivBack.setOnClickListener(this);
        binding.btnVerifyOtp.setOnClickListener(this);
        binding.changeMobileNumLink.setOnClickListener(this);
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
            case R.id.change_mobile_num_link:
                openChangeMobileNumberActivity();
                break;
        }
    }

    private void openChangeMobileNumberActivity() {
        Intent intent = new Intent(this, ChangeMobileNumberActivity.class);

        intent.putExtra(Config.RESPONSE,res);

        startActivity(intent);
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
        postParams.getData().setIdNumber( res.getData().getIdNumber() );
        postParams.getData().setMobileNo( res.getData().getMobileNo().toString() );
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
        return binding.etOtp1.getText().toString()+
                    binding.etOtp2.getText().toString()+
                    binding.etOtp3.getText().toString()+
                    binding.etOtp4.getText().toString()+
                    binding.etOtp5.getText().toString()+
                    binding.etOtp6.getText().toString();
    }
}
