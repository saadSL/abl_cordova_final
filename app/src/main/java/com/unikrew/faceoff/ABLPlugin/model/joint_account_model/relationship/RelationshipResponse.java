package com.unikrew.faceoff.ABLPlugin.model.joint_account_model.relationship;

import java.io.Serializable;
import java.util.ArrayList;

public class RelationshipResponse implements Serializable {
    public ArrayList<RelationshipResponseData> data;
    public RelationshipResponseMessage message;

    public ArrayList<RelationshipResponseData> getData() {
        return data;
    }

    public void setData(ArrayList<RelationshipResponseData> data) {
        this.data = data;
    }

    public RelationshipResponseMessage getMessage() {
        return message;
    }

    public void setMessage(RelationshipResponseMessage message) {
        this.message = message;
    }
}
