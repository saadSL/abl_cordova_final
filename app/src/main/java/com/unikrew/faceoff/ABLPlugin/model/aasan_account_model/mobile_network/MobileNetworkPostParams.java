package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.mobile_network;

import java.io.Serializable;

public class MobileNetworkPostParams implements Serializable {
    public MobileNetworkPostData data = new MobileNetworkPostData();

    public MobileNetworkPostData getData() {
        return data;
    }

    public void setData(MobileNetworkPostData data) {
        this.data = data;
    }
}