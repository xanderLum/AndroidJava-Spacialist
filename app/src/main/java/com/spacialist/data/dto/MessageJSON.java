package com.spacialist.data.dto;

import androidx.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageJSON implements Serializable {
    @JsonProperty("appointment_id")
    private String appointmentId;

    @JsonProperty("message")
    private String message;

    private RegisterDTO updatedProfile;

    @Nullable
    private Integer error;

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Nullable
    public Integer getError() {
        return error;
    }

    public void setError(@Nullable Integer error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "MessageJSON{" +
                "message='" + message + '\'' +
                '}';
    }

    public RegisterDTO getUpdatedProfile() {
        return updatedProfile;
    }

    public void setUpdatedProfile(RegisterDTO updatedProfile) {
        this.updatedProfile = updatedProfile;
    }
}
