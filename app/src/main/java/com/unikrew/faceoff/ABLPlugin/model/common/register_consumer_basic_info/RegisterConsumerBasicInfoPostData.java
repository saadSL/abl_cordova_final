package com.unikrew.faceoff.ABLPlugin.model.common.register_consumer_basic_info;

import java.io.Serializable;
import java.util.ArrayList;

public class RegisterConsumerBasicInfoPostData implements Serializable {
    public ArrayList<RegisterConsumerBasicInfoPostConsumerList> consumerList;

    public ArrayList<RegisterConsumerBasicInfoPostConsumerList> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(ArrayList<RegisterConsumerBasicInfoPostConsumerList> consumerList) {
        this.consumerList = consumerList;
    }
}
