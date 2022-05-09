package com.unikrew.faceoff.ABLPlugin.model.joint_account_model.joint_applicant;

public class JointApplicant {
    private int number;
    private String description;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return description+" "+number;
    }
}
