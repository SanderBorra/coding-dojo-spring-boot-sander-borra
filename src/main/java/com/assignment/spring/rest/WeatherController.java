package com.assignment.spring.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.spring.exceptions.MissingParameterException;
import com.assignment.spring.service.WeatherData;
import com.assignment.spring.service.WeatherService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
// REST Protocol interface
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(final WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @RequestMapping(value = "/weather", method = GET)
    @ResponseStatus(value = HttpStatus.OK)
    public WeatherData weather(@RequestParam(value = "city", required = false) final Optional<String> city) {
        log.info("Serving /weather/{}", city.orElse(""));
        return city
                .map(weatherService::getWeatherForCity)
                // De gebruikte weather API URL is expliciet voor een enkele city
                .orElseThrow(() -> new MissingParameterException("parameter city voor request is verplicht"));
    }
}
