package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type;

import java.util.ArrayList;

public class MobileNetworkResponse {
    public ArrayList<MobileNetworkResponseData> data;
    public MobileNetworkResponseMessage message;


    public ArrayList<MobileNetworkResponseData> getData() {
        return data;
    }

    public void setData(ArrayList<MobileNetworkResponseData> data) {
        this.data = data;
    }

    public MobileNetworkResponseMessage getMessage() {
        return message;
    }

    public void setMessage(MobileNetworkResponseMessage message) {
        this.message = message;
    }
}