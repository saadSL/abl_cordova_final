package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.additional_applicant;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.ABLPlugin.model.joint_account_model.joint_applicant.JointApplicant;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.account_application.AccountApplicationViewHolder;

import java.util.ArrayList;

public class AdditionalApplicantAdapter extends RecyclerView.Adapter<AdditionalApplicantViewHolder> {

    int numberOfJointApplicants;
    Context context;
    ArrayList<String> applicants = new ArrayList<>();
    String name = "";

    public AdditionalApplicantAdapter(int numberOfJointApplicants, Context context) {
        this.numberOfJointApplicants = numberOfJointApplicants;
        this.context = context;
    }

    @NonNull
    @Override
    public AdditionalApplicantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_additional_applicant,parent,false);
        return new AdditionalApplicantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdditionalApplicantViewHolder holder, int position) {
        int count = position + 1;

        holder.tvHeading.setText("Additional Applicant "+count+" Details ");

    }

    @Override
    public int getItemCount() {
        return numberOfJointApplicants;
    }
}
