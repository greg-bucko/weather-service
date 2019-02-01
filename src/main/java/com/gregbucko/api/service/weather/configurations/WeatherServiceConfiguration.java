package com.gregbucko.api.service.weather.configurations;

import org.apache.commons.configuration2.Configuration;

/**
 * This class represents main configuration object.
 */
public class WeatherServiceConfiguration extends io.dropwizard.Configuration {
    private final CorsConfiguration corsConfiguration;
    private final DosConfiguration dosConfiguration;
    private final OpenWeatherMapConfiguration openWeatherMapConfiguration;

    public WeatherServiceConfiguration(
        final CorsConfiguration corsConfiguration,
        final DosConfiguration dosConfiguration,
        final OpenWeatherMapConfiguration openWeatherMapConfiguration
    ) {
        this.corsConfiguration = corsConfiguration;
        this.dosConfiguration = dosConfiguration;
        this.openWeatherMapConfiguration = openWeatherMapConfiguration;
    }

    public static WeatherServiceConfiguration loadFromConfiguration(final Configuration config) {
        final CorsConfiguration corsConfiguration = CorsConfiguration.loadFromConfiguration(config);
        final DosConfiguration dosConfiguration = DosConfiguration.loadFromConfiguration(config);
        final OpenWeatherMapConfiguration openWeatherMapConfiguration = OpenWeatherMapConfiguration.loadFromConfiguration(config);

        return new WeatherServiceConfiguration(
            corsConfiguration,
            dosConfiguration,
            openWeatherMapConfiguration
        );
    }

    public CorsConfiguration getCorsConfiguration() {
        return corsConfiguration;
    }

    public DosConfiguration getDosConfiguration() {
        return dosConfiguration;
    }

    public OpenWeatherMapConfiguration getOpenWeatherMapConfiguration() {
        return openWeatherMapConfiguration;
    }
}
