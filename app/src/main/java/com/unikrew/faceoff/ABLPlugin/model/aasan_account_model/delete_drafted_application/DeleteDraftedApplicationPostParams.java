package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.delete_drafted_application;

import java.io.Serializable;

public class DeleteDraftedApplicationPostParams implements Serializable {
    public DeleteDraftedApplicationPostData data = new DeleteDraftedApplicationPostData();

    public DeleteDraftedApplicationPostData getData() {
        return data;
    }

    public void setData(DeleteDraftedApplicationPostData data) {
        this.data = data;
    }
}
