package com.unikrew.faceoff.ABLPlugin.ui.otp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.unikrew.faceoff.ABLPlugin.CNIC_Availability;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationResponse;
import com.unikrew.faceoff.ABLPlugin.model.VerifyOtpBioMetricVerificationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.VerifyOtpBioMetricVerificationResponse;
import com.unikrew.faceoff.ABLPlugin.ui.fingerprint.FingerPrintActivity;
import com.unikrew.faceoff.Config;
import com.ofss.digx.mobile.android.allied.R;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class OtpVerificationActivity extends AppCompatActivity {

    private EditText otp1;
    private EditText otp2;
    private EditText otp3;
    private EditText otp4;
    private EditText otp5;
    private EditText otp6;

    private Button btnVerify;

    private TextView mobileNumber;

    private BioMetricVerificationResponse res;
    private BioMetricVerificationPostParams bioMetricVerificationPostParams;


    boolean otpStatus = false;
    private OtpVerificationViewModel otpVerificationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verification);
        setViewModel();
        bind();
        set();
        textChangeListeners();
        clicks();
        observeData();
    }

    private void clicks() {
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpVerification();
            }
        });
    }

    private void observeData() {
        otpVerificationViewModel.CnicSuccessLiveData.observe(this, new Observer<BioMetricVerificationResponse>() {
            @Override
            public void onChanged(BioMetricVerificationResponse bioMetricVerificationResponse) {
                showAlert("OTP Request Send");
            }
        });

        otpVerificationViewModel.CnicErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showAlert(s);
            }
        });


        otpVerificationViewModel.OtpSuccessLiveData.observe(this, new Observer<VerifyOtpBioMetricVerificationResponse>() {
            @Override
            public void onChanged(VerifyOtpBioMetricVerificationResponse verifyOtpBioMetricVerificationResponse) {
                Intent i = new Intent(OtpVerificationActivity.this, FingerPrintActivity.class);
                i.putExtra(Config.RESPONSE, res);
                i.putExtra("TOKEN", res.getData().getAccessToken());
                startActivity(i);
                clearFields();
                finish();
            }
        });

        otpVerificationViewModel.OtpErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMsg) {
                showAlert(errorMsg);
                clearFields();
            }
        });
    }

    private void setViewModel() {
        otpVerificationViewModel = new ViewModelProvider(this).get(OtpVerificationViewModel.class);
    }

    private void textChangeListeners() {
        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();

                if (text.length() == 1) {
                    otp2.requestFocus();
                }
            }
        });

        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();

                if (text.length() == 1) {
                    otp3.requestFocus();
                }
            }
        });

        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();

                if (text.length() == 1) {
                    otp4.requestFocus();
                }
            }
        });

        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();

                if (text.length() == 1) {
                    otp5.requestFocus();
                }
            }
        });

        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();

                if (text.length() == 1) {
                    otp6.requestFocus();
                }
            }
        });

        otp6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (otp6.getText().length() > 0) {
                    otpVerification();
                }
            }
        });
    }


    private void set() {
        res = (BioMetricVerificationResponse) getIntent().getSerializableExtra(Config.RESPONSE);
//        mobileNumber.setText("03XX-XXXX" + res.getData().getMobileNo().substring(res.getData().getMobileNo().length() - 3));
        bioMetricVerificationPostParams = (BioMetricVerificationPostParams) getIntent().getSerializableExtra(Config.CNIC_ACC);
    }

    private void bind() {
        otp1 = findViewById(R.id.et_otp1);
        otp2 = findViewById(R.id.et_otp2);
        otp3 = findViewById(R.id.et_otp3);
        otp4 = findViewById(R.id.et_otp4);
        otp5 = findViewById(R.id.et_otp5);
        otp6 = findViewById(R.id.et_otp6);


        btnVerify = findViewById(R.id.btn_verify);

        mobileNumber = findViewById(R.id.tv_mobileNumber);

    }


    public void otpVerification() {
        try {
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

            VerifyOtpBioMetricVerificationPostParams verifyOtpBioMetricVerificationPostParams = new VerifyOtpBioMetricVerificationPostParams();

            verifyOtpBioMetricVerificationPostParams.getData().setOtp(encrypt(otp));
            verifyOtpBioMetricVerificationPostParams.getData().setRdaCustomerProfileId("" + res.getData().getEntityId());

            otpVerificationViewModel.postOtp(verifyOtpBioMetricVerificationPostParams, res.getData().getAccessToken(), this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public void cancelActivity(View view) {
        Intent intent = new Intent(this, CNIC_Availability.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public void sendOtp(View view) {
        try {
            otpVerificationViewModel.postCNIC(bioMetricVerificationPostParams, this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void messageFunc(View view) {
        showAlert("Sorry, currently the function is not responsive !!!");
    }

    public void powerSettingFunc(View view) {
        showAlert("Sorry, currently the function is not responsive !!!");
    }

    private String encrypt(String value) {

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
        String data = android.util.Base64.encodeToString(encrypted, Base64.NO_WRAP);
        System.out.println(data);
        return data;
    }


    public void showAlert(String msg) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(OtpVerificationActivity.this);
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
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            return false;
        }
        return true;
    }

//    private void requestPermission() {
//        if (ContextCompat.checkSelfPermission(OtpVerificationActivity.this, Manifest.permission.RECEIVE_SMS)
//                != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(OtpVerificationActivity.this,new String[]{
//                    Manifest.permission.RECEIVE_SMS
//            },100);
//        }
//    }


}