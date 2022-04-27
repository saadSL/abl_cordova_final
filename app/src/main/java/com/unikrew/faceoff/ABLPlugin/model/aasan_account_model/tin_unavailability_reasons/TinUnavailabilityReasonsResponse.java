package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.tin_unavailability_reasons;

import java.io.Serializable;
import java.util.ArrayList;

public class TinUnavailabilityReasonsResponse implements Serializable {
    public ArrayList<TinUnavailabilityReasonsResponseData> data;
    public TinUnavailabilityReasonsResponseMessage message;

    public ArrayList<TinUnavailabilityReasonsResponseData> getData() {
        return data;
    }

    public void setData(ArrayList<TinUnavailabilityReasonsResponseData> data) {
        this.data = data;
    }

    public TinUnavailabilityReasonsResponseMessage getMessage() {
        return message;
    }

    public void setMessage(TinUnavailabilityReasonsResponseMessage message) {
        this.message = message;
    }
}
