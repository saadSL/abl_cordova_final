package com.unikrew.faceoff.ABLPlugin.model.current_account.countries;

import java.io.Serializable;
import java.util.ArrayList;

public class CountriesResponse implements Serializable {
    public ArrayList<CountriesResponseData> data;
    public CountriesResponseMessage message;

    public ArrayList<CountriesResponseData> getData() {
        return data;
    }

    public void setData(ArrayList<CountriesResponseData> data) {
        this.data = data;
    }

    public CountriesResponseMessage getMessage() {
        return message;
    }

    public void setMessage(CountriesResponseMessage message) {
        this.message = message;
    }
}
