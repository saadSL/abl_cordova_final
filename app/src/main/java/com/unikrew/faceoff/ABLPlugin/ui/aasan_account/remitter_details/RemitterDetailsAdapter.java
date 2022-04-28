package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.remitter_details;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.databinding.RemitterDetailsItemBinding;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.remitter_details.PdaRemitterDetailListItem;

import java.util.List;

public class RemitterDetailsAdapter extends RecyclerView.Adapter<RemitterDetailsAdapter.RemitterViewHolder> {
    private List<PdaRemitterDetailListItem> pdaRemitterDetailList;
    private Context mContext;

    public RemitterDetailsAdapter(List<PdaRemitterDetailListItem> pdaRemitterDetailList, Context mContext) {
        this.pdaRemitterDetailList = pdaRemitterDetailList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RemitterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RemitterDetailsItemBinding itemBinding = RemitterDetailsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RemitterViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RemitterViewHolder holder, int position) {
        textChangeListener(holder,position);
    }

    private void textChangeListener(RemitterViewHolder holder,int position) {
        holder.itemBinding.etFullName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (!holder.itemBinding.etFullName.getText().toString().trim().isEmpty()){
                    pdaRemitterDetailList.get(position).setRemitterName(holder.itemBinding.etFullName.getText().toString().trim());
                }
            }
        });

        holder.itemBinding.etRelationship.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (!holder.itemBinding.etRelationship.getText().toString().trim().isEmpty()){
                    pdaRemitterDetailList.get(position).setRelationshipWithRemitter(holder.itemBinding.etRelationship.getText().toString().trim());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdaRemitterDetailList.size();
    }

    public class RemitterViewHolder extends RecyclerView.ViewHolder {
        private RemitterDetailsItemBinding itemBinding;

        public RemitterViewHolder(@NonNull RemitterDetailsItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}
