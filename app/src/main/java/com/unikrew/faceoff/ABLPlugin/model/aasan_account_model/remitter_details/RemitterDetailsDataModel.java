package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.remitter_details;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RemitterDetailsDataModel implements Serializable {
	private String customerBranch;
	private Object transactionalAlertId;
	private int physicalCardInd;
	private int customerAccountTypeId;
	private int accountVariantId;
	private int rdaCustomerId;
	private Object bankingModeId;
	private int customerTypeId;
	private int purposeOfAccountId;

	public void setCustomerBranch(String customerBranch) {
		this.customerBranch = customerBranch;
	}

	public void setTransactionalAlertId(Object transactionalAlertId) {
		this.transactionalAlertId = transactionalAlertId;
	}

	public void setPhysicalCardInd(int physicalCardInd) {
		this.physicalCardInd = physicalCardInd;
	}

	public void setCustomerAccountTypeId(int customerAccountTypeId) {
		this.customerAccountTypeId = customerAccountTypeId;
	}

	public void setAccountVariantId(int accountVariantId) {
		this.accountVariantId = accountVariantId;
	}

	public void setRdaCustomerId(int rdaCustomerId) {
		this.rdaCustomerId = rdaCustomerId;
	}

	public void setBankingModeId(Object bankingModeId) {
		this.bankingModeId = bankingModeId;
	}

	public void setCustomerTypeId(int customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public void setPurposeOfAccountId(int purposeOfAccountId) {
		this.purposeOfAccountId = purposeOfAccountId;
	}

	public void setNatureOfAccountId(Integer natureOfAccountId) {
		this.natureOfAccountId = natureOfAccountId;
	}

	public void setRdaCustomerAccInfoId(int rdaCustomerAccInfoId) {
		this.rdaCustomerAccInfoId = rdaCustomerAccInfoId;
	}

	public void setPdaRemitterDetailList(List<PdaRemitterDetailListItem> pdaRemitterDetailList) {
		this.pdaRemitterDetailList = pdaRemitterDetailList;
	}

	public void setTransAlertInd(int transAlertInd) {
		this.transAlertInd = transAlertInd;
	}

	public void setNoOfJointApplicatns(int noOfJointApplicatns) {
		this.noOfJointApplicatns = noOfJointApplicatns;
	}

	public void setChequeBookReqInd(Object chequeBookReqInd) {
		this.chequeBookReqInd = chequeBookReqInd;
	}

	public void setAtmTypeId(Object atmTypeId) {
		this.atmTypeId = atmTypeId;
	}

	public void setProofOfIncomeInd(int proofOfIncomeInd) {
		this.proofOfIncomeInd = proofOfIncomeInd;
	}

	private Integer natureOfAccountId = null;
	private int rdaCustomerAccInfoId;
	private List<PdaRemitterDetailListItem> pdaRemitterDetailList = new ArrayList<>();
	private int transAlertInd;
	private int noOfJointApplicatns;
	private Object chequeBookReqInd;
	private Object atmTypeId;
	private int proofOfIncomeInd;

	public String getCustomerBranch(){
		return customerBranch;
	}

	public Object getTransactionalAlertId(){
		return transactionalAlertId;
	}

	public int getPhysicalCardInd(){
		return physicalCardInd;
	}

	public int getCustomerAccountTypeId(){
		return customerAccountTypeId;
	}

	public int getAccountVariantId(){
		return accountVariantId;
	}

	public int getRdaCustomerId(){
		return rdaCustomerId;
	}

	public Object getBankingModeId(){
		return bankingModeId;
	}

	public int getCustomerTypeId(){
		return customerTypeId;
	}

	public int getPurposeOfAccountId(){
		return purposeOfAccountId;
	}

	public int getNatureOfAccountId(){
		return natureOfAccountId;
	}

	public int getRdaCustomerAccInfoId(){
		return rdaCustomerAccInfoId;
	}

	public List<PdaRemitterDetailListItem> getPdaRemitterDetailList(){
		return pdaRemitterDetailList;
	}

	public int getTransAlertInd(){
		return transAlertInd;
	}

	public int getNoOfJointApplicatns(){
		return noOfJointApplicatns;
	}

	public Object getChequeBookReqInd(){
		return chequeBookReqInd;
	}

	public Object getAtmTypeId(){
		return atmTypeId;
	}

	public int getProofOfIncomeInd(){
		return proofOfIncomeInd;
	}

	@Override
	public String toString() {
		return "RemitterDetailsDataModel{" +
				"customerBranch='" + customerBranch + '\'' +
				", transactionalAlertId=" + transactionalAlertId +
				", physicalCardInd=" + physicalCardInd +
				", customerAccountTypeId=" + customerAccountTypeId +
				", accountVariantId=" + accountVariantId +
				", rdaCustomerId=" + rdaCustomerId +
				", bankingModeId=" + bankingModeId +
				", customerTypeId=" + customerTypeId +
				", purposeOfAccountId=" + purposeOfAccountId +
				", natureOfAccountId=" + natureOfAccountId +
				", rdaCustomerAccInfoId=" + rdaCustomerAccInfoId +
				", pdaRemitterDetailList=" + pdaRemitterDetailList +
				", transAlertInd=" + transAlertInd +
				", noOfJointApplicatns=" + noOfJointApplicatns +
				", chequeBookReqInd=" + chequeBookReqInd +
				", atmTypeId=" + atmTypeId +
				", proofOfIncomeInd=" + proofOfIncomeInd +
				'}';
	}
}