package com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info;

import java.io.Serializable;
import java.util.ArrayList;

public class CurrentAccountTaxPostData implements Serializable {
    private ArrayList<CurrentAccountTaxPostConsumerList> consumerList;

    public ArrayList<CurrentAccountTaxPostConsumerList> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(ArrayList<CurrentAccountTaxPostConsumerList> consumerList) {
        this.consumerList = consumerList;
    }
}
