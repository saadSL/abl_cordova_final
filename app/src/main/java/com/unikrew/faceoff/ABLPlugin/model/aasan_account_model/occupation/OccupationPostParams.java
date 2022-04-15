package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.occupation;

import java.io.Serializable;

public class OccupationPostParams implements Serializable {
    public OccupationPostData data = new OccupationPostData();

    public OccupationPostData getData() {
        return data;
    }

    public void setData(OccupationPostData data) {
        this.data = data;
    }
}
