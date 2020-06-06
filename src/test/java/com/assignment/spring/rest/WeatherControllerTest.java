package com.assignment.spring.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.assignment.spring.exceptions.MissingParameterException;
import com.assignment.spring.exceptions.NotFoundException;
import com.assignment.spring.service.WeatherData;
import com.assignment.spring.service.WeatherService;

@RunWith(MockitoJUnitRunner.class)
public class WeatherControllerTest {

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    @Before
    public void before() {
        var weatherData = WeatherData.builder().city("city").country("country").temperature(100.0).build();
        when(weatherService.getWeatherForCity(anyString())).thenReturn(weatherData);
    }

    @Test
    public void happyFlow() {
        var weatherData = weatherController.weather(Optional.of("city"));
        assertNotNull(weatherData);
        assertEquals("city", weatherData.getCity());
        assertEquals("Verwachte waarde is 100.0", (Double) 100.0, weatherData.getTemperature());
    }
    
    @Test(expected = NotFoundException.class)
    public void notFound() {
        when(weatherService.getWeatherForCity(anyString())).thenThrow(new NotFoundException("City not found"));
        weatherController.weather(Optional.of("city"));
    }
    
    @Test(expected = MissingParameterException.class)
    public void noCity() {
        weatherController.weather(Optional.empty());  
    }

}
