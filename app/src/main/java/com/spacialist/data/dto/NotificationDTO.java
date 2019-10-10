package com.spacialist.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDTO implements Serializable {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("bus_id")
    private String busId;
    @JsonProperty("appointment_id")
    private String appointmentId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("message")
    private String message;
    @JsonProperty("image_url")
    private String image_url;
    @JsonProperty("action")
    private String action;
    @JsonProperty("action_destination")
    private String action_destination;
    @JsonProperty("firebase_token")
    private String firebaseToken;
    @JsonProperty("firebase_api")
    private String firebaseAPI;

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

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction_destination() {
        return action_destination;
    }

    public void setAction_destination(String action_destination) {
        this.action_destination = action_destination;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "userId='" + userId + '\'' +
                ", busId='" + busId + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", image_url='" + image_url + '\'' +
                ", action='" + action + '\'' +
                ", action_destination='" + action_destination + '\'' +
                '}';
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getFirebaseAPI() {
        return firebaseAPI;
    }

    public void setFirebaseAPI(String firebaseAPI) {
        this.firebaseAPI = firebaseAPI;
    }
}
