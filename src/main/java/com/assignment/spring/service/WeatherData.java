package com.assignment.spring.service;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class WeatherData {
    private String city;

    private String country;

    private Double temperature;

}
