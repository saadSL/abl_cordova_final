package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.profession;

import java.io.Serializable;

public class ProfessionPostParams implements Serializable {
    public ProfessionPostData data = new ProfessionPostData();

    public ProfessionPostData getData() {
        return data;
    }

    public void setData(ProfessionPostData data) {
        this.data = data;
    }
}
