package com.unikrew.faceoff.ABLPlugin.model.common.look_up_code;

import java.io.Serializable;

public class LookUpCodePostParams implements Serializable {
    private LookUpCodePostData data = new LookUpCodePostData();

    public LookUpCodePostData getData() {
        return data;
    }

    public void setData(LookUpCodePostData data) {
        this.data = data;
    }
}
