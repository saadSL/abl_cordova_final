package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details;

import java.io.Serializable;

public class RegisterEmploymentDetailsResponse implements Serializable {
    public RegisterEmployeeDetailsResponseData data;
    public RegisterEmployeeDetailsResponseMessage message;

    public RegisterEmployeeDetailsResponseData getData() {
        return data;
    }

    public void setData(RegisterEmployeeDetailsResponseData data) {
        this.data = data;
    }

    public RegisterEmployeeDetailsResponseMessage getMessage() {
        return message;
    }

    public void setMessage(RegisterEmployeeDetailsResponseMessage message) {
        this.message = message;
    }
}