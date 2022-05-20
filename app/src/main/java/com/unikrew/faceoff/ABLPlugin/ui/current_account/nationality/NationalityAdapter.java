package com.unikrew.faceoff.ABLPlugin.ui.current_account.nationality;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.databinding.ItemLayoutNationalityBinding;

import java.util.ArrayList;

public class NationalityAdapter extends RecyclerView.Adapter<NationalityViewHolder>{
    private Context context;
    private INationality iNationality;
    private int number;

    public NationalityAdapter(int number, Context context,INationality iNationality) {
        this.number = number;
        this.context = context;
        this.iNationality = iNationality;
    }

    @NonNull
    @Override
    public NationalityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutNationalityBinding view = ItemLayoutNationalityBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NationalityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NationalityViewHolder holder, int position) {
        setSpinner(holder,position);
    }

    private void setSpinner(NationalityViewHolder holder, int position) {

//        holder.itemNationalityBinding.spinnerNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    selectedNationality = (Nationality) adapterView.getSelectedItem();
//                    iNationality.getNationality(selectedNationality);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//
//        ArrayAdapter<Nationality> dataAdapter = new ArrayAdapter<Nationality>(context, android.R.layout.simple_spinner_item, this.nationalityArrayList){
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
//                TextView textView = (TextView) view;
//                if (position == 0){
//                    textView.setTextColor(Color.GRAY);
//                }else{
//                    textView.setTextColor(Color.BLACK);
//                }
//                return view;
//            }
//        };
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        holder.itemNationalityBinding.spinnerNationality.setAdapter(dataAdapter);
    }

    @Override
    public int getItemCount() {
        return this.number;
    }

    public void setList() {
        number++;
        notifyItemInserted(number-1);
    }
}
