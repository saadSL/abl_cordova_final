package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode;

import java.util.List;

public class BranchesDataModel {
	private List<BranchListItemModel> branchList;
	private List<SuggestBranchListItemModel> suggestBranchList;

	public List<BranchListItemModel> getBranchList(){
		return branchList;
	}

	public List<SuggestBranchListItemModel> getSuggestBranchList(){
		return suggestBranchList;
	}
}