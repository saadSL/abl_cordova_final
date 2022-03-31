package com.unikrew.faceoff.ABLPlugin;

import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationResponse;
import com.unikrew.faceoff.ABLPlugin.ui.otp.OtpVerificationActivity;
import com.unikrew.faceoff.Config;

import com.ofss.digx.mobile.android.allied.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CNIC_Availability extends AppCompatActivity {

    private EditText etAccNumber;
    private EditText etCnicNumber;
    private CnicAvailabilityViewModel cnicAvailabilityViewModel;
    private BioMetricVerificationPostParams bioMetricVerificationPostParams;
    private Button btnNext, btCancel;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cnic_availability);
//        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        String countryCode = tm.getSimCountryIso();
//        Toast.makeText(this, countryCode, Toast.LENGTH_SHORT).show();
        setViewModel();
        bindViews();
        observeData();
        clicks();
    }

    private void clicks() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCustomerDetail();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void observeData() {
        cnicAvailabilityViewModel.CnicVerifiedLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showAlert(Config.successType, s);
            }
        });

        cnicAvailabilityViewModel.CnicSuccessLiveData.observe(this, new Observer<BioMetricVerificationResponse>() {
            @Override
            public void onChanged(BioMetricVerificationResponse bioMetricVerificationResponse) {
                Intent i = new Intent(CNIC_Availability.this, OtpVerificationActivity.class);
                i.putExtra(Config.RESPONSE, bioMetricVerificationResponse);
                i.putExtra(Config.CNIC_ACC, bioMetricVerificationPostParams);
                startActivity(i);
                clearFields();
            }
        });

        cnicAvailabilityViewModel.CnicErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String responseMsg) {
                showAlert(Config.errorType, responseMsg);
            }
        });
    }

    private void bindViews() {
        etAccNumber = findViewById(R.id.et_accNumber);
        etCnicNumber = findViewById(R.id.et_cnicNumber);
        btnNext = findViewById(R.id.btn_Next);
        btCancel = findViewById(R.id.bt_cancel_cnic);
        ivBack = findViewById(R.id.iv_back_cnic);
    }

    private void setViewModel() {
        cnicAvailabilityViewModel = new ViewModelProvider(this).get(CnicAvailabilityViewModel.class);
        bioMetricVerificationPostParams = new BioMetricVerificationPostParams();
    }

    public void postCustomerDetail() {


        try {
            if (isEmpty(etAccNumber) ||
                    isEmpty(etCnicNumber)) {
                showAlert(Config.errorType, "Please fill all * fields");
                return;
            }
            if (etAccNumber.getText().length() < Config.ACCOUNT_LENGTH) {
                showAlert(Config.errorType, "Account Number Length is not valid");
                return;
            } else if (etCnicNumber.getText().length() < Config.CNIC_LENGTH) {
                showAlert(Config.errorType, "CNIC Length is not valid");
                return;
            } else if (!isOnline()) {
                showAlert(Config.errorType, "No Internet connection!");
                return;
            }
            bioMetricVerificationPostParams.getData().setCnic(etCnicNumber.getText().toString());
            bioMetricVerificationPostParams.getData().setAccountNo(etAccNumber.getText().toString());
            cnicAvailabilityViewModel.postCNIC(bioMetricVerificationPostParams, CNIC_Availability.this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        etAccNumber.setText("");
        etCnicNumber.setText("");
    }

    public Boolean isEmpty(EditText et) {
        if (et.getText().toString().equals("")) {
            return true;
        }
        return false;
    }

    public void showAlert(int type, String msg) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(CNIC_Availability.this);
        if (type == Config.errorType) {
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.BLACK);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("ERROR");
            spannableStringBuilder.setSpan(
                    foregroundColorSpan,
                    0,
                    "ERROR".length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            builder1.setTitle(spannableStringBuilder);
        } else if (type == Config.successType) {
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.BLACK);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("VERIFIED");
            spannableStringBuilder.setSpan(
                    foregroundColorSpan,
                    0,
                    "VERIFIED".length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            builder1.setTitle(spannableStringBuilder);

        }

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
}