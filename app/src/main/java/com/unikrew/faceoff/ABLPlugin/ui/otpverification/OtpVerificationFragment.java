package com.unikrew.faceoff.ABLPlugin.ui.otpverification;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.unikrew.faceoff.ABLPlugin.CNIC_Availability;
import com.unikrew.faceoff.ABLPlugin.OTPReciever;
import com.unikrew.faceoff.ABLPlugin.model.CnicPostParams;
import com.unikrew.faceoff.ABLPlugin.model.OtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.OtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.ResponseDTO;
import com.unikrew.faceoff.ABLPlugin.ui.fingerprintverification.FingerPrintFragment;
import com.unikrew.faceoff.Config;
import com.ofss.digx.mobile.android.allied.R;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class OtpVerificationFragment extends Fragment implements TextWatcher {

    private EditText otp1;
    private EditText otp2;
    private EditText otp3;
    private EditText otp4;
    private EditText otp5;
    private EditText otp6;

    private TextView mobileNumber;

    private TextView timer;
    private Button btnVerify, btnCancel;

    ResponseDTO res;
    CnicPostParams cnicPostParams;

    public static CountDownTimer countDownTimer;

    boolean otpStatus = false;
    OtpVerificationViewModel otpVerificationViewModel;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.otp_verification, container, false);
        otpVerificationViewModel = new ViewModelProvider(this).get(OtpVerificationViewModel.class);
        bind(view);
        set();
        clicks();
        new OTPReciever().setEditText_otp(
                otp1, otp2, otp3,
                otp4, otp5, otp6
        );

        startTimer(Config.countDownTime);

        return view;
    }

    private void clicks() {
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOtp();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelActivity();
            }
        });
    }

    private void set() {
        Bundle args = getArguments();
        res = (ResponseDTO) args.getSerializable(Config.RESPONSE);
        mobileNumber.setText("03XX-XXXX" + res.getData().getMobileNo().substring(res.getData().getMobileNo().length() - 3));
        cnicPostParams = (CnicPostParams) args.getSerializable(Config.CNIC_ACC);
        otp6.addTextChangedListener(this);
    }

    private void bind(View view) {
        otp1 = view.findViewById(R.id.et_otp1);
        otp2 = view.findViewById(R.id.et_otp2);
        otp3 = view.findViewById(R.id.et_otp3);
        otp4 = view.findViewById(R.id.et_otp4);
        otp5 = view.findViewById(R.id.et_otp5);
        otp6 = view.findViewById(R.id.et_otp6);
        btnVerify = view.findViewById(R.id.btn_verify);
        btnCancel = view.findViewById(R.id.btn_cancel);
        mobileNumber = view.findViewById(R.id.tv_mobileNumber);
        timer = view.findViewById(R.id.timer);
    }

    private void startTimer(int totalTime) {
        countDownTimer = new CountDownTimer(totalTime, 1000) {
            @Override
            public void onTick(long l) {
                int minutes = (int) (l / 1000) / 60;
                int seconds = (int) (l / 1000) % 60;
                String timeFormat = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                if (minutes == 0) {
                    timer.setText(timeFormat + " seconds");
                } else {
                    timer.setText(timeFormat + " minutes");
                }

            }

            @Override
            public void onFinish() {
                showAlert("You OTP Expired");
                countDownTimer.cancel();
            }
        }.start();
    }

    public void OTPVerification(View view) throws InterruptedException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        if (isEmpty(otp1) || isEmpty(otp4) ||
                isEmpty(otp2) || isEmpty(otp5) ||
                isEmpty(otp3) || isEmpty(otp6)) {
            showAlert("OTP fields empty");
            return;
        } else if (!isOnline()) {
            showAlert("No internet connection !!!");
            return;
        }

        String otp = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() + otp4.getText().toString() + otp5.getText().toString() + otp6.getText().toString();

        OtpPostParams otpPostParams = new OtpPostParams();

        otpPostParams.getData().setOtp(encrypt(otp));
        otpPostParams.getData().setRdaCustomerProfileId("" + res.getData().getEntityId());

        otpVerificationViewModel.postOtp(otpPostParams, res.getData().getAccessToken(), (Activity) mContext);


        otpVerificationViewModel.OtpSuccessLiveData.observe(this, new Observer<OtpResponse>() {
            @Override
            public void onChanged(OtpResponse otpResponse) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Config.RESPONSE, otpResponse);
                bundle.putString("TOKEN", res.getData().getAccessToken());
                ((CNIC_Availability) mContext).replaceFingerPrintFragment(bundle);
                countDownTimer.cancel();
                clearFields();
            }
        });

        otpVerificationViewModel.OtpErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMsg) {
                showAlert(errorMsg);
                clearFields();
            }
        });
//        startActivity(new Intent(OtpVerificationActivity.this, FingerPrintActivity.class));
    }

    private void clearFields() {
        otp1.setText("");
        otp2.setText("");
        otp3.setText("");
        otp4.setText("");
        otp5.setText("");
        otp6.setText("");
    }

    public Boolean isEmpty(EditText et) {
        if (et.getText().toString().equals("") || et.getText().toString().equals("-")) {
            return true;
        }
        return false;
    }

    public void cancelActivity() {

//        finish();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        countDownTimer.cancel();
    }

    public void sendOtp() {
        try {
            otpVerificationViewModel.postCNIC(cnicPostParams, (Activity) mContext);
            countDownTimer.start();

            otpVerificationViewModel.CnicSuccessLiveData.observe(this, new Observer<ResponseDTO>() {
                @Override
                public void onChanged(ResponseDTO responseDTO) {
                    showAlert("OTP Request Send");
                }
            });

            otpVerificationViewModel.CnicErrorLiveData.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    showAlert(s);
                }
            });


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void messageFunc() {
        showAlert("Sorry, currently the function is not responsive !!!");
    }

    public void powerSettingFunc() {
        showAlert("Sorry, currently the function is not responsive !!!");
    }

    @SuppressLint("NewApi")
    public String encrypt(String value) {

        String initVector = "0000000000000000";
        String key = "4dweqdxcerfvc3rw";
        IvParameterSpec ivParameterSpec = null;
        try {
            ivParameterSpec = new IvParameterSpec(initVector.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SecretKeySpec secretKeySpec = null;
        try {
            secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] encrypted = new byte[0];
        try {
            encrypted = cipher.doFinal(value.getBytes());
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(encrypted);
    }


    public void showAlert(String msg) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) mContext.getApplicationContext().getSystemService(mContext.getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            return false;
        }
        return true;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        System.out.println("Do Nothing!!!");
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        try {
            if (otp6.getText().length() > 0) {
                OTPVerification(btnVerify);
            }

        } catch (InterruptedException | UnsupportedEncodingException | BadPaddingException | NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        System.out.println("After Text changed : ");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}