package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_attachment;

import java.io.Serializable;

public class SaveAttachmentResponseData implements Serializable {
    public int appAttachmentId;
    public int attachmentTypeId;
    public int entityId;
    public String fileName;
    public String mimeType;
    public String path;
    public int rdaCustomerAccInfoId;

    public int getAppAttachmentId() {
        return appAttachmentId;
    }

    public void setAppAttachmentId(int appAttachmentId) {
        this.appAttachmentId = appAttachmentId;
    }

    public int getAttachmentTypeId() {
        return attachmentTypeId;
    }

    public void setAttachmentTypeId(int attachmentTypeId) {
        this.attachmentTypeId = attachmentTypeId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getRdaCustomerAccInfoId() {
        return rdaCustomerAccInfoId;
    }

    public void setRdaCustomerAccInfoId(int rdaCustomerAccInfoId) {
        this.rdaCustomerAccInfoId = rdaCustomerAccInfoId;
    }
}
