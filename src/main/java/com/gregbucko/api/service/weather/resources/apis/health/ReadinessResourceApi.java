package com.gregbucko.api.service.weather.resources.apis.health;

import com.gregbucko.api.service.weather.engines.weather.WeatherEngine;

public class ReadinessResourceApi {
    private final WeatherEngine weatherEngine;

    public ReadinessResourceApi(final WeatherEngine weatherEngine) {
        this.weatherEngine = weatherEngine;
    }

    /**
     * The only service that this application depends on is OpenWeatherMap therefore I have decided to use OpenWeatherMap
     * health check to determine whether the overall application is ready.
     *
     * @return Readiness status
     */
    public boolean get() {
        return weatherEngine.getHealthCheck().execute().isHealthy();
    }
}
