package com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info;

import java.io.Serializable;

public class CurrentAccountTaxPostParams implements Serializable {
    private CurrentAccountTaxPostData data = new CurrentAccountTaxPostData();

    public CurrentAccountTaxPostData getData() {
        return data;
    }

    public void setData(CurrentAccountTaxPostData data) {
        this.data = data;
    }
}
