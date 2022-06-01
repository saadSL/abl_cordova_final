package com.unikrew.faceoff.ABLPlugin.model.common.register_consumer_basic_info;

import java.io.Serializable;
import java.util.ArrayList;

public class RegisterConsumerBasicInfoResponseData implements Serializable {
    public ArrayList<RegisterConsumerBasicInfoResponseConsumerList> consumerList;

    public ArrayList<RegisterConsumerBasicInfoResponseConsumerList> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(ArrayList<RegisterConsumerBasicInfoResponseConsumerList> consumerList) {
        this.consumerList = consumerList;
    }
}
