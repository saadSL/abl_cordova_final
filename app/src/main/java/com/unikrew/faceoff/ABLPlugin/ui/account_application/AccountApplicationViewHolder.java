package com.unikrew.faceoff.ABLPlugin.ui.account_application;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.R;

public class AccountApplicationViewHolder extends RecyclerView.ViewHolder {

    TextView tvName;
    TextView tvAccountType;
    TextView tvAccountCurrency;

    ImageView imgDelete;

    public AccountApplicationViewHolder(@NonNull View itemView) {
        super(itemView);
        bind(itemView);
    }

    private void bind(View itemView) {
       tvName = itemView.findViewById(R.id.name);
       tvAccountType = itemView.findViewById(R.id.accountType);
       tvAccountCurrency = itemView.findViewById(R.id.accountCurrency);

       imgDelete = itemView.findViewById(R.id.btn_del);
    }


}
