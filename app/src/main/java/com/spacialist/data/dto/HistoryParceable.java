package com.spacialist.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryParceable implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public HistoryParceable createFromParcel(Parcel in) {
            return new HistoryParceable(in);
        }

        public HistoryParceable[] newArray(int size) {
            return new HistoryParceable[size];
        }
    };

    private String userId;
    private String transactionId;
    private String paymentId;
    private String amount;
    private String transactionDate;
    private String appointmentId;
    private String dtiNo;
    private String busName;
    private String bus_address;
    private String startTime;
    private String endTime;
    private String appointmentDate;
    private String serviceName;
    private String firstname;
    private String appointmentStatus;

    public HistoryParceable(Parcel in) {
        this.userId = in.readString();
        this.transactionId = in.readString();
        this.paymentId = in.readString();
        this.amount = in.readString();
        this.transactionDate = in.readString();
        this.appointmentId = in.readString();
        this.dtiNo = in.readString();
        this.busName = in.readString();
        this.bus_address = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.appointmentDate = in.readString();
        this.serviceName = in.readString();
        this.firstname = in.readString();
        this.appointmentStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.userId);
        parcel.writeString(this.transactionId);
        parcel.writeString(this.paymentId);
        parcel.writeString(this.amount);
        parcel.writeString(this.transactionDate);
        parcel.writeString(this.appointmentId);
        parcel.writeString(this.dtiNo);
        parcel.writeString(this.busName);
        parcel.writeString(this.bus_address);
        parcel.writeString(this.startTime);
        parcel.writeString(this.endTime);
        parcel.writeString(this.appointmentDate);
        parcel.writeString(this.serviceName);
        parcel.writeString(this.firstname);
        parcel.writeString(this.appointmentStatus);
    }

    public HistoryParceable(String userId, String transactionId, String paymentId, String amount,
                            String transactionDate, String appointmentId, String dtiNo, String busName,
                            String bus_address, String startTime, String endTime, String appointmentDate,
                            String serviceName, String firstname, String appointmentStatus) {
        this.userId = userId;
        this.transactionId = transactionId;
        this.paymentId = paymentId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.appointmentId = appointmentId;
        this.dtiNo = dtiNo;
        this.busName = busName;
        this.bus_address = bus_address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.appointmentDate = appointmentDate;
        this.serviceName = serviceName;
        this.firstname = firstname;
        this.appointmentStatus = appointmentStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static Creator getCREATOR() {
        return CREATOR;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDtiNo() {
        return dtiNo;
    }

    public void setDtiNo(String dtiNo) {
        this.dtiNo = dtiNo;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBus_address() {
        return bus_address;
    }

    public void setBus_address(String bus_address) {
        this.bus_address = bus_address;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    @Override
    public String toString() {
        return "HistoryParceable{" +
                "userId='" + userId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", amount='" + amount + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                ", dtiNo='" + dtiNo + '\'' +
                ", busName='" + busName + '\'' +
                ", bus_address='" + bus_address + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", firstname='" + firstname + '\'' +
                ", appointmentStatus='" + appointmentStatus + '\'' +
                '}';
    }
}