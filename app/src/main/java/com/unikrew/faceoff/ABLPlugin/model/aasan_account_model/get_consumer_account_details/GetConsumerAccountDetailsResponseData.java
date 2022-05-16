package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details;

import java.io.Serializable;
import java.util.ArrayList;

public class GetConsumerAccountDetailsResponseData implements Serializable {
    public ArrayList<GetConsumerAccountDetailsResponseConsumerList> consumerList;
    public Integer noOfJointApplicatns= null;
    public Object channelId;
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

    public void setNoOfJointApplicatns(Integer noOfJointApplicatns) {
        this.noOfJointApplicatns = noOfJointApplicatns;
    }

    public Object getChannelId() {
        return channelId;
    }

    public void setChannelId(Object channelId) {
        this.channelId = channelId;
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
