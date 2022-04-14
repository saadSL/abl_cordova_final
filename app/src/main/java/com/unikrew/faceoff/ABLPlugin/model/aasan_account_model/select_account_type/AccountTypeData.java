package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type;

import java.io.Serializable;

public class AccountTypeData implements Serializable {
	private int rdaCustomerId;
	private int bankingModeId;
	private String customerBranch;
	private int customerTypeId;
	private int purposeOfAccountId;
	private int rdaCustomerAccInfoId;
	private int customerAccountTypeId;
	private Integer accountVariantId = null;

	public Integer getAccountVariantId() {
		return accountVariantId;
	}

	public void setAccountVariantId(Integer accountVariantId) {
		this.accountVariantId = accountVariantId;
	}

	public int getRdaCustomerId(){
		return rdaCustomerId;
	}

	public int getBankingModeId(){
		return bankingModeId;
	}

	public String getCustomerBranch(){
		return customerBranch;
	}

	public int getCustomerTypeId(){
		return customerTypeId;
	}

	public int getPurposeOfAccountId(){
		return purposeOfAccountId;
	}

	public int getRdaCustomerAccInfoId(){
		return rdaCustomerAccInfoId;
	}

	public int getCustomerAccountTypeId(){
		return customerAccountTypeId;
	}

	public void setRdaCustomerId(int rdaCustomerId) {
		this.rdaCustomerId = rdaCustomerId;
	}

	public void setBankingModeId(int bankingModeId) {
		this.bankingModeId = bankingModeId;
	}

	public void setCustomerBranch(String customerBranch) {
		this.customerBranch = customerBranch;
	}

	public void setCustomerTypeId(int customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public void setPurposeOfAccountId(int purposeOfAccountId) {
		this.purposeOfAccountId = purposeOfAccountId;
	}

	public void setRdaCustomerAccInfoId(int rdaCustomerAccInfoId) {
		this.rdaCustomerAccInfoId = rdaCustomerAccInfoId;
	}

	public void setCustomerAccountTypeId(int customerAccountTypeId) {
		this.customerAccountTypeId = customerAccountTypeId;
	}

	@Override
	public String toString() {
		return "AccountTypeData{" +
				"rdaCustomerId=" + rdaCustomerId +
				", bankingModeId=" + bankingModeId +
				", customerBranch='" + customerBranch + '\'' +
				", customerTypeId=" + customerTypeId +
				", purposeOfAccountId=" + purposeOfAccountId +
				", rdaCustomerAccInfoId=" + rdaCustomerAccInfoId +
				", customerAccountTypeId=" + customerAccountTypeId +
				", accountVariantId=" + accountVariantId +
				'}';
	}
}
