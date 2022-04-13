package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details;

import java.io.Serializable;

public class GetConsumerAccountDetailsResponseStepperSections implements Serializable {


    public boolean SETUP_ACCOUNT_BANKING_MODE;
    public boolean SETUP_ACCOUNT_TYPE;
    public boolean SETUP_ACCOUNT_INCOME;
    public boolean PERSONAL_DETAIL_NAMES;
    public boolean PERSONAL_DETAIL_ADDRESS;
    public boolean PERSONAL_DETAIL_EMPLOYMENT;
    public boolean TRANSACTIONAL_DETAIL;
    public boolean DOCUMENT_UPLOADER;


    public boolean isSETUP_ACCOUNT_BANKING_MODE() {
        return SETUP_ACCOUNT_BANKING_MODE;
    }

    public void setSETUP_ACCOUNT_BANKING_MODE(boolean SETUP_ACCOUNT_BANKING_MODE) {
        this.SETUP_ACCOUNT_BANKING_MODE = SETUP_ACCOUNT_BANKING_MODE;
    }

    public boolean isSETUP_ACCOUNT_TYPE() {
        return SETUP_ACCOUNT_TYPE;
    }

    public void setSETUP_ACCOUNT_TYPE(boolean SETUP_ACCOUNT_TYPE) {
        this.SETUP_ACCOUNT_TYPE = SETUP_ACCOUNT_TYPE;
    }

    public boolean isSETUP_ACCOUNT_INCOME() {
        return SETUP_ACCOUNT_INCOME;
    }

    public void setSETUP_ACCOUNT_INCOME(boolean SETUP_ACCOUNT_INCOME) {
        this.SETUP_ACCOUNT_INCOME = SETUP_ACCOUNT_INCOME;
    }

    public boolean isPERSONAL_DETAIL_NAMES() {
        return PERSONAL_DETAIL_NAMES;
    }

    public void setPERSONAL_DETAIL_NAMES(boolean PERSONAL_DETAIL_NAMES) {
        this.PERSONAL_DETAIL_NAMES = PERSONAL_DETAIL_NAMES;
    }

    public boolean isPERSONAL_DETAIL_ADDRESS() {
        return PERSONAL_DETAIL_ADDRESS;
    }

    public void setPERSONAL_DETAIL_ADDRESS(boolean PERSONAL_DETAIL_ADDRESS) {
        this.PERSONAL_DETAIL_ADDRESS = PERSONAL_DETAIL_ADDRESS;
    }

    public boolean isPERSONAL_DETAIL_EMPLOYMENT() {
        return PERSONAL_DETAIL_EMPLOYMENT;
    }

    public void setPERSONAL_DETAIL_EMPLOYMENT(boolean PERSONAL_DETAIL_EMPLOYMENT) {
        this.PERSONAL_DETAIL_EMPLOYMENT = PERSONAL_DETAIL_EMPLOYMENT;
    }

    public boolean isTRANSACTIONAL_DETAIL() {
        return TRANSACTIONAL_DETAIL;
    }

    public void setTRANSACTIONAL_DETAIL(boolean TRANSACTIONAL_DETAIL) {
        this.TRANSACTIONAL_DETAIL = TRANSACTIONAL_DETAIL;
    }

    public boolean isDOCUMENT_UPLOADER() {
        return DOCUMENT_UPLOADER;
    }

    public void setDOCUMENT_UPLOADER(boolean DOCUMENT_UPLOADER) {
        this.DOCUMENT_UPLOADER = DOCUMENT_UPLOADER;
    }
}
