package com.unikrew.faceoff.ABLPlugin.model.current_account.setup_transactions;

import java.io.Serializable;
import java.util.ArrayList;

public class CurrentAccountSetupTransactionPostData implements Serializable {
    public int rdaCustomerAccInfoId;
    public int rdaCustomerId;
    public int customerTypeId;
    public int esoaInd;
//    public int physicalCardInd;
//    public int bankingModeId;
//    public String customerBranch;
//    public int customerAccountTypeId;
//    public int purposeOfAccountId;
//    public int proofOfIncomeInd;
//    public int accountVariantId;
    public int atmTypeId;
    public int transAlertInd;
    public int chequeBookReqInd;
    public Object transactionalAlertId;
//    public int natureOfAccountId;
//    public int currencyTypeId;
//    public ArrayList<Object> pdaRemitterDetailList;
//    public int noOfJointApplicatns;
    public int mailingAddrPrefId;
    public int reasonForVisaDebitCardRequestId;

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

    public int getEsoaInd() {
        return esoaInd;
    }

    public void setEsoaInd(int esoaInd) {
        this.esoaInd = esoaInd;
    }

//    public int getPhysicalCardInd() {
//        return physicalCardInd;
//    }
//
//    public void setPhysicalCardInd(int physicalCardInd) {
//        this.physicalCardInd = physicalCardInd;
//    }
//
//    public int getBankingModeId() {
//        return bankingModeId;
//    }
//
//    public void setBankingModeId(int bankingModeId) {
//        this.bankingModeId = bankingModeId;
//    }
//
//    public String getCustomerBranch() {
//        return customerBranch;
//    }
//
//    public void setCustomerBranch(String customerBranch) {
//        this.customerBranch = customerBranch;
//    }
//
//    public int getCustomerAccountTypeId() {
//        return customerAccountTypeId;
//    }
//
//    public void setCustomerAccountTypeId(int customerAccountTypeId) {
//        this.customerAccountTypeId = customerAccountTypeId;
//    }
//
//    public int getPurposeOfAccountId() {
//        return purposeOfAccountId;
//    }
//
//    public void setPurposeOfAccountId(int purposeOfAccountId) {
//        this.purposeOfAccountId = purposeOfAccountId;
//    }
//
//    public int getProofOfIncomeInd() {
//        return proofOfIncomeInd;
//    }
//
//    public void setProofOfIncomeInd(int proofOfIncomeInd) {
//        this.proofOfIncomeInd = proofOfIncomeInd;
//    }
//
//    public int getAccountVariantId() {
//        return accountVariantId;
//    }
//
//    public void setAccountVariantId(int accountVariantId) {
//        this.accountVariantId = accountVariantId;
//    }

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

    public Object getTransactionalAlertId() {
        return transactionalAlertId;
    }

    public void setTransactionalAlertId(Object transactionalAlertId) {
        this.transactionalAlertId = transactionalAlertId;
    }

//    public int getNatureOfAccountId() {
//        return natureOfAccountId;
//    }
//
//    public void setNatureOfAccountId(int natureOfAccountId) {
//        this.natureOfAccountId = natureOfAccountId;
//    }
//
//    public int getCurrencyTypeId() {
//        return currencyTypeId;
//    }
//
//    public void setCurrencyTypeId(int currencyTypeId) {
//        this.currencyTypeId = currencyTypeId;
//    }
//
//    public ArrayList<Object> getPdaRemitterDetailList() {
//        return pdaRemitterDetailList;
//    }
//
//    public void setPdaRemitterDetailList(ArrayList<Object> pdaRemitterDetailList) {
//        this.pdaRemitterDetailList = pdaRemitterDetailList;
//    }
//
//    public int getNoOfJointApplicatns() {
//        return noOfJointApplicatns;
//    }
//
//    public void setNoOfJointApplicatns(int noOfJointApplicatns) {
//        this.noOfJointApplicatns = noOfJointApplicatns;
//    }

    public int getMailingAddrPrefId() {
        return mailingAddrPrefId;
    }

    public void setMailingAddrPrefId(int mailingAddrPrefId) {
        this.mailingAddrPrefId = mailingAddrPrefId;
    }

    public int getReasonForVisaDebitCardRequestId() {
        return reasonForVisaDebitCardRequestId;
    }

    public void setReasonForVisaDebitCardRequestId(int reasonForVisaDebitCardRequestId) {
        this.reasonForVisaDebitCardRequestId = reasonForVisaDebitCardRequestId;
    }
}
