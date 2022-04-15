package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details;

import java.io.Serializable;

public class RegisterEmployeeDetailsPostConsumerList implements Serializable {
    public int rdaCustomerProfileId;
    public int rdaCustomerAccInfoId;
    public int occupationId;
    public int professionId;
    public boolean isPrimary;
    public String salary;

    public int getRdaCustomerProfileId() {
        return rdaCustomerProfileId;
    }

    public void setRdaCustomerProfileId(int rdaCustomerProfileId) {
        this.rdaCustomerProfileId = rdaCustomerProfileId;
    }

    public int getRdaCustomerAccInfoId() {
        return rdaCustomerAccInfoId;
    }

    public void setRdaCustomerAccInfoId(int rdaCustomerAccInfoId) {
        this.rdaCustomerAccInfoId = rdaCustomerAccInfoId;
    }

    public int getOccupationId() {
        return occupationId;
    }

    public void setOccupationId(int occupationId) {
        this.occupationId = occupationId;
    }

    public int getProfessionId() {
        return professionId;
    }

    public void setProfessionId(int professionId) {
        this.professionId = professionId;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
