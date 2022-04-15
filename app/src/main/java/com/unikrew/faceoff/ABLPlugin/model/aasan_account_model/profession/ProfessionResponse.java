package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.profession;

import java.io.Serializable;
import java.util.ArrayList;

public class ProfessionResponse implements Serializable {
    public ArrayList<ProfessionResponseData> data;
    public ProfessionResponseMessage message;

    public ArrayList<ProfessionResponseData> getData() {
        return data;
    }

    public void setData(ArrayList<ProfessionResponseData> data) {
        this.data = data;
    }

    public ProfessionResponseMessage getMessage() {
        return message;
    }

    public void setMessage(ProfessionResponseMessage message) {
        this.message = message;
    }
}
