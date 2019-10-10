package com.spacialist.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class StaffParcelable implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public StaffParcelable createFromParcel(Parcel in) {
            return new StaffParcelable(in);
        }

        public StaffParcelable[] newArray(int size) {
            return new StaffParcelable[size];
        }
    };

    public final String staffServiceId;

    public final String serviceId;

    public final String staffId;

    public final String firstName;

    public final String staffLogo;

    public StaffParcelable(Parcel in) {
        this.staffServiceId = in.readString();
        this.serviceId = in.readString();
        this.staffId = in.readString();
        this.firstName = in.readString();
        this.staffLogo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.staffServiceId);
        parcel.writeString(this.serviceId);
        parcel.writeString(this.staffId);
        parcel.writeString(this.firstName);
        parcel.writeString(this.staffLogo);
    }

    public StaffParcelable(String staffServiceId, String serviceId, String staffId, String firstName,
                           String staffLogo) {
        this.staffServiceId = staffServiceId;
        this.serviceId = serviceId;
        this.staffId = staffId;
        this.firstName = firstName;
        this.staffLogo = staffLogo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static Creator getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "StaffParcelable{" +
                "staffServiceId='" + staffServiceId + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", staffId='" + staffId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", staffLogo='" + staffLogo + '\'' +
                '}';
    }
}
