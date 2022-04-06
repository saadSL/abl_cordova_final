package com.unikrew.faceoff.ABLPlugin.model.phase2.get_consumer_account_details;

import java.io.Serializable;
import java.util.ArrayList;

public class GetConsumerAccountDetailsResponseConsumerList implements Serializable {
    public ArrayList<GetConsumerAccountDetailsResponseConsumerList> consumerList;
    public int noOfJointApplicatns;
    public int sessionTimeout;
    public ArrayList<Object> pdaRemitterDetailList;

    public ArrayList<GetConsumerAccountDetailsResponseConsumerList> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(ArrayList<GetConsumerAccountDetailsResponseConsumerList> consumerList) {
        this.consumerList = consumerList;
    }

    public int getNoOfJointApplicatns() {
        return noOfJointApplicatns;
    }

    public void setNoOfJointApplicatns(int noOfJointApplicatns) {
        this.noOfJointApplicatns = noOfJointApplicatns;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public ArrayList<Object> getPdaRemitterDetailList() {
        return pdaRemitterDetailList;
    }

    public void setPdaRemitterDetailList(ArrayList<Object> pdaRemitterDetailList) {
        this.pdaRemitterDetailList = pdaRemitterDetailList;
    }
}
