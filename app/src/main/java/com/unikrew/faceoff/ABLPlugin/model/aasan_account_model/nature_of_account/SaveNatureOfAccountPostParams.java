package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.nature_of_account;

import java.io.Serializable;

public class SaveNatureOfAccountPostParams implements Serializable {
    public SaveNatureOfAccountPostData data = new SaveNatureOfAccountPostData();

    public SaveNatureOfAccountPostData getData() {
        return data;
    }

    public void setData(SaveNatureOfAccountPostData data) {
        this.data = data;
    }
}
