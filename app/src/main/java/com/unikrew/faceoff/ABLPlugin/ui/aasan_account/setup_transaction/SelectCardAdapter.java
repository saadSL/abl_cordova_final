package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_transaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponseData;

import java.util.ArrayList;

public class SelectCardAdapter extends RecyclerView.Adapter<SelectCardViewHolder> {

    private ArrayList<LookUpCodeResponseData> atmCardList;
    private SelectCardInterface selectCardInterface;
    private Context context;

    public SelectCardAdapter(ArrayList<LookUpCodeResponseData> cardList, Context context, SelectCardInterface selectCardInterface){
        this.atmCardList = cardList;
        this.selectCardInterface = selectCardInterface;
        this.context = context;
    }

    @NonNull
    @Override
    public SelectCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.atm_card_layout,parent,false);
        return new SelectCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectCardViewHolder holder, int position) {
        if (this.atmCardList.get(holder.getAdapterPosition()).getSelected()){
            holder.llAtmCardLayout.setBackground(context.getDrawable(R.drawable.rounded_corner_selected));
        }else{
            holder.llAtmCardLayout.setBackground(context.getDrawable(R.drawable.rounded_corner_white));
        }
        int atmId = this.atmCardList.get(holder.getAdapterPosition()).getId();

        Glide.with(context).load("https://10.100.102.124/consumer-portal/assets/img/card/"+atmId+".png").placeholder(R.drawable.no_image).into(holder.imgAtmCard);


        holder.tvAtmCardHeading.setText(atmCardList.get(holder.getAdapterPosition()).getName());

        holder.llAtmCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCardInterface.setSelectionAt(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.atmCardList.size();
    }
}
