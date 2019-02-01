package com.gregbucko.api.service.weather.engines;

import com.codahale.metrics.health.HealthCheck;

public interface Engine {
    HealthCheck getHealthCheck();
}
