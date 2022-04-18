package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details;

import java.io.Serializable;
import java.util.ArrayList;

public class RegisterEmployeeDetailsResponseData implements Serializable {
    public ArrayList<RegisterEmployeeDetailsResponseConsumerList> consumerList;

    public ArrayList<RegisterEmployeeDetailsResponseConsumerList> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(ArrayList<RegisterEmployeeDetailsResponseConsumerList> consumerList) {
        this.consumerList = consumerList;
    }
}