package com.unikrew.faceoff.ABLPlugin.model.joint_account_model.relationship;

import java.io.Serializable;

public class RelationshipPostData implements Serializable {
    private int codeTypeId;

    public int getCodeTypeId() {
        return codeTypeId;
    }

    public void setCodeTypeId(int codeTypeId) {
        this.codeTypeId = codeTypeId;
    }
}
