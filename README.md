Spring Boot Coding Dojo adaptation by Sander Borra
---

Welcome to the Spring Boot Coding Dojo! (by Sander Borra)
This excercise is based on the code by Marcos Barbero (https://github.com/marcosbarbero/coding-dojo-spring-boot)

### Introduction

This project implements a Rest API that will store current weather information for a given city in a database and also will return the data to the requester.
The name of the city cannot be empty and must match one of the names of a city the OpenWeather API is familiar with otherwise an error will be returned.

### The task

The original code was intentionally not production ready, an attempt was made to fix that

## Usage
To run the application, configuration must be entered
Configuration can be found in application.yaml and can be overriden by a platform specfic configuration
I.e. for production create a application.prod.yaml and specify an environment variable 'Env=prod' for startup

### Code on GitHub

See: https://github.com/SanderBorra/coding-dojo-spring-boot-sander-borra

### Footnote
It's possible to generate the API key going to the [OpenWeather Sign up](https://openweathermap.org/appid) page.
