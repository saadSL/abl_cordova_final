package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.setup_transaction;

import java.io.Serializable;

public class SetupTransactionPostParams implements Serializable {
    public SetupTransactionPostData data = new SetupTransactionPostData();

    public SetupTransactionPostData getData() {
        return data;
    }

    public void setData(SetupTransactionPostData data) {
        this.data = data;
    }
}
