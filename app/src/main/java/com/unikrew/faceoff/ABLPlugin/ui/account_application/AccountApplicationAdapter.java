package com.unikrew.faceoff.ABLPlugin.ui.account_application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerifyOtpResponseAppList;

import java.util.ArrayList;

public class AccountApplicationAdapter extends RecyclerView.Adapter<AccountApplicationViewHolder> {

    ArrayList<GetDraftedAppsVerifyOtpResponseAppList> appList;

    public AccountApplicationAdapter(ArrayList<GetDraftedAppsVerifyOtpResponseAppList> appList) {
        this.appList = appList;
    }

    @NonNull
    @Override
    public AccountApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_list_item,parent,false);
        return new AccountApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountApplicationViewHolder holder, int position) {
        holder.tvName.setText(this.appList.get(position).getFullName());
        if (this.appList.get(position).getAccountType() != null){
            holder.tvAccountType.setText(this.appList.get(position).getAccountType().toString());
        }else{
            holder.tvAccountType.setText("No Account Type");
        }

        if (this.appList.get(position).getCurrencyType() != null){
            holder.tvAccountCurrency.setText(this.appList.get(position).getCurrencyType());
        }else{
            holder.tvAccountCurrency.setText("No Currency Type");
        }
    }

    @Override
    public int getItemCount() {
        return this.appList.size();
    }
}
