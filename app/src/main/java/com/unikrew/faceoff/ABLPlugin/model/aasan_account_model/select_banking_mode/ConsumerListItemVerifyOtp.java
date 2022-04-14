package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode;

import java.io.Serializable;

public class ConsumerListItemVerifyOtp implements Serializable {
    private int bankingModeId;
    private int customerTypeId;
    private String customerBranch;
    private String idNumber;
    private String mobileNo;
    private boolean isPrimary;

    @Override
    public String toString() {
        return "ConsumerListItemVerifyOtp{" +
                "bankingModeId=" + bankingModeId +
                ", customerTypeId=" + customerTypeId +
                ", customerBranch='" + customerBranch + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", isPrimary=" + isPrimary +
                '}';
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

    public void setBankingModeId(int bankingModeId) {
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
