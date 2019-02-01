package com.gregbucko.api.service.weather.engines.weather.openweathermap;

import com.gregbucko.api.service.weather.configurations.OpenWeatherMapConfiguration;
import com.gregbucko.api.service.weather.engines.weather.WeatherEngine;
import com.gregbucko.api.service.weather.engines.weather.WeatherUnits;
import com.gregbucko.api.service.weather.resources.entities.v1.current.CurrentEntity;
import org.openweathermap.api.DataWeatherClient;
import org.openweathermap.api.UrlConnectionDataWeatherClient;
import org.openweathermap.api.model.currentweather.CurrentWeather;
import org.openweathermap.api.query.*;
import org.openweathermap.api.query.currentweather.CurrentWeatherOneLocationQuery;

public class OpenWeatherMapEngine extends WeatherEngine {
    private final String apiKey;

    public OpenWeatherMapEngine(final OpenWeatherMapConfiguration configuration) {
        this.apiKey = configuration.getApiKey();
    }

    public CurrentEntity getCurrentWeather(
        final String country,
        final String city,
        final WeatherUnits units
    ) {
        DataWeatherClient client = new UrlConnectionDataWeatherClient(apiKey);

        CurrentWeatherOneLocationQuery currentWeatherOneLocationQuery = QueryBuilderPicker.pick()
            .currentWeather()
            .oneLocation()
            .byCityName(city)
            .countryCode(country)
            .type(Type.ACCURATE)
            .language(Language.ENGLISH)
            .responseFormat(ResponseFormat.JSON)
            .unitFormat(WeatherUnits.IMPERIAL.equals(units) ? UnitFormat.IMPERIAL : UnitFormat.METRIC)
            .build();

        CurrentWeather currentWeather = client.getCurrentWeather(currentWeatherOneLocationQuery);

        return new CurrentEntity(
            currentWeather.getMainParameters().getTemperature(),
            currentWeather.getWind().getSpeed(),
            currentWeather.getSystemParameters().getSunrise(),
            currentWeather.getSystemParameters().getSunset()
        );
    }

    public OpenWeatherMapHealthCheck getHealthCheck() {
        return new OpenWeatherMapHealthCheck(apiKey);
    }
}
