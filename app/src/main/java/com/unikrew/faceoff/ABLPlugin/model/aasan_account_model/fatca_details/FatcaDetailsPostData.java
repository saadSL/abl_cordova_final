package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.fatca_details;

import java.io.Serializable;
import java.util.ArrayList;

public class FatcaDetailsPostData implements Serializable {
    private ArrayList<FatcaReqPostDtoList> fatcaReqDtoList;

    public ArrayList<FatcaReqPostDtoList> getFatcaReqDtoList() {
        return fatcaReqDtoList;
    }

    public void setFatcaReqDtoList(ArrayList<FatcaReqPostDtoList> fatcaReqDtoList) {
        this.fatcaReqDtoList = fatcaReqDtoList;
    }
}
