package com.spacialist.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonInclude
public class AuditFieldsDTO {
    @JsonProperty("created_by")
    private String createdBy;
    @JsonProperty("created_dt")
    private Date createdDt;
    @JsonProperty("last_updated_by")
    private String lastUpdatedBy;
    @JsonProperty("last_updated_dt")
    private Date lastUpdatedDt;
    @JsonProperty("ver_no")
    private int verNo;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public Date getLastUpdatedDt() {
        return lastUpdatedDt;
    }

    public void setLastUpdatedDt(Date lastUpdatedDt) {
        this.lastUpdatedDt = lastUpdatedDt;
    }

    public int getVerNo() {
        return verNo;
    }

    public void setVerNo(int verNo) {
        this.verNo = verNo;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Override
    public String toString() {
        return "AuditFieldsDTO{" +
                "createdBy='" + createdBy + '\'' +
                ", createdDt=" + createdDt +
                ", lastUpdatedBy='" + lastUpdatedBy + '\'' +
                ", lastUpdatedDt=" + lastUpdatedDt +
                ", verNo=" + verNo +
                '}';
    }
}
