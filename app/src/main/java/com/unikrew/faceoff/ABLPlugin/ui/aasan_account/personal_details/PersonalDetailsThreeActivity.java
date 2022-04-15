package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivityPersonalDetailsThreeBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkResponseData;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;
import java.util.List;

public class PersonalDetailsThreeActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private ActivityPersonalDetailsThreeBinding personalDetailsThreeBinding;
    private PersonalDetailsViewModel personalDetailsViewModel;
    private ArrayList<MobileNetworkResponseData> occupationArray, professionArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setViewModel();
        getDropDownData();
        setObservers();
        clicks();
    }

    private void clicks() {
        personalDetailsThreeBinding.layoutBtn.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        personalDetailsThreeBinding.layoutBtn.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidations();
            }
        });
    }

    private void checkValidations() {
        if(isEmpty(personalDetailsThreeBinding.etSalary)){
            showAlert(Config.errorType, getString(R.string.text_fields_error));
        }else {
            postData();
        }
    }

    private void postData() {
    }

    private void setObservers() {
        personalDetailsViewModel.professionLiveData.observe(this, new Observer<MobileNetworkResponse>() {
            @Override
            public void onChanged(MobileNetworkResponse mobileNetworkResponse) {
                dismissLoading();
                setProfessionSpinner(mobileNetworkResponse);
            }
        });

        personalDetailsViewModel.occupationLiveData.observe(this, new Observer<MobileNetworkResponse>() {
            @Override
            public void onChanged(MobileNetworkResponse mobileNetworkResponse) {
                dismissLoading();
                setOccupationSpinner(mobileNetworkResponse);
            }
        });
    }

    private void setOccupationSpinner(MobileNetworkResponse mobileNetworkResponse) {
        occupationArray = mobileNetworkResponse.getData();
        setSpinner(occupationArray, personalDetailsThreeBinding.spinnerOccupation);
    }

    private void setProfessionSpinner(MobileNetworkResponse mobileNetworkResponse) {
        professionArray = mobileNetworkResponse.getData();
        setSpinner(professionArray, personalDetailsThreeBinding.spinnerProfession);
    }


    private void setSpinner(ArrayList<MobileNetworkResponseData> dataArrayList, Spinner spinner) {
        List<String> _allItemsArray = new ArrayList<>();
        if (dataArrayList.size() > 0) {
            // Spinner click listener
            spinner.setOnItemSelectedListener(this);
            // Spinner Drop down elements
            for (int i = 0; i < dataArrayList.size(); i++) {
                _allItemsArray.add(dataArrayList.get(i).getName());
            }

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, _allItemsArray);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);
        }
    }


    private void setBinding() {
        personalDetailsThreeBinding = ActivityPersonalDetailsThreeBinding.inflate(getLayoutInflater());
        setContentView(personalDetailsThreeBinding.getRoot());
    }

    private void setViewModel() {
        personalDetailsViewModel = new ViewModelProvider(this).get(PersonalDetailsViewModel.class);
    }

    private void getDropDownData() {
        showLoading();
        getOccupation();
        getProfession();
    }

    private void getProfession() {
        MobileNetworkPostParams mobileNetworkPostParams = new MobileNetworkPostParams();
        mobileNetworkPostParams.getData().codeTypeId = Config.PROFESSION_CODE;
        personalDetailsViewModel.getProfession(mobileNetworkPostParams);
    }


    private void getOccupation() {
        MobileNetworkPostParams mobileNetworkPostParams = new MobileNetworkPostParams();
        mobileNetworkPostParams.getData().codeTypeId = Config.OCCUPATION_CODE;
        personalDetailsViewModel.getOccupation(mobileNetworkPostParams);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}