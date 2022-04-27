package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.fatca_details;

import java.io.Serializable;

public class FatcaDetailsPostParams implements Serializable {
    private FatcaDetailsPostData data = new FatcaDetailsPostData();

    public FatcaDetailsPostData getData() {
        return data;
    }

    public void setData(FatcaDetailsPostData data) {
        this.data = data;
    }
}
