package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.additional_applicant;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ItemLayoutAdditionalApplicantBinding;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.occupation.OccupationResponseData;
import com.unikrew.faceoff.ABLPlugin.model.joint_account_model.joint_applicant.ApplicantModel;
import com.unikrew.faceoff.ABLPlugin.model.joint_account_model.joint_applicant.JointApplicant;
import com.unikrew.faceoff.ABLPlugin.model.joint_account_model.relationship.RelationshipPostData;
import com.unikrew.faceoff.ABLPlugin.model.joint_account_model.relationship.RelationshipResponse;
import com.unikrew.faceoff.ABLPlugin.model.joint_account_model.relationship.RelationshipResponseData;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.account_application.AccountApplicationViewHolder;

import java.util.ArrayList;

public class AdditionalApplicantAdapter extends RecyclerView.Adapter<AdditionalApplicantViewHolder> {

    int numberOfJointApplicants;
    private Context context;
    private RelationshipResponse relationshipResponse;
    private ArrayList<RelationshipResponseData> selectedRelationship;


    public AdditionalApplicantAdapter(int numberOfJointApplicants, Context context, ArrayList<RelationshipResponseData> selectedRelationship, RelationshipResponse relationshipResponse) {
        this.numberOfJointApplicants = numberOfJointApplicants;
        this.context = context;
        this.relationshipResponse = relationshipResponse;
        this.selectedRelationship = selectedRelationship;
    }

    @NonNull
    @Override
    public AdditionalApplicantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutAdditionalApplicantBinding itemBinding = ItemLayoutAdditionalApplicantBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AdditionalApplicantViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdditionalApplicantViewHolder holder, int position) {
        int count = position + 1;

        holder.itemBinding.headingAdditionalApplicant.setText("Additional Applicant "+count+" Details ");

        selectedRelationship.add(new RelationshipResponseData());


        setSpinner(holder,position);


    }

    private void setSpinner(AdditionalApplicantViewHolder holder, int position) {


        holder.itemBinding.spRelationshipName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRelationship.set(position, (RelationshipResponseData) adapterView.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<RelationshipResponseData> dataAdapter = new ArrayAdapter<RelationshipResponseData>(context, android.R.layout.simple_spinner_item, relationshipResponse.getData()){
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
        holder.itemBinding.spRelationshipName.setAdapter(dataAdapter);
    }

    @Override
    public int getItemCount() {
        return numberOfJointApplicants;
    }
}
