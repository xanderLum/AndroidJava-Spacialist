package com.spacialist.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDetailsDTO implements Serializable {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("bus_id")
    private String busId;
    @JsonProperty("payment_id")
    private String paymentId;
    @JsonProperty("payment_details")
    private String paymentDetails;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("staff_id")
    private String staff_id;
    @JsonProperty("staff_service_id")
    private String staff_service_id;
    @JsonProperty("start_time")
    private String startTime;
    @JsonProperty("end_time")
    private String endTime;
    @JsonProperty("sched_date")
    private String schedDate;
    @JsonProperty("appointment_name")
    private String appointmentName;
    @JsonProperty("appointment_desc")
    private String appointmentDesc;
    @JsonProperty("payment_transaction_id")
    private String paymentTransactionId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getStaff_service_id() {
        return staff_service_id;
    }

    public void setStaff_service_id(String staff_service_id) {
        this.staff_service_id = staff_service_id;
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

    public String getSchedDate() {
        return schedDate;
    }

    public void setSchedDate(String schedDate) {
        this.schedDate = schedDate;
    }

    public String getAppointmentName() {
        return appointmentName;
    }

    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName;
    }

    public String getAppointmentDesc() {
        return appointmentDesc;
    }

    public void setAppointmentDesc(String appointmentDesc) {
        this.appointmentDesc = appointmentDesc;
    }

    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }

    @Override
    public String toString() {
        return "PaymentDetailsDTO{" +
                "userId='" + userId + '\'' +
                ", busId='" + busId + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", paymentDetails='" + paymentDetails + '\'' +
                ", amount='" + amount + '\'' +
                ", staff_id='" + staff_id + '\'' +
                ", staff_service_id='" + staff_service_id + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", schedDate='" + schedDate + '\'' +
                ", appointmentName='" + appointmentName + '\'' +
                ", appointmentDesc='" + appointmentDesc + '\'' +
                ", paymentTransactionId='" + paymentTransactionId + '\'' +
                '}';
    }
}
