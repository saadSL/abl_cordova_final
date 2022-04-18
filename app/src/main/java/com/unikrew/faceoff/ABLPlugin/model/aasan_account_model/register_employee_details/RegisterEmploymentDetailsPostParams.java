package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details;

import java.io.Serializable;

public class RegisterEmploymentDetailsPostParams implements Serializable {
    public RegisterEmployeeDetailsPostData data = new RegisterEmployeeDetailsPostData();

    public RegisterEmployeeDetailsPostData getData() {
        return data;
    }

    public void setData(RegisterEmployeeDetailsPostData data) {
        this.data = data;
    }
}
