package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.otp_phase2;

import static androidx.navigation.ViewKt.findNavController;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.OpenAccountOtpVerificationBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseFragment;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerfiyOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpResponse;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account.SelectBankingModeFragment;
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

public class OtpVerificationFragment extends BaseFragment implements View.OnClickListener {


    OtpVerificationViewModel viewModel;
    GetDraftedAppsVerfiyOtpPostParams postParams;
    private ViewAppsGenerateOtpPostParams viewAppsGenerateOtpPostParams;

    ViewAppsGenerateOtpResponse res;
    private OpenAccountOtpVerificationBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = OpenAccountOtpVerificationBinding.inflate(inflater, container, false);
        setListeners();
        setViewModel();
        setAutoFocusForOtp(binding.etOtp1, binding.etOtp2, binding.etOtp3, binding.etOtp4, binding.etOtp5, binding.etOtp6);
        observe();


        return binding.getRoot();
    }

    private void setLayout() {
        binding.screenHeader.stepsHeading1.setText("Verify");
        binding.screenHeader.stepsHeading2.setText("OTP");
        setLogoLayout(binding.logoToolbar.tvDate);
    }


    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(OtpVerificationViewModel.class);
        postParams = new GetDraftedAppsVerfiyOtpPostParams();
        res = (ViewAppsGenerateOtpResponse) getArguments().getSerializable(Config.RESPONSE);
        viewAppsGenerateOtpPostParams = new ViewAppsGenerateOtpPostParams();
    }


    private void observe() {
        otp6LiveData.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                /* aBoolean works if last field for otp is filled. */
                if (aBoolean) {
                    postOtp();
                }
            }
        });

        viewModel.otpVerificationResponseLiveData.observe(getViewLifecycleOwner(), new Observer<GetDraftedAppsVerifyOtpResponse>() {
            @Override
            public void onChanged(GetDraftedAppsVerifyOtpResponse getDraftedAppsVerifyOtpResponse) {
                if (getDraftedAppsVerifyOtpResponse.getData().getAppList().size() > 0) {
                    openAccountApplication(getDraftedAppsVerifyOtpResponse);
                    saveStringInPref(Config.ACCESS_TOKEN, getDraftedAppsVerifyOtpResponse.getData().accessToken);
                } else {
                    setupAccount();
                }

                dismissLoading();
            }
        });

        viewModel.otpVerificationErrorLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showAlert(Config.errorType, s);
                dismissLoading();
            }
        });


        viewModel.reSendOtpSuccessLiveData.observe(getViewLifecycleOwner(), new Observer<ViewAppsGenerateOtpResponse>() {
            @Override
            public void onChanged(ViewAppsGenerateOtpResponse viewAppsGenerateOtpResponse) {
                showAlert(Config.successType, "OTP Request Successful !!!");
                dismissLoading();
            }
        });

        viewModel.reSendOtpErrorLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showAlert(Config.errorType, "OTP Request Failed !!!");
            }
        });
    }

    private void setupAccount() {
        findNavController(binding.getRoot()).navigate(R.id.openBankingMode);
    }

    private void openAccountApplication(GetDraftedAppsVerifyOtpResponse getDraftedAppsVerifyOtpResponse) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Config.RESPONSE, getDraftedAppsVerifyOtpResponse);
        findNavController(binding.getRoot()).navigate(R.id.openAccountApplication, bundle);

//        get.finish();
    }


    private void setListeners() {
        binding.linkForOTP.setOnClickListener(this);
        binding.btnContainer.btnNext.setOnClickListener(this);
        binding.btnContainer.btBack.setOnClickListener(this);
        binding.changeMobileNumLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                postOtp();
                break;
            case R.id.linkForOTP:
                reSendOtp();
                break;
            case R.id.bt_back:
//                finish();
                break;
            case R.id.change_mobile_num_link:
                openChangeMobileNumberActivity();
                break;
        }
    }

    private void openChangeMobileNumberActivity() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Config.RESPONSE, res);
        findNavController(binding.getRoot()).navigate(R.id.openChangeNumber, bundle);
    }

    private void reSendOtp() {
        setResendOtpPostParams();
        viewModel.viewAppsGenerateOtpPostData(viewAppsGenerateOtpPostParams);
        showLoading();
    }

    private void setResendOtpPostParams() {
        viewAppsGenerateOtpPostParams.getData().setCustomerTypeId(res.getData().getCustomerTypeId());
        viewAppsGenerateOtpPostParams.getData().setMobileNo(res.getData().getMobileNo());
        viewAppsGenerateOtpPostParams.getData().setGenerateOtp(true);
        viewAppsGenerateOtpPostParams.getData().setIdNumber(res.getData().getIdNumber());
    }

    private void postOtp() {
        setPostParams();
        viewModel.getDraftedAppsVerifyOtp(postParams);
        showLoading();
    }

    private void setPostParams() {
        postParams.getData().setCustomerTypeId(Config.CUSTOMER_TYPE_ID);
        postParams.getData().setIdNumber(res.getData().getIdNumber());
        postParams.getData().setMobileNo(res.getData().getMobileNo().toString());
        postParams.getData().setOtp(getEncryptedOtp(getOtp()));

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
        return binding.etOtp1.getText().toString() +
                binding.etOtp2.getText().toString() +
                binding.etOtp3.getText().toString() +
                binding.etOtp4.getText().toString() +
                binding.etOtp5.getText().toString() +
                binding.etOtp6.getText().toString();
    }


    @Override
    public void onResume() {
        super.onResume();
        setLayout();
    }
}
