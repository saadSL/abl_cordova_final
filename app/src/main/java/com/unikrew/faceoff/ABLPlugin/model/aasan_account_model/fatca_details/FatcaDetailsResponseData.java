package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.fatca_details;

import java.io.Serializable;
import java.util.ArrayList;

public class FatcaDetailsResponseData implements Serializable {
    public ArrayList<FatcaResponseDtoList> fatcaResDtoList;

    public ArrayList<FatcaResponseDtoList> getFatcaResDtoList() {
        return fatcaResDtoList;
    }

    public void setFatcaResDtoList(ArrayList<FatcaResponseDtoList> fatcaResDtoList) {
        this.fatcaResDtoList = fatcaResDtoList;
    }
}
