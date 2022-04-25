package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.atm_cards;

import java.io.Serializable;

public class AtmCardsPostParams implements Serializable {
    public AtmCardsPostData data = new AtmCardsPostData();

    public AtmCardsPostData getData() {
        return data;
    }

    public void setData(AtmCardsPostData data) {
        this.data = data;
    }
}
