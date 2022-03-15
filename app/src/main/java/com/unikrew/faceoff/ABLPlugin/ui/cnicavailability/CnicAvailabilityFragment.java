package com.unikrew.faceoff.ABLPlugin.ui.cnicavailability;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.ABLPlugin.CNIC_Availability;
import com.unikrew.faceoff.ABLPlugin.model.CnicPostParams;
import com.unikrew.faceoff.ABLPlugin.model.ResponseDTO;
import com.unikrew.faceoff.ABLPlugin.ui.otpverification.OtpVerificationFragment;
import com.unikrew.faceoff.Config;

public class CnicAvailabilityFragment extends Fragment {

    private Button btnNext, btnCancel;
    private EditText etAccNumber;
    private EditText etCnicNumber;
    private CnicAvailabilityViewModel vm;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cnic_availability, container, false);

        vm = new ViewModelProvider(this).get(CnicAvailabilityViewModel.class);
        bindViews(view);
        setClicks();

        return view;
    }

    private void setClicks() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCustomerDetail();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelActivity();
            }
        });
    }

    private void bindViews(View view) {
        etAccNumber = view.findViewById(R.id.et_accNumber);
        etCnicNumber = view.findViewById(R.id.et_cnicNumber);
        btnNext = view.findViewById(R.id.btn_Next);
        btnCancel = view.findViewById(R.id.btn_Cancel);
    }


    public void postCustomerDetail() {


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

        try {
            CnicPostParams cnicPostParams = new CnicPostParams();

            cnicPostParams.getData().setCnic(etCnicNumber.getText().toString());
            cnicPostParams.getData().setAccountNo(etAccNumber.getText().toString());


            vm.postCNIC(cnicPostParams, (Activity) mContext);


            vm.CnicVerifiedLiveData.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    showAlert(Config.successType, s);
                }
            });

            vm.CnicSuccessLiveData.observe(this, new Observer<ResponseDTO>() {
                @Override
                public void onChanged(ResponseDTO responseDTO) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Config.RESPONSE, responseDTO);
                    bundle.putSerializable(Config.CNIC_ACC, cnicPostParams);
                    ((CNIC_Availability) mContext).replaceOtpFragment(bundle);
                    clearFields();
                }
            });

            vm.CnicErrorLiveData.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String responseMsg) {
                    showAlert(Config.errorType, responseMsg);
                }
            });
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

    public void cancelActivity() {
//        finish();
    }

    public void showAlert(int type, String msg) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
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
        ConnectivityManager conMgr = (ConnectivityManager) mContext.getApplicationContext().getSystemService(mContext.getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}
