package com.unikrew.faceoff.ABLPlugin.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.Config;

public class BaseActivity extends AppCompatActivity {

    public MutableLiveData<Boolean> otp6LiveData = new MutableLiveData<Boolean>();
    public AlertDialog loader;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLoader();
    }

    private void setLoader() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setView(View.inflate(this, R.layout.loader, null));
        builder1.setCancelable(false);
        loader = builder1.create();
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public boolean wifiAvailable() {
        ConnectivityManager conMgr = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            return false;
        }
        return true;
    }


    public Boolean isEmpty(EditText et) {
        if (et.getText().toString().equals("")) {
            return true;
        }
        return false;
    }


    public void showAlert(int type, String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

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
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("SUCCESS");
            spannableStringBuilder.setSpan(
                    foregroundColorSpan,
                    0,
                    "SUCCESS".length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            builder1.setTitle(spannableStringBuilder);

        } else if (type == Config.verifiedType) {
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
        builder1.setCancelable(false);

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


    public void setAutoFocusForOtp(EditText otp1, EditText otp2, EditText otp3, EditText otp4, EditText otp5, EditText otp6) {
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
                String text = editable.toString();

                if (text.length() == 1) {
                    otp6LiveData.postValue(true);
                }
            }
        });
    }

    public void showLoading() {
        loader.show();
    }

    public void showMobileInfoDialogue() {
        AlertDialog alert;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogueView = inflater.inflate(R.layout.mobile_info_dialogue, null);
        builder.setView(dialogueView);
        builder.setCancelable(true);
        Button btnDone = (Button) dialogueView.findViewById(R.id.btn_done_mobile_info);
        alert = builder.create();
        alert.show();
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });
    }

    public void dismissLoading() {
        loader.dismiss();
    }


}
