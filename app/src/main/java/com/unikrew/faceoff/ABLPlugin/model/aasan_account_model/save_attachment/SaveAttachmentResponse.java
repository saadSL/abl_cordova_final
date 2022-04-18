package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_attachment;

import java.io.Serializable;

public class SaveAttachmentResponse implements Serializable {
    public SaveAttachmentResponseData data;
    public SaveAttachmentResponseMessage message;

    public SaveAttachmentResponseData getData() {
        return data;
    }

    public void setData(SaveAttachmentResponseData data) {
        this.data = data;
    }

    public SaveAttachmentResponseMessage getMessage() {
        return message;
    }

    public void setMessage(SaveAttachmentResponseMessage message) {
        this.message = message;
    }
}
