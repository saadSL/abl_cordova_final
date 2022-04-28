package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.tin_unavailability_reasons;

import java.io.Serializable;

public class TinUnavailabilityReasonsPostParams implements Serializable {
    private TinUnavailabilityReasonsPostData data = new TinUnavailabilityReasonsPostData();

    public TinUnavailabilityReasonsPostData getData() {
        return data;
    }

    public void setData(TinUnavailabilityReasonsPostData data) {
        this.data = data;
    }
}
