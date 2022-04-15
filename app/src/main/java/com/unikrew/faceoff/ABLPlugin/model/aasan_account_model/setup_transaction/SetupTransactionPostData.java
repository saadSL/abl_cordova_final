package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.setup_transaction;

import java.io.Serializable;

public class SetupTransactionPostData implements Serializable {
    public int rdaCustomerAccInfoId;
    public int rdaCustomerId;
    public int customerTypeId;
    public int atmTypeId;
    public int transAlertInd;
    public int chequeBookReqInd;
    public int transactionalAlertId;

    public int getRdaCustomerAccInfoId() {
        return rdaCustomerAccInfoId;
    }

    public void setRdaCustomerAccInfoId(int rdaCustomerAccInfoId) {
        this.rdaCustomerAccInfoId = rdaCustomerAccInfoId;
    }

    public int getRdaCustomerId() {
        return rdaCustomerId;
    }

    public void setRdaCustomerId(int rdaCustomerId) {
        this.rdaCustomerId = rdaCustomerId;
    }

    public int getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(int customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public int getAtmTypeId() {
        return atmTypeId;
    }

    public void setAtmTypeId(int atmTypeId) {
        this.atmTypeId = atmTypeId;
    }

    public int getTransAlertInd() {
        return transAlertInd;
    }

    public void setTransAlertInd(int transAlertInd) {
        this.transAlertInd = transAlertInd;
    }

    public int getChequeBookReqInd() {
        return chequeBookReqInd;
    }

    public void setChequeBookReqInd(int chequeBookReqInd) {
        this.chequeBookReqInd = chequeBookReqInd;
    }

    public int getTransactionalAlertId() {
        return transactionalAlertId;
    }

    public void setTransactionalAlertId(int transactionalAlertId) {
        this.transactionalAlertId = transactionalAlertId;
    }
}
