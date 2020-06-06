package com.assignment.spring.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource(name = "generic", value = "classpath:application.yaml")
@PropertySource(name = "Env", value = "classpath:application-${Env}.yaml", ignoreResourceNotFound = true)
public class ApplicationConfiguration {

    @Value("${WEATHER_API_URL}")
    private String weatherApiUrl;

    @Value("${APP_ID}")
    private String appId;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean(name = "weatherApiUrl")
    public String getWeatherApiUrl() {
        return weatherApiUrl;
    }
    
    @Bean(name = "appId")
    public String getAppId() {
        return appId;
    }
}
