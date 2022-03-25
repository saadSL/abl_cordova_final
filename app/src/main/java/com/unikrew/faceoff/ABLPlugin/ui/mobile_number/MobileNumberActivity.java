package com.unikrew.faceoff.ABLPlugin.ui.mobile_number;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.ABLPlugin.BaseClass;
import com.unikrew.faceoff.ABLPlugin.ui.cnic_upload.CnicUploadActivity;
import com.unikrew.faceoff.Config;

public class MobileNumberActivity extends BaseClass implements View.OnClickListener{

    /* variables  */
    private EditText mobileNum;
    private EditText cnicNum;
    private Spinner mobileNetwork;
    private Button btnMobileNext;
    private Button btnMobileCancel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_number_availability);

        bind();
        set();
    }

    private void bind() {

        /* Mobile Number availability  */
        mobileNum = findViewById(R.id.et_mobile_num);
        mobileNetwork = findViewById(R.id.mobile_network);
        btnMobileNext = findViewById(R.id.btn_next);
        btnMobileCancel = findViewById(R.id.btn_cancel);
        cnicNum = findViewById(R.id.cnic_num);

    }

    private void set() {
        /* Mobile Number Availability */
        btnMobileNext.setOnClickListener(this);
        btnMobileCancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                verifyMobileNum();
                break;
        }
    }

    private void verifyMobileNum() {
        if ( isEmpty( mobileNum ) ){

            showAlert(Config.errorType,"Mobile Number Is Empty !!!");

        }else if( mobileNum.getText().toString().length() < Config.mobileNumberLength){

            showAlert(Config.errorType,"Mobile Number Length Not Valid");

        }else if( isEmpty( cnicNum )){

            showAlert(Config.errorType,"CNIC Is Empty !!!");

        }else if ( cnicNum.getText().toString().length() < Config.CNIC_LENGTH){

            showAlert(Config.errorType,"CNIC Length Is Not Valid !!!");

        }else if ( mobileNetwork.getSelectedItem().toString().equalsIgnoreCase("select your mobile network") ){

            showAlert(Config.errorType,"Mobile Network Not Selected !!!");

        }else if ( !wifiAvailable(this) ){

            showAlert(Config.errorType,"Network Is Not Available");

        }else{

            Intent intent = new Intent(this, CnicUploadActivity.class);
            intent.putExtra(Config.MOBILE_NUMBER,mobileNum.getText().toString());
            intent.putExtra(Config.CNIC_NUMBER,cnicNum.getText().toString());
            intent.putExtra(Config.MOBILE_NETWORK,mobileNetwork.getSelectedItem().toString());
            startActivity(intent);
//            /* CNIC Upload */

        }
    }
}
