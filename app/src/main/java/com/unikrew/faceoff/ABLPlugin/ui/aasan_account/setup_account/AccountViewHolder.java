package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.databinding.LayoutItemPreferredAccountBinding;

public class AccountViewHolder extends RecyclerView.ViewHolder {

    LayoutItemPreferredAccountBinding preferredAccountBinding;

    public AccountViewHolder(@NonNull LayoutItemPreferredAccountBinding itemView) {
        super(itemView.getRoot());
        bindViews(itemView);
    }

    private void bindViews(LayoutItemPreferredAccountBinding itemView) {
        this.preferredAccountBinding = itemView;
    }
}
