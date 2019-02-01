package com.gregbucko.api.service.weather.resources.implementations.health;

import com.gregbucko.api.service.weather.exceptions.ServiceApiException;
import com.gregbucko.api.service.weather.resources.apis.health.LivenessResourceApi;
import com.gregbucko.api.service.weather.resources.definitions.health.LivenessResourceDef;

import java.util.ArrayList;

public class LivenessResourceImpl implements LivenessResourceDef {

    private final LivenessResourceApi api;

    public LivenessResourceImpl() {
        this.api = new LivenessResourceApi();
    }

    @Override
    public void getLiveness() {
        if (!api.get()) {
            throw new ServiceApiException(503, "Service is not ready", new ArrayList());
        }
    }
}
