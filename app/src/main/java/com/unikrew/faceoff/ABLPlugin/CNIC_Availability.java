package com.unikrew.faceoff.ABLPlugin;

import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationResponse;
import com.unikrew.faceoff.ABLPlugin.ui.nadra_sdk.otp.OtpVerificationActivity;
import com.unikrew.faceoff.Config;

import com.ofss.digx.mobile.android.allied.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;

public class CNIC_Availability extends AppCompatActivity {

    private EditText etAccNumber;
    private EditText etCnicNumber;
    private CnicAvailabilityViewModel cnicAvailabilityViewModel;
    private BioMetricVerificationPostParams bioMetricVerificationPostParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cnic_availability);
        setViewModel();
        bindViews();
        observeData();
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
    }

    private void setViewModel() {
        cnicAvailabilityViewModel = new ViewModelProvider(this).get(CnicAvailabilityViewModel.class);
        bioMetricVerificationPostParams = new BioMetricVerificationPostParams();
    }

    public void postCustomerDetail(View view) throws InterruptedException {


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
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("ERROR");
            spannableStringBuilder.setSpan(
                    foregroundColorSpan,
                    0,
                    "ERROR".length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            builder1.setTitle(spannableStringBuilder);
        } else if (type == Config.successType) {
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.GREEN);
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