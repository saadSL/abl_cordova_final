package com.unikrew.faceoff.ABLPlugin.ui.mobile_number;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.phase2.view_apps_generate_otp.ViewAppsGenerateOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.phase2.view_apps_generate_otp.ViewAppsGenerateOtpResponse;
import com.unikrew.faceoff.ABLPlugin.ui.cnic_upload.CnicUploadActivity;
import com.unikrew.faceoff.ABLPlugin.ui.otp_phase2.OtpVerification;
import com.unikrew.faceoff.Config;

public class MobileNumberActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    /* variables  */
    private EditText mobileNum;
    private EditText cnicNumber;
    private SwitchCompat portedNetworkSwitch;

    private Button btnMobileNext;
    private Button btnMobileCancel;



    MobileNumberViewModel viewModel;
    ViewAppsGenerateOtpPostParams postParams;

    private Boolean isPortedMobileNetwork = false;
    private Boolean generateOtp = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_number_availability);

        bind();
        set();
        setViewModel();
        observeData();
    }



    private void observeData() {
        viewModel.responseLiveData.observe(this, new Observer<ViewAppsGenerateOtpResponse>() {
            @Override
            public void onChanged(ViewAppsGenerateOtpResponse viewAppsGenerateOtpResponse) {
                if ( viewAppsGenerateOtpResponse.getData().isAlreadyExist() ){
                    if (generateOtp){
                        openOtpVerificationActivity();
                    }else{
                        showCnic(viewAppsGenerateOtpResponse);
                    }
                }else{
                    openCnicUploadActivity();
                }
                loader.dismiss();
            }
        });

        viewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType,errMsg);
                loader.dismiss();
            }
        });

    }

    private void openOtpVerificationActivity() {
        Intent intent = new Intent(this, OtpVerification.class);

        intent.putExtra(Config.MOBILE_NUMBER,mobileNum.getText().toString());
        intent.putExtra(Config.CNIC_NUMBER,cnicNumber.getText().toString());

        startActivity(intent);
    }

    /* The method below will work when mobile number is not registered with cnic. */
    private void openCnicUploadActivity() {
            Intent intent = new Intent(this, CnicUploadActivity.class);

            intent.putExtra(Config.MOBILE_NUMBER,mobileNum.getText().toString());
            intent.putExtra(Config.PORTED_MOBILE_NETWORK,isPortedMobileNetwork);

            startActivity(intent);
    }

    /* The method below will work when mobile number is already registered with cnic. */
    private void showCnic(ViewAppsGenerateOtpResponse response) {
        findViewById(R.id.ll_cnic).setVisibility(View.VISIBLE);

        cnicNumber.setText(response.getData().getIdNumber().toString());
        generateOtp = true;

    }


    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(MobileNumberViewModel.class);
        postParams = new ViewAppsGenerateOtpPostParams();
    }

    private void bind() {

        /* Mobile Number availability  */
        mobileNum = findViewById(R.id.et_mobile_num);
        cnicNumber = findViewById(R.id.et_cnicNumber);
        portedNetworkSwitch = findViewById(R.id.ported_mobile_network_switch);
        btnMobileNext = findViewById(R.id.btn_next);
        btnMobileCancel = findViewById(R.id.btn_cancel);
    }

    private void set() {
        /* Mobile Number Availability */
        portedNetworkSwitch.setOnCheckedChangeListener(this);

        btnMobileNext.setOnClickListener(this);
        btnMobileCancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                if (!validateMobileNumActivity()){
                    return;
                }else{
                    setPostParams();
                    viewModel.viewAppsGenerateOtpPostData(postParams);
                    showLoading();
                    loader.show();
                }
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    private void viewAppsGenerateOtpPostData() {

    }

    private void setPostParams() {
        postParams.getData().setCustomerTypeId(Config.CUSTOMER_TYPE_ID);
        postParams.getData().setMobileNo(mobileNum.getText().toString());
        postParams.getData().setGenerateOtp(generateOtp);

        if ( generateOtp ){
            postParams.getData().setIdNumber(cnicNumber.getText().toString());
            postParams.getData().setPortedMobileNetwork(isPortedMobileNetwork);
        }

    }

    private Boolean validateMobileNumActivity() {
        if ( isEmpty( mobileNum ) ){

            showAlert(Config.errorType,"Mobile Number Is Empty !!!");
            return false;

        }else if( mobileNum.getText().toString().length() < Config.MOBILE_NUMBER_LENGTH){

            showAlert(Config.errorType,"Mobile Number Length Not Valid !!!");
            return false;

        }else if( generateOtp && isEmpty(cnicNumber) ){

            showAlert(Config.errorType,"CNIC Number Is Empty !!!");
            return false;

        }else if ( generateOtp && cnicNumber.getText().toString().length() < Config.CNIC_LENGTH ){

            showAlert(Config.errorType,"CNIC Number Length Not Valid !!!");
            return false;

        }else if ( !wifiAvailable(this) ){

            showAlert(Config.errorType,"Network Is Not Available !!!");
            return false;

        }else{
            return true;
        }
    }



    /* Checking Ported network to true or false.  */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked){
            showMobileInfoDialogue();
        }else{
            isPortedMobileNetwork = false;
        }
    }

    private void showMobileInfoDialogue() {
        AlertDialog alert;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogueView = inflater.inflate(R.layout.mobile_info_dialogue,null);
        builder.setView(dialogueView);
        builder.setCancelable(true);
        Button btnDone = (Button) dialogueView.findViewById(R.id.btn_done_mobile_info);
        alert = builder.create();
        alert.show();
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPortedMobileNetwork = true;
                alert.dismiss();
            }
        });
    }
}




/* All Dumps that is not needed right now. */

//    private EditText cnicNum;
//    private Spinner mobileNetwork;
//    private Spinner spinner;

//    private Boolean isPortedMobileNetwork = false;

//    public String mobileNetworkId = "";

//    public List<MobileNetworkResponseData> networkList = new ArrayList<>();
//        postDataForMobileNetwork();



//    private void setNetworkList() {
//        ArrayAdapter<MobileNetworkResponseData> arrayAdapter = new ArrayAdapter<MobileNetworkResponseData>(
//                this,
//                android.R.layout.simple_spinner_item,
//                networkList){
//            @Override
//            public boolean isEnabled(int position) {
//                if (position == 0){
//                    return false;
//                }else{
//                    return true;
//                }
//            }
//
//            @Override
//            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                View view = super.getDropDownView(position, convertView, parent);
//                TextView tv = (TextView) view;
//                if (position == 0){
//                    tv.setTextColor(Color.GRAY);
//                }else{
//                    tv.setTextColor(Color.BLACK);
//                }
//                return view;
//            }
//        };
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(arrayAdapter);
//        spinner.setOnItemSelectedListener(this);
//    }


//        viewModel.mobileNetworkResponse.observe(this, new Observer<MobileNetworkResponse>() {
//            @Override
//            public void onChanged(MobileNetworkResponse mobileNetworkResponse) {
//
////                MobileNetworkResponseData mobileNetworkResponseData = new MobileNetworkResponseData();
////                mobileNetworkResponseData.setId("0");
////                mobileNetworkResponseData.setName("Select Your Mobile Network");
////                mobileNetworkResponseData.setDescription("");
//
////                networkList.add(mobileNetworkResponseData);
////                for ( MobileNetworkResponseData network: mobileNetworkResponse.getData() ) {
////                    networkList.add(network);
////                }
//
////                setNetworkList();
//            }
//        });
//
//        viewModel.mobileNetworkErrorLiveData.observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                showAlert(Config.errorType,s);
//            }
//        });

//    private void postDataForMobileNetwork() {
//        mobileNetworkPostParams.getData().setCodeTypeId(Config.codeTypeId);
//        viewModel.postDataForMobileNetwork(mobileNetworkPostParams,this);
//    }

//        mobileNetwork = findViewById(R.id.mobile_network);

//        cnicNum = findViewById(R.id.cnic_num);

//        spinner = findViewById(R.id.mobile_network);


//        else if( isEmpty( cnicNum )){
//
//            showAlert(Config.errorType,"CNIC Is Empty !!!");
//
//        }else if ( cnicNum.getText().toString().length() < Config.CNIC_LENGTH){
//
//            showAlert(Config.errorType,"CNIC Length Is Not Valid !!!");
//
//        }
//        else if ( mobileNetwork.getSelectedItemPosition() == 0){
//
//            showAlert(Config.errorType,"Mobile Network Can Not Be Empty !!!");
//
//        }

//            intent.putExtra(Config.CNIC_NUMBER,cnicNum.getText().toString());
//            intent.putExtra(Config.MOBILE_NETWORK,mobileNetworkId);


//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        MobileNetworkResponseData network = (MobileNetworkResponseData) adapterView.getSelectedItem();
//        if (i == 0){
//            TextView tv = (TextView) view;
//            tv.setTextColor(Color.GRAY);
//        }else if (i > 0){
////            mobileNetworkId = network.getId();
////            showAlert(Config.successType,"Id Of Network : "+mobileNetworkId);
//        }
//
//    }

//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//        return;
//    }