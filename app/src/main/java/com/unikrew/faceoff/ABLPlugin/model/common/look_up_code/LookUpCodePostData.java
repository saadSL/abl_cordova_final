package com.unikrew.faceoff.ABLPlugin.model.common.look_up_code;

import java.io.Serializable;

public class LookUpCodePostData implements Serializable {
    private int codeTypeId;

    public int getCodeTypeId() {
        return codeTypeId;
    }

    public void setCodeTypeId(int codeTypeId) {
        this.codeTypeId = codeTypeId;
    }
}
