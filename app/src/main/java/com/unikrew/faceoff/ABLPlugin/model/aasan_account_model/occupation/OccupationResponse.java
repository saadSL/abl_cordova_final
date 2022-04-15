package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.occupation;

import java.io.Serializable;
import java.util.ArrayList;

public class OccupationResponse implements Serializable {
    public ArrayList<OccupationResponseData> data;
    public OccupationResponseMessage message;

    public ArrayList<OccupationResponseData> getData() {
        return data;
    }

    public void setData(ArrayList<OccupationResponseData> data) {
        this.data = data;
    }

    public OccupationResponseMessage getMessage() {
        return message;
    }

    public void setMessage(OccupationResponseMessage message) {
        this.message = message;
    }
}
