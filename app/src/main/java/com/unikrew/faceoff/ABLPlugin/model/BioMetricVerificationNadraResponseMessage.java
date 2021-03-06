package com.unikrew.faceoff.ABLPlugin.model;

import java.io.Serializable;

public class BioMetricVerificationNadraResponseMessage implements Serializable {
    private String status="";
    private String description="";
    private String errorDetail="";

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

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    @Override
    public String toString(){
        return
                "BioMetricVerificationNadraResponseMessage{" +
                        "status = '" + status + '\'' +
                        ",description = '" + description + '\'' +
                        ",errorDetail = '" + errorDetail + '\'' +
                        "}";
    }
}
