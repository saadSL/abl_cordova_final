package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_attachment;

import java.io.Serializable;

public class SaveAttachmentResponseMessage implements Serializable {
    public String status;
    public String description;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
