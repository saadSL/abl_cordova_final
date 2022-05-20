package com.unikrew.faceoff.ABLPlugin.ui.current_account.nationality;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.databinding.ItemLayoutNationalityBinding;

public class NationalityViewHolder extends RecyclerView.ViewHolder{

    public ItemLayoutNationalityBinding itemNationalityBinding;

    public NationalityViewHolder(@NonNull ItemLayoutNationalityBinding itemLayoutNationalityBinding) {
        super(itemLayoutNationalityBinding.getRoot());
        this.itemNationalityBinding = itemLayoutNationalityBinding;
    }
}
