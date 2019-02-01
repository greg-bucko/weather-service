package com.gregbucko.api.service.weather.resources.requests.v1.current;

import com.gregbucko.api.service.weather.engines.weather.WeatherUnits;
import com.gregbucko.api.service.weather.utils.ResponseFormat;
import io.swagger.v3.oas.annotations.Parameter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.QueryParam;

public class CurrentRequest {
    @Parameter(
        name = "country",
        description = "An ISO-3166 country code for which current weather parameters are to be retrieved.",
        required = true
    )
    @QueryParam("country")
    @Size(min = 2, max = 2)
    @NotNull
    String country;

    @Parameter(
        name = "city",
        description = "A city name for which current weather parameters are to be retrieved.",
        required = true
    )
    @QueryParam("city")
    @Size(min = 1, max = 200)
    @NotNull
    String city;

    @Parameter(
        name = "units",
        description = "The units of weather parameters to be retrieved. Default is METRIC."
    )
    @QueryParam("units")
    WeatherUnits units;

    @Parameter(
        name = "format",
        description = "The format of the API response. Default is JSON."
    )
    @QueryParam("format")
    ResponseFormat format;

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public WeatherUnits getUnits() {
        return units;
    }

    public ResponseFormat getFormat() {
        return format;
    }
}
