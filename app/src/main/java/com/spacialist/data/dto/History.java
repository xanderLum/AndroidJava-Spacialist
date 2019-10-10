package com.spacialist.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "user_id",
        "transaction_id",
        "payment_id",
        "amount",
        "transaction_date",
        "appointment_id",
        "dti_no",
        "bus_name",
        "bus_address",
        "start_time",
        "end_time",
        "appointment_date",
        "service_name",
        "firstname",
        "status"
})
public class History {

    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("payment_id")
    private String paymentId;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("transaction_date")
    private String transactionDate;
    @JsonProperty("appointment_id")
    private String appointmentId;
    @JsonProperty("dti_no")
    private String dtiNo;
    @JsonProperty("bus_name")
    private String busName;
    @JsonProperty("bus_address")
    private String bus_address;
    @JsonProperty("start_time")
    private String startTime;
    @JsonProperty("end_time")
    private String endTime;
    @JsonProperty("appointment_date")
    private String appointmentDate;
    @JsonProperty("service_name")
    private String serviceName;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("status")
    private String status;

    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("transaction_id")
    public String getTransactionId() {
        return transactionId;
    }

    @JsonProperty("transaction_id")
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @JsonProperty("payment_id")
    public String getPaymentId() {
        return paymentId;
    }

    @JsonProperty("payment_id")
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    @JsonProperty("amount")
    public String getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(String amount) {
        this.amount = amount;
    }

    @JsonProperty("transaction_date")
    public String getTransactionDate() {
        return transactionDate;
    }

    @JsonProperty("transaction_date")
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    @JsonProperty("appointment_id")
    public String getAppointmentId() {
        return appointmentId;
    }

    @JsonProperty("appointment_id")
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    @JsonProperty("dti_no")
    public String getDtiNo() {
        return dtiNo;
    }

    @JsonProperty("dti_no")
    public void setDtiNo(String dtiNo) {
        this.dtiNo = dtiNo;
    }

    @JsonProperty("bus_name")
    public String getBusName() {
        return busName;
    }

    @JsonProperty("bus_name")
    public void setBusName(String busName) {
        this.busName = busName;
    }

    @JsonProperty("start_time")
    public String getStartTime() {
        return startTime;
    }

    @JsonProperty("start_time")
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @JsonProperty("end_time")
    public String getEndTime() {
        return endTime;
    }

    @JsonProperty("end_time")
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @JsonProperty("appointment_date")
    public String getAppointmentDate() {
        return appointmentDate;
    }

    @JsonProperty("appointment_date")
    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    @JsonProperty("service_name")
    public String getServiceName() {
        return serviceName;
    }

    @JsonProperty("service_name")
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @JsonProperty("firstname")
    public String getFirstname() {
        return firstname;
    }

    @JsonProperty("firstname")
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getBus_address() {
        return bus_address;
    }

    public void setBus_address(String bus_address) {
        this.bus_address = bus_address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}