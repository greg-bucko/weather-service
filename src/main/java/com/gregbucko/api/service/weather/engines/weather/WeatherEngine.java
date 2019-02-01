package com.gregbucko.api.service.weather.engines.weather;

import com.gregbucko.api.service.weather.engines.Engine;
import com.gregbucko.api.service.weather.resources.entities.v1.current.CurrentEntity;

public abstract class WeatherEngine implements Engine {
    abstract public CurrentEntity getCurrentWeather(final String country, final String city, final WeatherUnits units) throws Exception;
}
