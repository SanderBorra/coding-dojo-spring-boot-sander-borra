package com.assignment.spring.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.NonTransientDataAccessResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
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

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherService weatherService;

    @Before
    public void before() {
        ReflectionTestUtils.setField(weatherService, "weatherApiUrl", "${city}_${appId}");
        ReflectionTestUtils.setField(weatherService, "appId", "test");
        final var mainInfo = MainInfo.builder().temp(100.0).build();
        final var sys = Sys.builder().country("NL").build();
        final var weatherResponse =  WeatherResponse.builder().mainInfo(mainInfo).sys(sys).name("city").build();
        
        final var weatherEntity = WeatherEntity.builder().city("city").id(1).country("NL").temperature(100.0).build();
        when(weatherRepository.save(any())).thenReturn(weatherEntity);
        final ResponseEntity<Object> responseEntity = new ResponseEntity<>(weatherResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(responseEntity);
    }

    @Test
    public void happyFlow() {
        final var result = weatherService.getWeatherForCity("city");
        assertNotNull(result);
    }
    
    @Test(expected = SystemErrorException.class)
    public void databaseError() {
        when(weatherRepository.save(any())).thenThrow(new NonTransientDataAccessResourceException("database error")) ;
        weatherService.getWeatherForCity("city");    
    }
    
    @Test(expected = NotFoundException.class)
    public void weatherServiceNotFound() {

        HttpClientErrorException notFoundException = new HttpClientErrorException(HttpStatus.NOT_FOUND);
        when(restTemplate.getForEntity(anyString(), any())).thenThrow(notFoundException);
        weatherService.getWeatherForCity("city");    
    }
    
    @Test(expected = UserException.class)
    public void weatherServiceUserError() {

        HttpClientErrorException userException = new HttpClientErrorException(HttpStatus.METHOD_NOT_ALLOWED);
        when(restTemplate.getForEntity(anyString(), any())).thenThrow(userException);
        weatherService.getWeatherForCity("city");    
    }
    
    @Test(expected = SystemErrorException.class)
    public void weatherServiceError() {

        HttpClientErrorException userException = new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.getForEntity(anyString(), any())).thenThrow(userException);
        weatherService.getWeatherForCity("city");    
    }

}
