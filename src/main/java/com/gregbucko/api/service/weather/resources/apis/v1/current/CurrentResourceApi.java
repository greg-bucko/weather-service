package com.gregbucko.api.service.weather.resources.apis.v1.current;

import com.gregbucko.api.service.weather.engines.weather.WeatherEngine;
import com.gregbucko.api.service.weather.engines.weather.WeatherUnits;
import com.gregbucko.api.service.weather.resources.entities.v1.current.CurrentEntity;

public class CurrentResourceApi {
    private final WeatherEngine weatherEngine;

    public CurrentResourceApi(final WeatherEngine weatherEngine) {
        this.weatherEngine = weatherEngine;
    }

    public CurrentEntity get(
        final String country,
        final String city,
        final WeatherUnits units
    ) throws Exception {
        return weatherEngine.getCurrentWeather(country, city, units);
    }
}
