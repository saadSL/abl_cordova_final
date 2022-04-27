package com.unikrew.faceoff.ABLPlugin.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.Config;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BaseFragment extends Fragment {
    public MutableLiveData<Boolean> otp6LiveData = new MutableLiveData<Boolean>();
    private AlertDialog loader;
    private SharedPreferences.Editor editor;
    public Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLoader();
        setSharedPref();
    }

    public void setLogoLayout(TextView tvDate) {
        tvDate.setText(getCurrentDate());
    }

    private void setSharedPref() {
        editor = mContext.getSharedPreferences(Config.ASAAN_ACCOUNT_PREF, mContext.MODE_PRIVATE).edit();
    }

    protected void saveStringInPref(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }


    protected void saveSerializableInPref(String key, Serializable value) {
        Gson gson = new Gson();
        String json = gson.toJson(value);
        editor.putString(key, json);
        editor.apply();
    }

    protected Serializable getSerializableFromPref(String key, Class<? extends Serializable> className) {

        SharedPreferences prefs = mContext.getSharedPreferences(Config.ASAAN_ACCOUNT_PREF, mContext.MODE_PRIVATE);

        Gson gson = new Gson();

        String json = prefs.getString(key, "");

        return gson.fromJson(json, className);
    }

    protected String getStringFromPref(String key) {
        SharedPreferences prefs = mContext.getSharedPreferences(Config.ASAAN_ACCOUNT_PREF, mContext.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    private void setLoader() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
        builder1.setView(View.inflate(mContext, R.layout.loader, null));
        builder1.setCancelable(false);
        loader = builder1.create();
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public boolean wifiAvailable() {
        ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
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
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);

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
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

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

    public String getCurrentDate() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int dayName = calendar.get(Calendar.DAY_OF_WEEK);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        String[] days = {"", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        String[] months = new String[12];
        months[0] = "January";
        months[1] = "February";
        months[2] = "March";
        months[3] = "April";
        months[4] = "May";
        months[5] = "June";
        months[6] = "July";
        months[7] = "August";
        months[8] = "September";
        months[9] = "October";
        months[10] = "November";
        months[11] = "December";
        return days[dayName - 1] + "," + months[month] + " " + day + ", " + year;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}
