package com.gregbucko.api.service.weather.resources.implementations.health;

import com.gregbucko.api.service.weather.engines.weather.WeatherEngine;
import com.gregbucko.api.service.weather.exceptions.ServiceApiException;
import com.gregbucko.api.service.weather.resources.apis.health.ReadinessResourceApi;
import com.gregbucko.api.service.weather.resources.definitions.health.ReadinessResourceDef;

import java.util.ArrayList;

public class ReadinessResourceImpl implements ReadinessResourceDef {

    private final ReadinessResourceApi api;

    public ReadinessResourceImpl(final WeatherEngine weatherEngine) {
        this.api = new ReadinessResourceApi(weatherEngine);
    }

    @Override
    public void getReadiness() {
        if (!api.get()) {
            throw new ServiceApiException(503, "Service is not ready", new ArrayList());
        }
    }
}
