package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.delete_drafted_application;

import java.io.Serializable;

public class DeleteDraftedApplicationResponse implements Serializable {
    public DeleteDraftedApplicationResponseData data;
    public DeleteDraftedApplicationResponseMessage message;

    public DeleteDraftedApplicationResponseData getData() {
        return data;
    }

    public void setData(DeleteDraftedApplicationResponseData data) {
        this.data = data;
    }

    public DeleteDraftedApplicationResponseMessage getMessage() {
        return message;
    }

    public void setMessage(DeleteDraftedApplicationResponseMessage message) {
        this.message = message;
    }
}
