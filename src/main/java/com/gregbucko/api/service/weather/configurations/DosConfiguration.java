package com.gregbucko.api.service.weather.configurations;


import org.apache.commons.configuration2.Configuration;

/**
 * This class provides configuration for the Denial of Service filter.
 */
public class DosConfiguration {
    public static final String configurationDosMaxRequestsPerSec = "CONFIGURATION-WEATHER-SERVICE-DOS-MAX-REQUESTS-PER-SEC";
    public static final String configurationDosDelayMs = "CONFIGURATION-WEATHER-SERVICE-DOS-DELAY-MS";

    private final Integer maxRequestsPerSec;
    private final Integer delayMs;

    public DosConfiguration(
        final Integer maxRequestsPerSec,
        final Integer delayMs
    ) {
        this.maxRequestsPerSec = maxRequestsPerSec;
        this.delayMs = delayMs;
    }

    /**
     * @param config - Configuration object which is created using environment variables.
     * @return DosConfiguration object
     */
    public static DosConfiguration loadFromConfiguration(final Configuration config) {
        final Integer maxRequestsPerSec = config.getInt(configurationDosMaxRequestsPerSec, 25);
        final Integer delayMs = config.getInt(configurationDosDelayMs, 100);

        return new DosConfiguration(
            maxRequestsPerSec,
            delayMs
        );
    }

    public Integer getMaxRequestsPerSec() {
        return maxRequestsPerSec;
    }

    public Integer getDelayMs() {
        return delayMs;
    }
}
