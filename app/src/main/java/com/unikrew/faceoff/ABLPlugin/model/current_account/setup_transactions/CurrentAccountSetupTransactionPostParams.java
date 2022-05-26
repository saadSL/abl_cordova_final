package com.unikrew.faceoff.ABLPlugin.model.current_account.setup_transactions;

import java.io.Serializable;

public class CurrentAccountSetupTransactionPostParams implements Serializable {
    public CurrentAccountSetupTransactionPostData data = new CurrentAccountSetupTransactionPostData();

    public CurrentAccountSetupTransactionPostData getData() {
        return data;
    }

    public void setData(CurrentAccountSetupTransactionPostData data) {
        this.data = data;
    }
}
