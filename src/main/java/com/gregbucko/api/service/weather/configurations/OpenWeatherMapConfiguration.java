package com.gregbucko.api.service.weather.configurations;


import org.apache.commons.configuration2.Configuration;

/**
 * This class provides configuration for the OpenWeatherMap engine.
 */
public class OpenWeatherMapConfiguration {
    public static final String configurationOpenWeatherMapApiKey = "CONFIGURATION-WEATHER-SERVICE-OPEN_WEATHER_MAP-API-KEY";

    private final String apiKey;

    public OpenWeatherMapConfiguration(final String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @param config - Configuration object which is created using environment variables.
     * @return OpenWeatherMapConfiguration object
     */
    public static OpenWeatherMapConfiguration loadFromConfiguration(final Configuration config) {
        final String apiKey = config.getString(configurationOpenWeatherMapApiKey, "");

        return new OpenWeatherMapConfiguration(apiKey);
    }

    public String getApiKey() {
        return apiKey;
    }
}
