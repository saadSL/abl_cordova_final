package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details;

import java.io.Serializable;
import java.util.ArrayList;

public class RegisterEmployeeDetailsPostData implements Serializable {
    public ArrayList<RegisterEmploymentDetailsPostConsumerList> consumerList = new ArrayList<>();

    public ArrayList<RegisterEmploymentDetailsPostConsumerList> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(ArrayList<RegisterEmploymentDetailsPostConsumerList> consumerList) {
        this.consumerList = consumerList;
    }
}
