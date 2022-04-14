package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode;

import java.io.Serializable;

public class StepperSectionsResponse implements Serializable {
	private boolean pERSONALDETAILNAMES;
	private boolean pERSONALDETAILEMPLOYMENT;
	private boolean tRANSACTIONALDETAIL;
	private boolean sETUPACCOUNTTYPE;
	private boolean pERSONALDETAILADDRESS;
	private boolean sETUPACCOUNTBANKINGMODE;
	private boolean dOCUMENTUPLOADER;
	private boolean sETUPACCOUNTINCOME;

	public boolean isPERSONALDETAILNAMES(){
		return pERSONALDETAILNAMES;
	}

	public boolean isPERSONALDETAILEMPLOYMENT(){
		return pERSONALDETAILEMPLOYMENT;
	}

	public boolean isTRANSACTIONALDETAIL(){
		return tRANSACTIONALDETAIL;
	}

	public boolean isSETUPACCOUNTTYPE(){
		return sETUPACCOUNTTYPE;
	}

	public boolean isPERSONALDETAILADDRESS(){
		return pERSONALDETAILADDRESS;
	}

	public boolean isSETUPACCOUNTBANKINGMODE(){
		return sETUPACCOUNTBANKINGMODE;
	}

	public boolean isDOCUMENTUPLOADER(){
		return dOCUMENTUPLOADER;
	}

	public boolean isSETUPACCOUNTINCOME(){
		return sETUPACCOUNTINCOME;
	}
}
