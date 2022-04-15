package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details;

import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ActivityPersonalDetailsOneBinding;
import com.unikrew.faceoff.ABLPlugin.base.BaseActivity;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.SelectionModel;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account.SelectBankingModeActivity;
import com.unikrew.faceoff.Config;
;import java.util.ArrayList;
import java.util.List;


public class PersonalDetailsOneActivity extends BaseActivity implements AdapterClickListener {

    private ActivityPersonalDetailsOneBinding personalDetailsBinding;
    private SuggestionsAdapter motherNameAdapter, placeOfBirthAdapter;
    List<SelectionModel> _motherNameSuggestions = new ArrayList<>();
    List<SelectionModel> _placeOfBirthSuggestions = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        setData();
        setMotherNameRecycler();
        setPlaceOfBirthRecycler();
        clicks();
    }

    private void clicks() {
        personalDetailsBinding.layoutBtn.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidations();
            }
        });

        personalDetailsBinding.layoutBtn.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkValidations() {
        if (isEmpty(personalDetailsBinding.etFullName) || isEmpty(personalDetailsBinding.etFatherName)) {
            showAlert(Config.errorType, getString(R.string.text_fields_error));
        } else if (!isMotherNameSelected()) {
            showAlert(Config.errorType, getString(R.string.mother_name_error));
        } else if (!isBirthPlaceSelected()) {
            showAlert(Config.errorType, getString(R.string.birth_place_error));
        }else {
            goToPersonalDetailsTwo();
        }
    }

    private void goToPersonalDetailsTwo() {
        Intent intent = new Intent(this,PersonalDetailsTwoActivity.class);
        startActivity(intent);
    }


    private boolean isMotherNameSelected() {
        for (int i = 0; i < _motherNameSuggestions.size(); i++) {
            if (_motherNameSuggestions.get(i).getSelected()) {
                return true;
            }
        }
        return false;
    }

    private boolean isBirthPlaceSelected() {
        for (int i = 0; i < _placeOfBirthSuggestions.size(); i++) {
            if (_placeOfBirthSuggestions.get(i).getSelected()) {
                return true;
            }
        }
        return false;
    }

    private void setData() {
        String fullName = SelectBankingModeActivity.globalRegisterVerifyOtp.getData().getConsumerList().get(0).getFullName();
        String fatherHusbandName = SelectBankingModeActivity.globalRegisterVerifyOtp.getData().getConsumerList().get(0).getFatherHusbandName();
        if (fullName != null) {
            personalDetailsBinding.etFullName.setText(fullName);
        }

        if (fatherHusbandName != null) {
            personalDetailsBinding.etFatherName.setText(fatherHusbandName);
        }

    }

    private void setPlaceOfBirthRecycler() {
        List<String> placeOfBirthSuggestions = SelectBankingModeActivity.globalRegisterVerifyOtp.getData().getConsumerList().get(0).getSuggestPlaceOfBirth();
        for (int i = 0; i < placeOfBirthSuggestions.size(); i++) {
            SelectionModel selectionModel = new SelectionModel();
            selectionModel.setSelected(false);
            selectionModel.setTitle(placeOfBirthSuggestions.get(i));
            _placeOfBirthSuggestions.add(selectionModel);
        }

        placeOfBirthAdapter = new SuggestionsAdapter(_placeOfBirthSuggestions, this, Config.PLACE_OF_BIRTH_SUGGESTION, this);
        personalDetailsBinding.rvPlaceOfBirth.setLayoutManager(new GridLayoutManager(this, 3));
        personalDetailsBinding.rvPlaceOfBirth.setAdapter(placeOfBirthAdapter);
    }

    private void setMotherNameRecycler() {
        List<String> motherNameSuggestions = SelectBankingModeActivity.globalRegisterVerifyOtp.getData().getConsumerList().get(0).getSuggestMotherNames();
        for (int i = 0; i < motherNameSuggestions.size(); i++) {
            SelectionModel selectionModel = new SelectionModel();
            selectionModel.setSelected(false);
            selectionModel.setTitle(motherNameSuggestions.get(i));
            _motherNameSuggestions.add(selectionModel);
        }

        motherNameAdapter = new SuggestionsAdapter(_motherNameSuggestions, this, Config.MOTHER_NAME_SUGGESTION, this);
        personalDetailsBinding.rvMotherName.setLayoutManager(new GridLayoutManager(this, 3));
        personalDetailsBinding.rvMotherName.setAdapter(motherNameAdapter);
    }


    private void setBinding() {
        personalDetailsBinding = ActivityPersonalDetailsOneBinding.inflate(getLayoutInflater());
        setContentView(personalDetailsBinding.getRoot());
    }

    @Override
    public void onItemClick(String type, int position) {
        if (type.equals(Config.MOTHER_NAME_SUGGESTION)) {
            unselectAllMotherSuggestions();
            _motherNameSuggestions.get(position).setSelected(true);
            motherNameAdapter.notifyDataSetChanged();
        } else {
            unselectAllPlaceSuggestions();
            _placeOfBirthSuggestions.get(position).setSelected(true);
            placeOfBirthAdapter.notifyDataSetChanged();
        }
    }

    private void unselectAllMotherSuggestions() {
        for (int i = 0; i < _motherNameSuggestions.size(); i++) {
            _motherNameSuggestions.get(i).setSelected(false);
        }
    }

    private void unselectAllPlaceSuggestions() {
        for (int i = 0; i < _placeOfBirthSuggestions.size(); i++) {
            _placeOfBirthSuggestions.get(i).setSelected(false);
        }
    }
}