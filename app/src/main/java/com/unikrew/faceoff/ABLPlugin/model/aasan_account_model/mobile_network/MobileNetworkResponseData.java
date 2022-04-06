package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.mobile_network;

import java.io.Serializable;

public class MobileNetworkResponseData implements Serializable {
    public String description;
    public String id;
    public String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
