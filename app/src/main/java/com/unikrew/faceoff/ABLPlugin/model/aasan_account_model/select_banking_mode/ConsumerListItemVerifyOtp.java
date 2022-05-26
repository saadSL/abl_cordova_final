package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode;

import java.io.Serializable;

public class ConsumerListItemVerifyOtp implements Serializable {
    private Integer bankingModeId = null;
    private int customerTypeId;
    private Integer rdaCustomerAccInfoId = null;
    private String customerBranch;
    private String idNumber;
    private String mobileNo;
    private boolean isPrimary;
    public String dateOfBirth;
    public String dateOfIssue;
    public Integer portedMobileNetwork = null;

    public int getPortedMobileNetwork() {
        return portedMobileNetwork;
    }

    public void setPortedMobileNetwork(Integer portedMobileNetwork) {
        this.portedMobileNetwork = portedMobileNetwork;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Integer getRdaCustomerAccInfoId() {
        return rdaCustomerAccInfoId;
    }

    public void setRdaCustomerAccInfoId(Integer rdaCustomerAccInfoId) {
        this.rdaCustomerAccInfoId = rdaCustomerAccInfoId;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }




    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getBankingModeId() {
        return bankingModeId;
    }

    public int getCustomerTypeId() {
        return customerTypeId;
    }

    public String getCustomerBranch() {
        return customerBranch;
    }

    public boolean isIsPrimary() {
        return isPrimary;
    }

    public void setBankingModeId(Integer bankingModeId) {
        this.bankingModeId = bankingModeId;
    }

    public void setCustomerTypeId(int customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public void setCustomerBranch(String customerBranch) {
        this.customerBranch = customerBranch;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

}
