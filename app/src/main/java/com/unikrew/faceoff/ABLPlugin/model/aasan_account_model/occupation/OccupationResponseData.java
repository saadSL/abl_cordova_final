package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.occupation;

import java.io.Serializable;

public class OccupationResponseData implements Serializable {
    public String description;
    public int id;
    public String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return this.name;
    }
}
