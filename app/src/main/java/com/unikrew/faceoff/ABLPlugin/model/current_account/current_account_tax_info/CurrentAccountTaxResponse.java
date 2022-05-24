package com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info;

import java.io.Serializable;

public class CurrentAccountTaxResponse implements Serializable {
    private CurrentAccountTaxResponseData data;
    private CurrentAccountTaxResponseMessage message;

    public CurrentAccountTaxResponseData getData() {
        return data;
    }

    public void setData(CurrentAccountTaxResponseData data) {
        this.data = data;
    }

    public CurrentAccountTaxResponseMessage getMessage() {
        return message;
    }

    public void setMessage(CurrentAccountTaxResponseMessage message) {
        this.message = message;
    }
}
