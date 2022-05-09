package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.additional_applicant;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.R;

public class AdditionalApplicantViewHolder extends RecyclerView.ViewHolder {

    TextView tvHeading;
    EditText etName;

    public AdditionalApplicantViewHolder(@NonNull View itemView) {
        super(itemView);
        bindItems(itemView);
    }

    private void bindItems(View itemView) {
        tvHeading = itemView.findViewById(R.id.heading_additional_applicant);
        etName = itemView.findViewById(R.id.et_relationship_name);
    }
}
