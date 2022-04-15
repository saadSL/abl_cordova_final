package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model;

import java.io.Serializable;

public class SelectionModel implements Serializable {
   private String title;
   private Boolean isSelected;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
