package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.setup_transaction;

import java.io.Serializable;

public class SetupTransactionResponse implements Serializable {
    public SetupTransactionResponseData data;
    public SetupTransactionResponseMessage message;

    public SetupTransactionResponseData getData() {
        return data;
    }

    public void setData(SetupTransactionResponseData data) {
        this.data = data;
    }

    public SetupTransactionResponseMessage getMessage() {
        return message;
    }

    public void setMessage(SetupTransactionResponseMessage message) {
        this.message = message;
    }
}
