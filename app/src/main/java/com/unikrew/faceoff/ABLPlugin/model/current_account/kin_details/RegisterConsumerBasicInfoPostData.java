package com.unikrew.faceoff.ABLPlugin.model.current_account.kin_details;

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
