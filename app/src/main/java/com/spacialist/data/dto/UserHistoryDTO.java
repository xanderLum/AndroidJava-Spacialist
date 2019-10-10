package com.spacialist.data.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "history"
})
public class UserHistoryDTO {

    @JsonProperty("history")
    private List<History> history = null;

    @JsonProperty("history")
    public List<History> getHistory() {
        return history;
    }

    @JsonProperty("history")
    public void setHistory(List<History> history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "UserHistoryDTO{" +
                "history=" + history +
                '}';
    }
}