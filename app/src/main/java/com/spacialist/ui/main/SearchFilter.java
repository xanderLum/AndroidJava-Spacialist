package com.spacialist.ui.main;

import java.util.Date;

public class SearchFilter {
    private String latitude;
    private String longitude;

    private Date date;
    private Date time;

    //format HH:MM 24hr format
    private String timeString;
    //format dd/MMM/yyyy 08/Aug/2019
    private String dateString;

    private String guestRating;
    private String starRating;

    private double price;
    private String busId;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getGuestRating() {
        return guestRating;
    }

    public void setGuestRating(String guestRating) {
        this.guestRating = guestRating;
    }

    public String getStarRating() {
        return starRating;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}
