package com.spacialist.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude
public class SpaBusinessJSON implements Serializable {

    @JsonProperty("bus_id")
    private int busId;
    @JsonProperty("bus_name")
    private String bus_name;
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("address")
    private String address;
    @JsonProperty("contact_no")
    private String contactNo;
    @JsonProperty("maps_latitude")
    private String mapsLatitude;
    @JsonProperty("maps_longitude")
    private String mapsLongitude;
    @JsonProperty("bus_logo")
    private String logo;

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getBus_name() {
        return bus_name;
    }

    public void setBus_name(String bus_name) {
        this.bus_name = bus_name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getMapsLatitude() {
        return mapsLatitude;
    }

    public void setMapsLatitude(String mapsLatitude) {
        this.mapsLatitude = mapsLatitude;
    }

    public String getMapsLongitude() {
        return mapsLongitude;
    }

    public void setMapsLongitude(String mapsLongitude) {
        this.mapsLongitude = mapsLongitude;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "SpaBusinessDTO{" +
                "busId=" + busId +
                ", bus_name='" + bus_name + '\'' +
                ", owner='" + owner + '\'' +
                ", address='" + address + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", mapsLatitude='" + mapsLatitude + '\'' +
                ", mapsLongitude='" + mapsLongitude + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }

}
