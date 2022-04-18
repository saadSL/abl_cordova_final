package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_attachment;

import java.io.Serializable;

public class SaveAttachmentPostParams implements Serializable {
    public SaveAttachmentPostData data = new SaveAttachmentPostData();

    public SaveAttachmentPostData getData() {
        return data;
    }

    public void setData(SaveAttachmentPostData data) {
        this.data = data;
    }
}
