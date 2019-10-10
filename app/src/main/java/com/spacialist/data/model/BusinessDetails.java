package com.spacialist.data.model;

import java.io.File;
import java.util.List;

public class BusinessDetails {

    private File logo;
    private String businessName;
    private String address;
    private String latitude;
    private String longitude;

    private float minServicePrice;

    private String starRating;
    private int noOfReviews;
    private String guestRating;
    private String spaBusinessId;

    private List<File> gallery;

    public File getLogo() {
        return logo;
    }

    public void setLogo(File logo) {
        this.logo = logo;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public float getMinServicePrice() {
        return minServicePrice;
    }

    public void setMinServicePrice(float minServicePrice) {
        this.minServicePrice = minServicePrice;
    }

    public String getStarRating() {
        return starRating;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
    }

    public int getNoOfReviews() {
        return noOfReviews;
    }

    public void setNoOfReviews(int noOfReviews) {
        this.noOfReviews = noOfReviews;
    }

    public String getGuestRating() {
        return guestRating;
    }

    public void setGuestRating(String guestRating) {
        this.guestRating = guestRating;
    }

    public String getSpaBusinessId() {
        return spaBusinessId;
    }

    public void setSpaBusinessId(String spaBusinessId) {
        this.spaBusinessId = spaBusinessId;
    }

    public List<File> getGallery() {
        return gallery;
    }

    public void setGallery(List<File> gallery) {
        this.gallery = gallery;
    }
}
