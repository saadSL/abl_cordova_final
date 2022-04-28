package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.freelancer_tax_info;

import java.io.Serializable;
import java.util.ArrayList;

public class FreelancerTaxPostData implements Serializable {
    private ArrayList<FreelancerTaxPostConsumerList> consumerList;

    public ArrayList<FreelancerTaxPostConsumerList> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(ArrayList<FreelancerTaxPostConsumerList> consumerList) {
        this.consumerList = consumerList;
    }
}
