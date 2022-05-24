package com.unikrew.faceoff.ABLPlugin.model.current_account.setup_transactions;

import java.io.Serializable;

public class CurrentAccountSetupTransactionResponse implements Serializable {
    private CurrentAccountSetupTransactionResponseData data;
    private CurrentAccountSetupTransactionResponseMessage message;

    public CurrentAccountSetupTransactionResponseData getData() {
        return data;
    }

    public void setData(CurrentAccountSetupTransactionResponseData data) {
        this.data = data;
    }

    public CurrentAccountSetupTransactionResponseMessage getMessage() {
        return message;
    }

    public void setMessage(CurrentAccountSetupTransactionResponseMessage message) {
        this.message = message;
    }
}
