package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details;

import java.io.Serializable;
import java.util.ArrayList;

public class RegisterEmployeeDetailsPostData implements Serializable {
    public ArrayList<RegisterEmployeeDetailsPostConsumerList> consumerList = new ArrayList<>();

    public ArrayList<RegisterEmployeeDetailsPostConsumerList> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(ArrayList<RegisterEmployeeDetailsPostConsumerList> consumerList) {
        this.consumerList = consumerList;
    }
}