package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp;

import java.io.Serializable;

public class GetDraftedAppsVerfiyOtpPostPagination implements Serializable {
    public int page;
    public int size;
    public Object totalPages;
    public Object totalElements;
    public Object sortOrder;
    public Object sortBy;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Object getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Object totalPages) {
        this.totalPages = totalPages;
    }

    public Object getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Object totalElements) {
        this.totalElements = totalElements;
    }

    public Object getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Object sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Object getSortBy() {
        return sortBy;
    }

    public void setSortBy(Object sortBy) {
        this.sortBy = sortBy;
    }
}