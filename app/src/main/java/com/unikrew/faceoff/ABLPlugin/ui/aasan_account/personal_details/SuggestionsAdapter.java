package com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ofss.digx.mobile.android.allied.R;
import com.ofss.digx.mobile.android.allied.databinding.MotherNameItemBinding;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.SelectionModel;

import java.util.List;

public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.CustomViewHolder> {

    private final List<SelectionModel> motherNameSuggestions;
    private final Context context;
    private String type;
    private AdapterClickListener adapterClickListener;

    public SuggestionsAdapter(List<SelectionModel> motherNameSuggestions, Context context, String type,AdapterClickListener adapterClickListener) {
        this.motherNameSuggestions = motherNameSuggestions;
        this.context = context;
        this.type = type;
        this.adapterClickListener = adapterClickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MotherNameItemBinding itemBinding = MotherNameItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CustomViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        SelectionModel item = motherNameSuggestions.get(position);
        setData(item, holder);
        setClicks(position, holder);

    }

    private void setClicks(int position, CustomViewHolder holder) {
        holder.motherNameItemBinding.btnMotherName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterClickListener.onItemClick(type,position);
            }
        });
    }

    private void setData(SelectionModel item, CustomViewHolder holder) {
        holder.motherNameItemBinding.btnMotherName.setText(item.getTitle());
        if (item.getSelected()) {
            holder.motherNameItemBinding.btnMotherName.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corner_blue));
            holder.motherNameItemBinding.btnMotherName.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            holder.motherNameItemBinding.btnMotherName.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corner));
            holder.motherNameItemBinding.btnMotherName.setTextColor(ContextCompat.getColor(context, R.color.grey));
        }
    }

    @Override
    public int getItemCount() {
        return motherNameSuggestions.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private MotherNameItemBinding motherNameItemBinding;

        public CustomViewHolder(@NonNull MotherNameItemBinding motherNameItemBinding) {
            super(motherNameItemBinding.getRoot());
            this.motherNameItemBinding = motherNameItemBinding;
        }


    }
}
