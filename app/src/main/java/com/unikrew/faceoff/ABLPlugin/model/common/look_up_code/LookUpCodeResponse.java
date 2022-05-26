package com.unikrew.faceoff.ABLPlugin.model.common.look_up_code;

import java.io.Serializable;
import java.util.ArrayList;

public class LookUpCodeResponse implements Serializable {
    private ArrayList<LookUpCodeResponseData> data;
    private LookUpCodeResponseMessage message;

    public ArrayList<LookUpCodeResponseData> getData() {
        return data;
    }

    public void setData(ArrayList<LookUpCodeResponseData> data) {
        this.data = data;
    }

    public LookUpCodeResponseMessage getMessage() {
        return message;
    }

    public void setMessage(LookUpCodeResponseMessage message) {
        this.message = message;
    }
}
