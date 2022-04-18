package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details;

import java.io.Serializable;
import java.util.ArrayList;

public class RegisterEmployeeDetailsResponseData implements Serializable {
    public ArrayList<RegisterEmploymentDetailsResponseConsumerList> consumerList;

    public ArrayList<RegisterEmploymentDetailsResponseConsumerList> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(ArrayList<RegisterEmploymentDetailsResponseConsumerList> consumerList) {
        this.consumerList = consumerList;
    }
}
