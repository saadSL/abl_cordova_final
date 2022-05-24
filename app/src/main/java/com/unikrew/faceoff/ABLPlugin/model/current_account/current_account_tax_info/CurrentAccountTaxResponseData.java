package com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info;

import java.io.Serializable;
import java.util.ArrayList;

public class CurrentAccountTaxResponseData implements Serializable {
    public ArrayList<CurrentAccountTaxResponseConsumerList> consumerList;

    public ArrayList<CurrentAccountTaxResponseConsumerList> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(ArrayList<CurrentAccountTaxResponseConsumerList> consumerList) {
        this.consumerList = consumerList;
    }
}
