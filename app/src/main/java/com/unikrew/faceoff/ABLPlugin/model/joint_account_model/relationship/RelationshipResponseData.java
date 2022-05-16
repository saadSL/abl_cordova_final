package com.unikrew.faceoff.ABLPlugin.model.joint_account_model.relationship;

import java.io.Serializable;

public class RelationshipResponseData implements Serializable {
    private String description;
    private int id;
    private String name;

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

}
