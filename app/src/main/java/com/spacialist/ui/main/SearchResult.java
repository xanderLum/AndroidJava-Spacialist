package com.spacialist.ui.main;

import androidx.annotation.Nullable;

import com.spacialist.data.dto.History;
import com.spacialist.data.dto.HistoryParceable;
import com.spacialist.data.dto.SpaBusinessEntry;
import com.spacialist.data.dto.SpaServiceEntry;
import com.spacialist.data.dto.StaffDTO;
import com.spacialist.data.model.BusinessDetails;

import java.util.List;

public class SearchResult {
    List<SpaBusinessEntry> spaList;
    List<SpaServiceEntry> serviceList;
    List<StaffDTO> serviceIdsAvailableByBookingTime;
    List<HistoryParceable> historyList;

    @Nullable
    private Integer error;

    public SearchResult(@Nullable Integer error) {
        this.error = error;
    }

    public SearchResult() {
    }

    @Nullable
    public Integer getError() {
        return error;
    }

    public SearchResult(List<SpaBusinessEntry> spaList) {
        this.spaList = spaList;
    }

    public List<SpaBusinessEntry> getSpaList() {
        return spaList;
    }

    public void setServiceList(List<SpaServiceEntry> serviceList) {
        this.serviceList = serviceList;
    }

    public List<SpaServiceEntry> getServiceList() {
        return serviceList;
    }

    public List<StaffDTO> getServiceIdsAvailableByBookingTime() {
        return serviceIdsAvailableByBookingTime;
    }

    public void setServiceIdsAvailableByBookingTime(List<StaffDTO> serviceIdsAvailableByBookingTime) {
        this.serviceIdsAvailableByBookingTime = serviceIdsAvailableByBookingTime;
    }

    public List<HistoryParceable> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<HistoryParceable> historyList) {
        this.historyList = historyList;
    }
}
