package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveKycPostParams implements Serializable {
    public ArrayList<SaveKycPostData> data = new ArrayList<>();

    public ArrayList<SaveKycPostData> getData() {
        return data;
    }

    public void setData(ArrayList<SaveKycPostData> data) {
        this.data = data;
    }
}
