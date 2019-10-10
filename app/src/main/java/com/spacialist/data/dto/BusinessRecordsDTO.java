package com.spacialist.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude
public class BusinessRecordsDTO implements Serializable {
    @JsonProperty("records")
    private List<SpaBusinessJSON> spaBusinessDTOs;

    public List<SpaBusinessJSON> getSpaBusinessDTOs() {
        return spaBusinessDTOs;
    }

    public void setSpaBusinessDTOs(List<SpaBusinessJSON> spaBusinessDTOs) {
        this.spaBusinessDTOs = spaBusinessDTOs;
    }

    @Override
    public String toString() {
        return "BusinessRecordsDTO{" +
                "spaBusinessDTOs=" + spaBusinessDTOs +
                '}';
    }
}
