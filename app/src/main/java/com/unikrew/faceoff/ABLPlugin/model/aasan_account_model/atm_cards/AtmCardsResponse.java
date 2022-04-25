package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.atm_cards;

import java.io.Serializable;
import java.util.ArrayList;

public class AtmCardsResponse implements Serializable {
    public ArrayList<AtmCardsResponseData> data;
    public AtmCardsResponseMessage message;

    public ArrayList<AtmCardsResponseData> getData() {
        return data;
    }

    public void setData(ArrayList<AtmCardsResponseData> data) {
        this.data = data;
    }

    public AtmCardsResponseMessage getMessage() {
        return message;
    }

    public void setMessage(AtmCardsResponseMessage message) {
        this.message = message;
    }
}
