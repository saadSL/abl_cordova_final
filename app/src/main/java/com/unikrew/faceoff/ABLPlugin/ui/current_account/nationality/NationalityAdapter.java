package com.unikrew.faceoff.ABLPlugin.ui.current_account.nationality;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.databinding.ItemLayoutNationalityBinding;
import com.unikrew.faceoff.ABLPlugin.model.current_account.countries.CountriesResponseData;
import com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info.CurrentAccountTaxPostNationality;

import java.util.ArrayList;
import java.util.List;

public class NationalityAdapter extends RecyclerView.Adapter<NationalityViewHolder> {
    private Context context;
    private List<CurrentAccountTaxPostNationality> nationalitiesArray;
    private ArrayList<CountriesResponseData> countriesArray;

    public NationalityAdapter(List<CurrentAccountTaxPostNationality> nationalitiesArray, ArrayList<CountriesResponseData> countriesArray, Context context) {
        this.nationalitiesArray = nationalitiesArray;
        this.context = context;
        this.countriesArray = countriesArray;
    }

    @NonNull
    @Override
    public NationalityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutNationalityBinding view = ItemLayoutNationalityBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NationalityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NationalityViewHolder holder, int position) {
        setSpinner(holder, position);
    }

    private void setSpinner(NationalityViewHolder holder, int position) {

        holder.itemNationalityBinding.spinnerNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nationalitiesArray.get(position).setNationalityId(String.valueOf(countriesArray.get(adapterView.getSelectedItemPosition()).getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<CountriesResponseData> dataAdapter = new ArrayAdapter<CountriesResponseData>(context, android.R.layout.simple_spinner_item, this.countriesArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.itemNationalityBinding.spinnerNationality.setAdapter(dataAdapter);
    }

    @Override
    public int getItemCount() {
        return nationalitiesArray.size();
    }

}
