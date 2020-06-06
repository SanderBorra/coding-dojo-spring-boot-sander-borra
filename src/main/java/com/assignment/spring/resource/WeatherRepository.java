package com.assignment.spring.resource;

import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<WeatherEntity, Integer> {
}
