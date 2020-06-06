
package com.assignment.spring.openweatherapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "coord",
        "weather",
        "base",
        "main",
        "visibility",
        "wind",
        "clouds",
        "dt",
        "sys",
        "id",
        "name",
        "cod"
})
@Getter
@ToString
@Builder
public class WeatherResponse {

    @Getter
    private Coord coord;
    @Getter
    private List<Weather> weather;
    @Getter
    private String base;
    @Getter
    @JsonProperty("main")
    private MainInfo mainInfo;
    @Getter
    private Integer visibility;
    @Getter
    private Wind wind;
    @Getter
    private Clouds clouds;
    @Getter
    private Integer dt;
    @Getter
    private Sys sys;
    @Getter
    private Integer id;
    @Getter
    private String name;
    @Getter
    private Integer cod;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
