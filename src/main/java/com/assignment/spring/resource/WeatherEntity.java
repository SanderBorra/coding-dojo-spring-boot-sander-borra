package com.assignment.spring.resource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "weather")
@Builder
@Getter
@ToString
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String city;

    private String country;

    private Double temperature;
}
