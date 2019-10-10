package com.spacialist.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "bus_id",
        "bus_name",
        "service_id",
        "service_name",
        "service_desc",
        "duration",
        "service_type",
        "price",
        "staff"
})
public class ServiceDTO implements Serializable {
    @JsonProperty("bus_id")
    private String busId;
    @JsonProperty("bus_name")
    private String busName;
    @JsonProperty("service_id")
    private String serviceId;
    @JsonProperty("service_name")
    private String serviceName;
    @JsonProperty("service_desc")
    private String serviceDesc;
    @JsonProperty("duration")
    private String duration;
    @JsonProperty("price")
    private String price;
    @JsonProperty("service_type")
    private String serviceType;

    @JsonProperty("staff")
    private StaffDTO staff;

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public StaffDTO getStaff() {
        return staff;
    }

    public void setStaff(StaffDTO staff) {
        this.staff = staff;
    }

    @Override
    public String toString() {
        return "ServiceDTO{" +
                "busId='" + busId + '\'' +
                ", busName='" + busName + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", serviceDesc='" + serviceDesc + '\'' +
                ", duration='" + duration + '\'' +
                ", price='" + price + '\'' +
                ", service_type='" + serviceType + '\'' +
                ", staff=" + staff +
                '}';
    }
}
