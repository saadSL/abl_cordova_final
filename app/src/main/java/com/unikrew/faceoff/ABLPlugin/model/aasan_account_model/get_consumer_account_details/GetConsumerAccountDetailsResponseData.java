package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details;

import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.ConsumerListItemResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class GetConsumerAccountDetailsResponseData implements Serializable {
    private ArrayList<ConsumerListItemResponse> consumerList;
    private Integer noOfJointApplicatns= null;
    private Object channelId;
    private int sessionTimeout;
    private ArrayList<Object> pdaRemitterDetailList;

    public ArrayList<ConsumerListItemResponse> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(ArrayList<ConsumerListItemResponse> consumerList) {
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
