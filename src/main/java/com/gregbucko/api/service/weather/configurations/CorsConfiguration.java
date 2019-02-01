package com.gregbucko.api.service.weather.configurations;


import org.apache.commons.configuration2.Configuration;

/**
 * This class provides configuration for the Cross-Origin Resource filter.
 */
public class CorsConfiguration {
    public static final String configurationCorsAllowedOrigins = "CONFIGURATION-WEATHER-SERVICE-CORS-ALLOWED-ORIGINS";
    public static final String configurationCorsAllowedHeaders = "CONFIGURATION-WEATHER-SERVICE-CORS-ALLOWED-HEADERS";
    public static final String configurationCorsAllowedMethods = "CONFIGURATION-WEATHER-SERVICE-CORS-ALLOWED-METHODS";

    private final String allowedOrigins;
    private final String allowedHeaders;
    private final String allowedMethods;

    public CorsConfiguration(
        final String allowedOrigins,
        final String allowedHeaders,
        final String allowedMethods
    ) {
        this.allowedOrigins = allowedOrigins;
        this.allowedHeaders = allowedHeaders;
        this.allowedMethods = allowedMethods;
    }

    /**
     * @param config - Configuration object which is created using environment variables.
     * @return CorsConfiguration object
     */
    public static CorsConfiguration loadFromConfiguration(final Configuration config) {
        final String allowedOrigins = config.getString(configurationCorsAllowedOrigins, "*");
        final String allowedHeaders = config.getString(configurationCorsAllowedHeaders, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        final String allowedMethods = config.getString(configurationCorsAllowedMethods, "OPTIONS,GET,PUT,POST,PATCH,DELETE,HEAD");

        return new CorsConfiguration(
            allowedOrigins,
            allowedHeaders,
            allowedMethods
        );
    }

    public String getAllowedOrigins() {
        return allowedOrigins;
    }

    public String getAllowedHeaders() {
        return allowedHeaders;
    }

    public String getAllowedMethods() {
        return allowedMethods;
    }
}
