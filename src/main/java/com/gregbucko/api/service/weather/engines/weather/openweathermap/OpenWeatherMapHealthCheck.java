package com.gregbucko.api.service.weather.engines.weather.openweathermap;

import com.codahale.metrics.health.HealthCheck;
import org.openweathermap.api.DataWeatherClient;
import org.openweathermap.api.UrlConnectionDataWeatherClient;
import org.openweathermap.api.query.*;
import org.openweathermap.api.query.currentweather.CurrentWeatherOneLocationQuery;

public class OpenWeatherMapHealthCheck extends HealthCheck {
    private final String apiKey;

    public OpenWeatherMapHealthCheck(final String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Ideally this function should call a health check of the OpenWeatherMap, however I could not find it amongst their
     * APIs nor the client library. I decided to make a call to get current weather for London, UK as a way to determine
     * whether OpenWeatherMap is accessible or not. If OpenWeatherMap will be down the getCurrentWeather should throw
     * an exception which will result in unhealthy response from this function.
     *
     * @return Health check result
     */
    protected Result check() {
        DataWeatherClient client = new UrlConnectionDataWeatherClient(apiKey);

        CurrentWeatherOneLocationQuery currentWeatherOneLocationQuery = QueryBuilderPicker.pick()
            .currentWeather()
            .oneLocation()
            .byCityName("London")
            .countryCode("UK")
            .build();

        try {
            client.getCurrentWeather(currentWeatherOneLocationQuery);
        } catch(Exception e) {
            return Result.unhealthy("The connection with Open Weather Map could not be established.");
        }

        return Result.healthy();
    }
}
