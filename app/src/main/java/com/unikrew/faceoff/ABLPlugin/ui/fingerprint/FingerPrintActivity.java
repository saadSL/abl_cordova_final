package com.unikrew.faceoff.ABLPlugin.ui.fingerprint;

import static android.os.Build.VERSION_CODES.M;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.unikrew.faceoff.ABLPlugin.CNIC_Availability;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationNadraPostParams;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationNadraResponse;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationResponse;
import com.unikrew.faceoff.ABLPlugin.model.FingerprintsItem;
import com.unikrew.faceoff.ABLPlugin.ui.ViewFingerprintActivity;

import com.unikrew.faceoff.Config;
import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.fingerprint.Customization.CustomUI;
import com.unikrew.faceoff.fingerprint.FingerprintConfig;
import com.unikrew.faceoff.fingerprint.FingerprintHelpers.Png;
import com.unikrew.faceoff.fingerprint.FingerprintResponse;
import com.unikrew.faceoff.fingerprint.FingerprintScannerActivity;
import com.unikrew.faceoff.fingerprint.LivenessNotSupportedException;
import com.unikrew.faceoff.fingerprint.NadraConfig;
import com.unikrew.faceoff.fingerprint.ResultIPC;

import java.util.ArrayList;
import java.util.List;

public class FingerPrintActivity extends AppCompatActivity {

    private Button btSubmit, btCancel;
    private ImageView ivFingerPrint, ivBack;
    private LinearLayout liSuccess;

    private String status = "0";
    private FingerPrintViewModel fingerPrintViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print);
        setViewModel();
        bindViews();
        setClicks();
        requestExternalStoragePermission();
        observeData();
    }

    private void observeData() {
        fingerPrintViewModel.BioMetricStatusSuccessLiveData.observe(this, new Observer<BioMetricVerificationNadraResponse>() {
            @Override
            public void onChanged(BioMetricVerificationNadraResponse bioMetricVerificationNadraResponse) {
                String code = bioMetricVerificationNadraResponse.getData().getResponseCode();
                if (code.equals("100")) {
                    submitFingerPrint();
                } else {
                    showAlert(Config.errorType, bioMetricVerificationNadraResponse.getData().getResponseDescription());
//                                    showAlert(Config.errorType,bioMetricVerificationNadraResponse.getData().getResponseMsg());
                }
            }
        });

        fingerPrintViewModel.BioMetricStatusErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMsg) {
                showAlert(Config.errorType, errorMsg);
            }
        });
    }

    private void setViewModel() {
        fingerPrintViewModel = new ViewModelProvider(this).get(FingerPrintViewModel.class);
    }

    private void requestExternalStoragePermission() {
        if (android.os.Build.VERSION.SDK_INT >= M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Config.EXTERNAL_STORAGE_CODE);
            }
        }
    }

    private void setClicks() {
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                submitFingerPrint();
                launchScanning(null, null, null, null, null, FingerprintConfig.Mode.EXPORT_WSQ);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCnicScreen();
            }
        });
    }

    private void goToCnicScreen() {
        Intent intent = new Intent(this, CNIC_Availability.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void fingerPrintSuccess() {
        ivFingerPrint.setVisibility(View.GONE);
        liSuccess.setVisibility(View.VISIBLE);
        btSubmit.setText("Done");
    }

    private void bindViews() {
        ivBack = findViewById(R.id.iv_back);
        btSubmit = findViewById(R.id.bt_submit);
        btCancel = findViewById(R.id.bt_cancel);
        ivFingerPrint = findViewById(R.id.iv_finger_print);
        liSuccess = findViewById(R.id.li_success);
    }

    private void launchScanning(String name, String username, String password,
                                String cnicNumber, NadraConfig nadraConfig, FingerprintConfig.Mode mode) {
        try {

            CustomUI customUI = new CustomUI()
                    .setShowGuidanceScreen(true);

            // Build FingerprintConfig, required by Fingerprint SDK
            // Fingerprint Config is used to customize the UI and fingerprint scanning options
            // See its usage in 'SettingsActivity' for details
            FingerprintConfig.Builder builder = new FingerprintConfig.Builder()
                    .setFingers(FingerprintConfig.Fingers.EIGHT_FINGERS)
                    .setMode(mode)
                    .setLiveness(true)
                    .setPackPng(true)
                    .setCustomUI(customUI);

            if (nadraConfig != null) {
                builder.setNadraConfig(nadraConfig);
            }

            FingerprintConfig fingerprintConfig = builder.build();

            // Setting intent data and launching scanner activity
            Intent intent = new Intent(FingerPrintActivity.this, FingerprintScannerActivity.class);
            if (mode == FingerprintConfig.Mode.ENROLL) {
                intent.putExtra(FingerprintScannerActivity.NAME_FOR_FINGERPRINT, name);
                intent.putExtra(FingerprintScannerActivity.CNIC_FOR_FINGERPRINT, cnicNumber);
            }
            if (mode == FingerprintConfig.Mode.NADRA) {
                intent.putExtra(FingerprintScannerActivity.USERNAME, username);
                intent.putExtra(FingerprintScannerActivity.PASSWORD, password);
                intent.putExtra(FingerprintScannerActivity.CNIC_FOR_FINGERPRINT, cnicNumber);
            }
            intent.putExtra(FingerprintScannerActivity.FACEOFF_FINGERPRINT_CONFIG, fingerprintConfig);
            startActivityForResult(intent, Config.REQ_SCAN_FINGERPRINT);

        } catch (LivenessNotSupportedException e) {
            Toast.makeText(FingerPrintActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Config.EXTERNAL_STORAGE_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) { // Camera permission not granted
                showPermissionDialog();
            }
        }
    }

    private void showPermissionDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);

        ((TextView) dialog.findViewById(R.id.titleTV)).setText(getString(R.string.error));
        ((TextView) dialog.findViewById(R.id.msgTV)).setText(getString(R.string.please_grant_external_storage_permissions));

        dialog.setCanceledOnTouchOutside(false);

        Button dialogButton = dialog.findViewById(R.id.okButton);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestExternalStoragePermission();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.REQ_SCAN_FINGERPRINT & data != null) {
            // Getting fingerprintResponse from intent
            int responseCode = data.getIntExtra(FingerprintScannerActivity.FINGERPRINT_RESPONSE_CODE, -1);
            if (responseCode > 0) {
                FingerprintResponse fingerprintResponse = ResultIPC.getInstance().getFingerprintResponse(responseCode);
                if (fingerprintResponse != null) {
                    if (resultCode == FingerprintResponse.Response.SUCCESS_WSQ_EXPORT.getResponseCode()
                            || resultCode == FingerprintResponse.Response.SUCCESS_PNG_EXPORT.getResponseCode()) {
//                        showFingerprints(responseCode);
                        postFingerPrints(fingerprintResponse);
                    }

                    // If unsuccessful, toast error message
                    else {
                        toastMessage(fingerprintResponse.getResponse().getResponseMessage());
                    }
                } else {
                    toastMessage("Fingerprint Response is null!");
                }
            } else {
                toastMessage("Could not receive response!");
            }
        }
    }

    private void postFingerPrints(FingerprintResponse fingerprintResponse) {
        List<FingerprintsItem> fingerprints = new ArrayList<FingerprintsItem>();
        BioMetricVerificationNadraPostParams pp = new BioMetricVerificationNadraPostParams();
        for (Png png : fingerprintResponse.getPngList()) {

            String binaryBase64ObjectPNG = png.getBinaryBase64ObjectPNG();
            int indexCode = png.getFingerPositionCode();

            FingerprintsItem fingerprintsItem = new FingerprintsItem();
            fingerprintsItem.setIndex(Long.valueOf(indexCode));
            fingerprintsItem.setTemplate(binaryBase64ObjectPNG);
            fingerprints.add(fingerprintsItem);
        }


        BioMetricVerificationResponse res = (BioMetricVerificationResponse) getIntent().getSerializableExtra(Config.RESPONSE);

        pp.getData().setRdaCustomerProfileId(Integer.toString(res.getData().getEntityId()));
        pp.getData().setRdaCustomerAccountInfoId(Integer.toString(res.getData().getAccountInfoId()));
        pp.getData().setCnic(res.getData().getUsername());
        pp.getData().setFingerprints(fingerprints);
        pp.getData().setTemplateType(Config.templateType);
        pp.getData().setContactNumber(res.getData().getMobileNo());
        pp.getData().setAreaName(res.getData().getArea());
        pp.getData().setAccountType(res.getData().getAccountType());

        Log.d("finalData", pp.getData().toString());
        fingerPrintViewModel.updateBioMetricStatus(pp, res.getData().getAccessToken(), FingerPrintActivity.this);
    }

    private void submitFingerPrint() {
        ivFingerPrint.setVisibility(View.GONE);
        liSuccess.setVisibility(View.VISIBLE);
        btSubmit.setText("Done");
    }

    private void showFingerprints(int responseCode) {
        Intent intent = new Intent(this, ViewFingerprintActivity.class);
        intent.putExtra(Config.KEY_RESPONSE_CODE, responseCode);
        startActivity(intent);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showNadraResults(int responseCode) {
//        // Send 'nadraResponse' to 'NadraActivity' and launch it to view results
//        Intent intent = new Intent(this, NadraActivity.class);
//        intent.putExtra(Config.KEY_RESPONSE_CODE, responseCode);
//        startActivity(intent);
    }

    private void showIdentificationResults(int responseCode) {
//        Intent intent = new Intent(this, IdentificationResultsActivity.class);
//        intent.putExtra(Constants.KEY_RESPONSE_CODE, responseCode);
//        startActivity(intent);
    }


    public void showAlert(int type, String msg) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(FingerPrintActivity.this);
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

    public void changeStatus(View view) {
        if (status.equals("0")) {
            status = "1";
        } else {
            status = "0";
        }
    }
}