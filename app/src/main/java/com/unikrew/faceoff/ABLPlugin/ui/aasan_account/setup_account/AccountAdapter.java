package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.setup_account;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.LayoutItemPreferredAccountBinding;
import com.unikrew.faceoff.ABLPlugin.model.common.look_up_code.LookUpCodeResponseData;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountViewHolder> {
    ArrayList<LookUpCodeResponseData> preferredAccountList = new ArrayList<>();
    AccountInterface accountInterface;
    AccountAdapter(ArrayList<LookUpCodeResponseData> list, Context context, AccountInterface accountInterface){
        preferredAccountList = list;
        this.accountInterface = accountInterface;
    }


    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemPreferredAccountBinding view = LayoutItemPreferredAccountBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {

        if (preferredAccountList.get(holder.getAdapterPosition()).getSelected()){
            holder.preferredAccountBinding.rbPreferredAccount.setChecked(true);
        }else{
            holder.preferredAccountBinding.rbPreferredAccount.setChecked(false);
        }


        holder.preferredAccountBinding.tvPreferredAccountHeading.setText(preferredAccountList.get(position).getName());
        holder.preferredAccountBinding.rbPreferredAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton radioButton, boolean isChecked) {
                if (isChecked){
                    accountInterface.setSelectionAt(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return preferredAccountList.size();
    }

    public void setList(ArrayList<LookUpCodeResponseData> preferredAccountList) {
        this.preferredAccountList = preferredAccountList;
        notifyDataSetChanged();
    }
}
