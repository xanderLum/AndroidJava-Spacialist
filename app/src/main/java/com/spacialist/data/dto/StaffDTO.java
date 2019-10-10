package com.spacialist.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "staff_service_id",
        "service_id",
        "staff_id",
        "firstname",
        "staff_logo"
})
public class StaffDTO implements Serializable {

    @JsonProperty("staff_service_id")
    private String staffServiceId;

    @JsonProperty("service_id")
    private String serviceId;

    @JsonProperty("staff_id")
    private String staffId;

    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("staff_logo")
    private String staffLogo;

    public String getStaffServiceId() {
        return staffServiceId;
    }

    public void setStaffServiceId(String staffServiceId) {
        this.staffServiceId = staffServiceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getStaffLogo() {
        return staffLogo;
    }

    public void setStaffLogo(String staffLogo) {
        this.staffLogo = staffLogo;
    }

    @Override
    public String toString() {
        return "StaffDTO{" +
                "staffServiceId='" + staffServiceId + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", staffId='" + staffId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", staffLogo='" + staffLogo + '\'' +
                '}';
    }

}
