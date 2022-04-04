package com.unikrew.faceoff.ABLPlugin.model.phase2.view_apps_generate_otp;

import java.io.Serializable;

public class ViewAppsGenerateOtpPostAttachment implements Serializable {
    public String fileName;
    public String base64Content;
    public int attachmentTypeId;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBase64Content() {
        return base64Content;
    }

    public void setBase64Content(String base64Content) {
        this.base64Content = base64Content;
    }

    public int getAttachmentTypeId() {
        return attachmentTypeId;
    }

    public void setAttachmentTypeId(int attachmentTypeId) {
        this.attachmentTypeId = attachmentTypeId;
    }
}
