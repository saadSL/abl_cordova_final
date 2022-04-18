package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.employment_details;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.EmploymentDetailsBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.occupation.OccupationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.occupation.OccupationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.occupation.OccupationResponseData;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.profession.ProfessionPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.profession.ProfessionResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.profession.ProfessionResponseData;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsPostConsumerList;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycPostData;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycResponse;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_transaction.SelectCardActivity;
import com.unikrew.faceoff.Config;

import java.util.ArrayList;

public class EmploymentDetailsActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EmploymentDetailsBinding employmentDetailsBinding;
    private EmploymentDetailsViewModel employmentDetailsViewModel;

    private OccupationPostParams occupationPostParams;
    private ProfessionPostParams professionPostParams;

    private OccupationResponseData selectedOccupation;
    private ProfessionResponseData selectedProfession;

    private RegisterEmploymentDetailsPostParams registerEmploymentDetailsPostParams;
    private RegisterEmploymentDetailsPostConsumerList registerEmploymentDetailsPostConsumerList;
    private GetConsumerAccountDetailsResponse consumerAccountDetailsResponse;
    private ArrayList<RegisterEmploymentDetailsPostConsumerList> consumerList;

    private SaveKycPostParams saveKycPostParams;

    private RegisterEmploymentDetailsResponse nextScreenResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setLayout();
        setViewModel();
        setClicks();
        getOccupation();
        getProfession();
        observe();
    }

    private void observe() {
        employmentDetailsViewModel.occupationResponseMutableLiveData.observe(this, new Observer<OccupationResponse>() {
            @Override
            public void onChanged(OccupationResponse occupationResponse) {
                setOccupationSpinner(occupationResponse);
                loader.dismiss();
            }
        });

        employmentDetailsViewModel.occupationErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType,errMsg);
                loader.dismiss();
            }
        });

        employmentDetailsViewModel.professionResponseMutableLiveData.observe(this, new Observer<ProfessionResponse>() {
            @Override
            public void onChanged(ProfessionResponse professionResponse) {
                setProfessionSpinner(professionResponse);
                loader.dismiss();
            }
        });

        employmentDetailsViewModel.professionErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType,errMsg);
                loader.dismiss();
            }
        });

        employmentDetailsViewModel.registerEmploymentDetailsResponseMutableLiveData.observe(this, new Observer<RegisterEmploymentDetailsResponse>() {
            @Override
            public void onChanged(RegisterEmploymentDetailsResponse registerEmploymentDetailsResponse) {
                nextScreenResponse = registerEmploymentDetailsResponse;
                saveKyc();
                loader.dismiss();
            }
        });


        employmentDetailsViewModel.registerEmployeeDetailsErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType,errMsg);
                loader.dismiss();
            }
        });

        employmentDetailsViewModel.saveKycResponseMutableLiveData.observe(this, new Observer<SaveKycResponse>() {
            @Override
            public void onChanged(SaveKycResponse saveKycResponse) {
                openCardSelectionActivity();
                loader.dismiss();
            }
        });

        employmentDetailsViewModel.saveKycResponseErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlert(Config.errorType,errMsg);
                loader.dismiss();
            }
        });
    }

    private void saveKyc() {
        setKycPostParams();
        employmentDetailsViewModel.saveKyc(saveKycPostParams,getStringFromPref(Config.ACCESS_TOKEN));
        showLoading();
    }

    private void setKycPostParams() {
        SaveKycPostData saveKycPostData = new SaveKycPostData();
        saveKycPostData.setRdaCustomerAccInfoId(consumerAccountDetailsResponse.getData().getConsumerList().get(0).getAccountInformation().getRdaCustomerAccInfoId());
        saveKycPostData.setRdaCustomerProfileId(consumerAccountDetailsResponse.getData().getConsumerList().get(0).getRdaCustomerProfileId());
        saveKycPostData.setAverageMonthlySalary(Integer.parseInt(employmentDetailsBinding.etSalary.getText().toString()));
        saveKycPostParams.getData().add(saveKycPostData);
    }

    private void openCardSelectionActivity() {
        Intent intent = new Intent(this, SelectCardActivity.class);
        intent.putExtra(Config.RESPONSE,nextScreenResponse);
        startActivity(intent);
    }

    private void setProfessionSpinner(ProfessionResponse professionResponse) {
        ArrayList<ProfessionResponseData> _allProfession = new ArrayList<>();
        ProfessionResponseData professionResponseData = new ProfessionResponseData();
        professionResponseData.setName("Choose Profession");
        professionResponseData.setId(0);
        professionResponseData.setDescription("");

        _allProfession.add(professionResponseData);
        employmentDetailsBinding.profession.setOnItemSelectedListener(this);

        if(professionResponse.getData().size()>0){
            for (int i = 0 ; i < professionResponse.getData().size() ; i++){
                _allProfession.add(professionResponse.getData().get(i));
            }
            ArrayAdapter<ProfessionResponseData> dataAdapter = new ArrayAdapter<ProfessionResponseData>(this, android.R.layout.simple_spinner_item, _allProfession){
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0){
                        return false;
                    }else{
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView textView = (TextView) view;
                    if (position == 0){
                        textView.setTextColor(Color.GRAY);
                    }else{
                        textView.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            employmentDetailsBinding.profession.setAdapter(dataAdapter);
        }

    }

    private void setOccupationSpinner(OccupationResponse occupationResponse) {
        ArrayList<OccupationResponseData> _allOccupation = new ArrayList<>();

        OccupationResponseData occupationResponseData = new OccupationResponseData();
        occupationResponseData.setName("Choose Occupation");
        occupationResponseData.setId(0);
        occupationResponseData.setDescription("");

        _allOccupation.add(occupationResponseData);

        employmentDetailsBinding.occupation.setOnItemSelectedListener(this);
        if (occupationResponse.getData().size() > 0){
            for (int i = 0 ; i < occupationResponse.getData().size() ; i++){
                _allOccupation.add(occupationResponse.getData().get(i));
            }

            ArrayAdapter<OccupationResponseData> dataAdapter = new ArrayAdapter<OccupationResponseData>(this, android.R.layout.simple_spinner_item, _allOccupation){
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0){
                        return false;
                    }else{
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView textView = (TextView) view;
                    if (position == 0){
                        textView.setTextColor(Color.GRAY);
                    }else{
                        textView.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            employmentDetailsBinding.occupation.setAdapter(dataAdapter);
        }
    }

    private void setClicks() {
        employmentDetailsBinding.btnContainer.btnNext.setOnClickListener(this);
    }

    private void setViewModel() {
        employmentDetailsViewModel = new ViewModelProvider(this).get(EmploymentDetailsViewModel.class);
        occupationPostParams = new OccupationPostParams();
        professionPostParams = new ProfessionPostParams();
        registerEmploymentDetailsPostParams = new RegisterEmploymentDetailsPostParams();
        registerEmploymentDetailsPostConsumerList = new RegisterEmploymentDetailsPostConsumerList();
        consumerList = new ArrayList<>();
        consumerAccountDetailsResponse = (GetConsumerAccountDetailsResponse) getIntent().getSerializableExtra(Config.CONSUMER_ACC_DETAILS);
        saveKycPostParams = new SaveKycPostParams();
    }

    private void getProfession() {
        setProfessionPostParams();
        employmentDetailsViewModel.postProfessionData(professionPostParams);
        showLoading();
    }

    private void setProfessionPostParams() {
        professionPostParams.getData().setCodeTypeId(Config.codeTypeIdForProfession);
    }

    private void setOccupationPostParams() {
        occupationPostParams.getData().setCodeTypeId(Config.codeTypeIdForOccupation);
    }

    private void getOccupation() {
        setOccupationPostParams();
        employmentDetailsViewModel.postOccupationData(occupationPostParams);
        showLoading();
    }

    private void setLayout() {
        employmentDetailsBinding.steps.screenHeader.stepsHeading1.setText("Your");
        employmentDetailsBinding.steps.screenHeader.stepsHeading2.setText("Details");
        employmentDetailsBinding.steps.step1.setBackground(this.getDrawable(R.color.custom_blue));
        employmentDetailsBinding.steps.step2.setBackground(this.getDrawable(R.color.custom_blue));
    }

    private void setBinding() {
        employmentDetailsBinding = EmploymentDetailsBinding.inflate(getLayoutInflater());
        setContentView(employmentDetailsBinding.getRoot());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.profession:
                selectedProfession = (ProfessionResponseData) adapterView.getSelectedItem();
                break;
            case R.id.occupation:
                selectedOccupation = (OccupationResponseData) adapterView.getSelectedItem();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                registerEmploymentDetails();
                break;
            case R.id.bt_back:
                finish();
                break;
        }
    }

    private void registerEmploymentDetails() {
        if (selectedOccupation.getId() == 0 ){

            showAlert(Config.errorType,"Please Select Occupation !!!");

        }else if (selectedProfession.getId() == 0){

            showAlert(Config.errorType,"Please Select Profession !!!");

        }else if (isEmpty(employmentDetailsBinding.etSalary)){

            showAlert(Config.errorType,"Please Enter Salary !!!");

        }else{
            setConsumerList();
            setRegisterEmployeeDetailsPostParams();
            employmentDetailsViewModel.registerEmpDetails(registerEmploymentDetailsPostParams,getStringFromPref(Config.ACCESS_TOKEN));
            showLoading();
        }
    }

    private void setRegisterEmployeeDetailsPostParams() {
//        registerEmploymentDetailsPostParams.getData().setConsumerList(consumerList);
    }

    private void setConsumerList() {
        registerEmploymentDetailsPostConsumerList.setRdaCustomerProfileId( Integer.parseInt( getStringFromPref( Config.PROFILE_ID ) ) );
        registerEmploymentDetailsPostConsumerList.setRdaCustomerAccInfoId( Integer.parseInt( getStringFromPref( Config.ACCOUNT_INFO_ID ) ));
        registerEmploymentDetailsPostConsumerList.setOccupationId(selectedOccupation.getId());
        registerEmploymentDetailsPostConsumerList.setProfessionId(selectedProfession.getId());
        registerEmploymentDetailsPostConsumerList.setPrimary(consumerAccountDetailsResponse.getData().getConsumerList().get(0).isPrimary());

        consumerList.add(registerEmploymentDetailsPostConsumerList);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setLayout();
    }
}
