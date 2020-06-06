package com.assignment.spring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.assignment.spring.exceptions.NotFoundException;
import com.assignment.spring.exceptions.SystemErrorException;
import com.assignment.spring.exceptions.UserException;
import com.assignment.spring.openweatherapi.MainInfo;
import com.assignment.spring.openweatherapi.Sys;
import com.assignment.spring.openweatherapi.WeatherResponse;
import com.assignment.spring.resource.WeatherEntity;
import com.assignment.spring.resource.WeatherRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeatherService {

    private final RestTemplate restTemplate;
    private final WeatherRepository weatherRepository;
    private final String weatherApiUrl;
    private final String appId;

    @Autowired
    public WeatherService(final RestTemplate restTemplate, final WeatherRepository weatherRepository, final String weatherApiUrl, final String appId) {
        this.restTemplate = restTemplate;
        this.weatherRepository = weatherRepository;
        this.weatherApiUrl = weatherApiUrl;
        this.appId = appId;
    }

    /**
     * Haal 'het weer' op voor een gegeven stad
     * 
     * @param city - de stad waar het weer voor opgehaald moet worden
     * @return - weer info
     */
    public WeatherData getWeatherForCity(final String city) {

        log.debug("getWeatherForCity: {}", city);

        final var weatherResponse = callWeatherService(city);

        return Optional.ofNullable(weatherResponse)
                .map(WeatherService::transformToWeatherEntity) // transformeer weather service data naar database entity
                .map(this::saveWeather) // sla database entity op
                .map(WeatherService::transformToWeatherData) // transformeer database entity naar service output data
                .orElseThrow(() -> new SystemErrorException("Fout opgetreden in mapper"));
    }

    /*
     * Sla weerdata op in database, maak database fouten expliciet
     */
    private WeatherEntity saveWeather(final WeatherEntity weatherEntity) {
        try {
            return weatherRepository.save(weatherEntity);
        } catch (final RuntimeException e) {
            log.debug("FAIL", e);
            throw new SystemErrorException(e);
        }
    }

    /*
     * Roep Weather service aan, handel eventuele functionele en technische fouten
     * af en geef de response body als Java POJO terug
     */
    private WeatherResponse callWeatherService(final String city) {
        final var url = weatherApiUrl.replace("{city}", city).replace("{appId}", appId);
        log.info("Call naar {}", url);
        try {
            final var response = restTemplate.getForEntity(url, WeatherResponse.class);
            log.info("response: {}", response);
            return response.getBody();
        } catch (final HttpClientErrorException e) {
            log.debug("FAIL", e);
            final var statusCode = e.getStatusCode();
            if (statusCode.equals(HttpStatus.NOT_FOUND)) {
                throw new NotFoundException("Geen gegevens gevonden voor: " + city);
            }
            if (statusCode.is4xxClientError()) {
                // Deze fout mag alleen gelogd worden
                throw new UserException(e.getResponseBodyAsString());
            }
            // Algemene foutmelding naar gebruiker
            throw new SystemErrorException(e);
        }
    }

    /*
     * Transformeer extern formaat naar database formaat
     */
    private static WeatherEntity transformToWeatherEntity(final WeatherResponse weatherResponse) {
        final var country = Optional.ofNullable(weatherResponse.getSys())
                .map(Sys::getCountry)
                .orElseThrow(() -> new SystemErrorException("Geen country in antwoord van Weather service"));
        final var temp = Optional.ofNullable(weatherResponse.getMainInfo())
                .map(MainInfo::getTemp)
                .orElseThrow(() -> new SystemErrorException("Geen temperature in antwoord van Weather service"));
        return WeatherEntity.builder().city(weatherResponse.getName()).country(country).temperature(temp).build();
    }

    /*
     * Transformeer van database formaat naar definitief resultaat
     */
    private static WeatherData transformToWeatherData(final WeatherEntity weatherEntity) {
        log.info("Gegenereerde database id: {}", weatherEntity.getId());
        return WeatherData.builder()
                .city(weatherEntity.getCity())
                .country(weatherEntity.getCountry())
                .temperature(weatherEntity.getTemperature())
                .build();
    }

}
