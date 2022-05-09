package com.unikrew.faceoff.ABLPlugin.model.joint_account_model.relationship;

import java.io.Serializable;

public class RelationshipPostParams implements Serializable {
    private RelationshipPostData data = new RelationshipPostData();

    public RelationshipPostData getData() {
        return data;
    }

    public void setData(RelationshipPostData data) {
        this.data = data;
    }
}
