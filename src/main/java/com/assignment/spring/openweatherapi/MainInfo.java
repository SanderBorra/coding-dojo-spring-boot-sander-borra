
package com.assignment.spring.openweatherapi;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "temp",
        "pressure",
        "humidity",
        "temp_min",
        "temp_max"
})
@ToString
@Builder
public class MainInfo {

    @Getter
    private Double temp;

    @Getter
    private Integer pressure;

    @Getter
    private Integer humidity;
    
    @JsonProperty("temp_min")
    @Getter
    private Double tempMin;
    
    @JsonProperty("temp_max")
    @Getter
    private Double tempMax;
    
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

}
