package com.spacialist.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.spacialist.data.Constants;

public class SpaBusinessDTO implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SpaBusinessDTO createFromParcel(Parcel in) {
            return new SpaBusinessDTO(in);
        }

        public SpaBusinessDTO[] newArray(int size) {
            return new SpaBusinessDTO[size];
        }
    };

    private int busId;
    private String bus_name;
    private String owner;
    private String address;
    private String contactNo;
    private String mapsLatitude;
    private String mapsLongitude;
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

    public SpaBusinessDTO(Parcel in) {
        this.busId = in.readInt();
        this.bus_name = in.readString();
        this.owner = in.readString();
        this.address = in.readString();
        this.contactNo = in.readString();
        this.mapsLatitude = in.readString();
        this.mapsLongitude = in.readString();
        this.logo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.busId);
        parcel.writeString(this.bus_name);
        parcel.writeString(this.owner);
        parcel.writeString(this.address);
        parcel.writeString(this.contactNo);
        parcel.writeString(this.mapsLatitude);
        parcel.writeString(this.mapsLongitude);
        parcel.writeString(Constants.IP + this.logo);
    }
}
