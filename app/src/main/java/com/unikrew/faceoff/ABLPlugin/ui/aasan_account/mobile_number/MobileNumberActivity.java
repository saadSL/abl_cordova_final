package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.mobile_number;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.MobileNumberAvailabilityBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpResponse;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.cnic_upload.CnicUploadActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.otp_phase2.OtpVerification;
import com.unikrew.faceoff.Config;

public class MobileNumberActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    /* variables  */
    private MobileNumberViewModel viewModel;
    private ViewAppsGenerateOtpPostParams postParams;

    private Boolean isPortedMobileNetwork = false;
    private Boolean generateOtp = false;

    private MobileNumberAvailabilityBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        set();
        setViewModel();
        observeData();
    }

    private void setBinding() {
        binding = MobileNumberAvailabilityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


    private void observeData() {
        viewModel.responseLiveData.observe(this, new Observer<ViewAppsGenerateOtpResponse>() {
            @Override
            public void onChanged(ViewAppsGenerateOtpResponse viewAppsGenerateOtpResponse) {
                if (viewAppsGenerateOtpResponse.getData().isAlreadyExist()) {
                    if (postParams.getData().isGenerateOtp()) {
                        openOtpActivity();
                    } else {
                        showCnic(viewAppsGenerateOtpResponse);
                    }
                } else {
                    openCnicUploadActivity();
                }
            }
        });

        viewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showAlert(Config.errorType, s);
            }
        });
    }

    private void openOtpActivity() {
        Intent intent = new Intent(this, OtpVerification.class);

        intent.putExtra(Config.MOBILE_NUMBER, binding.etMobileNum.getText().toString());
        intent.putExtra(Config.CNIC_NUMBER, binding.etCnicNumber.getText().toString());

        startActivity(intent);
    }

    /* The method below will work when mobile number is not registered with cnic. */
    private void openCnicUploadActivity() {
        Intent intent = new Intent(this, CnicUploadActivity.class);

        intent.putExtra(Config.MOBILE_NUMBER, binding.etMobileNum.getText().toString());
        intent.putExtra(Config.PORTED_MOBILE_NETWORK, isPortedMobileNetwork);

        startActivity(intent);
    }

    /* The method below will work when mobile number is already registered with cnic. */
    private void showCnic(ViewAppsGenerateOtpResponse response) {
        binding.llCnic.setVisibility(View.VISIBLE);

        binding.etCnicNumber.setText(response.getData().getIdNumber().toString());
        generateOtp = true;

    }


    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(MobileNumberViewModel.class);
        postParams = new ViewAppsGenerateOtpPostParams();
    }


    private void set() {
        /* Mobile Number Availability */
      binding.portedMobileNetworkSwitch.setOnCheckedChangeListener(this);

        binding.btnContainer.btnNext.setOnClickListener(this);
        binding.btnContainer.btnCancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                viewAppsGenerateOtpPostData();
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    private void viewAppsGenerateOtpPostData() {
        if (!verifyMobileNum()) {
            return;
        } else {
            setPostParams();
        }
    }

    private void setPostParams() {
        postParams.getData().setCustomerTypeId(Config.customerTypeId);
        postParams.getData().setMobileNo(binding.etMobileNum.getText().toString());
        postParams.getData().setGenerateOtp(generateOtp);
        postParams.getData().setIdNumber(binding.etCnicNumber.getText().toString());
        postParams.getData().setPortedMobileNetwork(isPortedMobileNetwork);
        viewModel.viewAppsGenerateOtpPostData(postParams, this);
    }

    private Boolean verifyMobileNum() {
        if (isEmpty(binding.etMobileNum)) {

            showAlert(Config.errorType, "Mobile Number Is Empty !!!");
            return false;

        } else if (binding.etMobileNum.getText().toString().length() < Config.mobileNumberLength) {

            showAlert(Config.errorType, "Mobile Number Length Not Valid");
            return false;

        } else if (!wifiAvailable(this)) {

            showAlert(Config.errorType, "Network Is Not Available");
            return false;

        } else {

            return true;

        }
    }


    /* Checking Ported network to true or false.  */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            showMobileInfoDialogue();
        } else {
            isPortedMobileNetwork = false;
        }
    }

    private void showMobileInfoDialogue() {
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