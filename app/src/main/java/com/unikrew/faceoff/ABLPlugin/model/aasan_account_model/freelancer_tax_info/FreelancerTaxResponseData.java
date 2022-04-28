package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.freelancer_tax_info;

import java.io.Serializable;
import java.util.ArrayList;

public class FreelancerTaxResponseData implements Serializable {
    public ArrayList<FreelancerTaxResponseConsumerList> consumerList;

    public ArrayList<FreelancerTaxResponseConsumerList> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(ArrayList<FreelancerTaxResponseConsumerList> consumerList) {
        this.consumerList = consumerList;
    }
}
