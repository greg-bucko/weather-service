package com.gregbucko.api.service.weather.resources.entities.v1.current;

import com.gregbucko.api.service.weather.utils.Utils;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.Date;

public class CurrentEntity {
    private final Double temperature;
    private final Double windSpeed;
    private final Date sunrise;
    private final Date sunset;

    public CurrentEntity(
        final Double temperature,
        final Double windSpeed,
        final Date sunrise,
        final Date sunset
    ) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public String getSunrise() {
        return Utils.getDateUTC(sunrise);
    }

    public String getSunset() {
        return Utils.getDateUTC(sunset);
    }

}
