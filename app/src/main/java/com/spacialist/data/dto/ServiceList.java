package com.spacialist.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "services"
})
public class ServiceList implements Serializable {

    @JsonProperty("services")
    private List<ServiceDTO> services;

    public List<ServiceDTO> getServices() {
        return services;
    }

    public void setServices(List<ServiceDTO> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "ServiceList{" +
                "services=" + services +
                '}';
    }
}
