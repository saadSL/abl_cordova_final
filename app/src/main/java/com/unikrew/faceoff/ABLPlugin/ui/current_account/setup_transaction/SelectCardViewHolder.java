package com.unikrew.faceoff.ABLPlugin.ui.current_account.setup_transaction;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.R;

public class SelectCardViewHolder extends RecyclerView.ViewHolder {

    ImageView imgAtmCard;
    TextView tvAtmCardHeading;
    LinearLayout llAtmCardLayout;


    public SelectCardViewHolder(@NonNull View itemView) {
        super(itemView);
        bindItemView(itemView);
    }

    private void bindItemView(View itemView) {
        imgAtmCard = itemView.findViewById(R.id.img_atm_card);
        tvAtmCardHeading = itemView.findViewById(R.id.heading_atm_card);
        llAtmCardLayout = itemView.findViewById(R.id.ll_atm_card_layout);
    }
}
