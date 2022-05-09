package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.additional_applicant;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.ItemLayoutAdditionalApplicantBinding;

public class AdditionalApplicantViewHolder extends RecyclerView.ViewHolder {


    ItemLayoutAdditionalApplicantBinding itemBinding;
    public AdditionalApplicantViewHolder(@NonNull ItemLayoutAdditionalApplicantBinding itemBinding) {
        super(itemBinding.getRoot());
        this.itemBinding = itemBinding;
    }
}
