package com.gregbucko.api.service.weather;

import com.gregbucko.api.service.weather.configurations.CorsConfiguration;
import com.gregbucko.api.service.weather.configurations.DosConfiguration;
import com.gregbucko.api.service.weather.configurations.WeatherServiceConfiguration;
import com.gregbucko.api.service.weather.engines.weather.WeatherEngine;
import com.gregbucko.api.service.weather.engines.weather.openweathermap.OpenWeatherMapEngine;
import com.gregbucko.api.service.weather.resources.definitions.v1.RootResourceDefV1;
import com.gregbucko.api.service.weather.resources.implementations.health.LivenessResourceImpl;
import com.gregbucko.api.service.weather.resources.implementations.health.ReadinessResourceImpl;
import com.gregbucko.api.service.weather.resources.implementations.v1.OpenApiImpl;
import com.gregbucko.api.service.weather.resources.implementations.v1.current.CurrentResourceImpl;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.apache.commons.configuration2.Configuration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.servlets.DoSFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Engines implements Managed {
    private static final Logger LOGGER = LoggerFactory.getLogger(Engines.class);

    private final Configuration config;
    private final Environment environment;

    Engines(final Configuration config, final Environment environment) {
        this.config = config;
        this.environment = environment;
    }

    @Override
    public final void start() throws Exception {
        final WeatherServiceConfiguration weatherServiceConfiguration = WeatherServiceConfiguration.loadFromConfiguration(config);

        // OpenWeatherMap Engine
        final WeatherEngine weatherEngine = new OpenWeatherMapEngine(weatherServiceConfiguration.getOpenWeatherMapConfiguration());

        // Health checks
        initHealthChecks(weatherEngine);

        // Cross-Origin Resource Sharing filter
        initCors(weatherServiceConfiguration.getCorsConfiguration());

        // Denial of Service filter
        initDosFilter(weatherServiceConfiguration.getDosConfiguration());

        // Resources
        initResources(weatherEngine);

        // Swagger
        initSwagger();
    }

    @Override
    public final void stop() {
        LOGGER.debug("Shutting down engines...");

        // Logic for engines shutdown for example database engine with a pool of connections etc.

        LOGGER.debug("Engines shutdown complete");
    }

    private void initHealthChecks(final WeatherEngine weatherEngine) {
        environment.healthChecks().register("OpenWeatherMapEngine", weatherEngine.getHealthCheck());
    }

    private void initCors(CorsConfiguration corsConfiguration) {
        LOGGER.debug("Initializing Cross-Origin Resource Sharing filter...");

        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("corsFilter", CrossOriginFilter.class);

        cors.setInitParameter("allowedOrigins", corsConfiguration.getAllowedOrigins());
        cors.setInitParameter("allowedHeaders", corsConfiguration.getAllowedHeaders());
        cors.setInitParameter("allowedMethods", corsConfiguration.getAllowedMethods());

        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        LOGGER.debug("Cross-Origin Resource Sharing filter initialized");
    }

    private void initDosFilter(DosConfiguration dosConfiguration) {
        LOGGER.debug("Denial of Service filter...");

        // For full set of options see docs at https://www.eclipse.org/jetty/documentation/9.4.x/dos-filter.html
        final FilterRegistration.Dynamic dosFilter = environment.servlets().addFilter("dosFilter", DoSFilter.class);

        // Number of permitted requests per connection per second
        dosFilter.setInitParameter("maxRequestsPerSec", dosConfiguration.getMaxRequestsPerSec().toString());

        // Delay imposed on requests over the limit (-1 to reject)
        dosFilter.setInitParameter("delayMs", dosConfiguration.getDelayMs().toString());

        dosFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        LOGGER.debug("Denial of Service filter initialized");
    }

    private void initResources(final WeatherEngine weatherEngine) {
        LOGGER.debug("Initializing Resources...");

        environment.jersey().register(new LivenessResourceImpl());
        environment.jersey().register(new ReadinessResourceImpl(weatherEngine));
        environment.jersey().register(new CurrentResourceImpl(weatherEngine));

        LOGGER.debug("Resources initialized");
    }

    private void initSwagger() throws Exception {
        LOGGER.debug("Initializing Swagger...");

        final OpenAPI oas = new OpenAPI();
        final Info info = new Info()
            .title("Weather Service")
            .version(RootResourceDefV1.VERSION)
            .description("This service offers an API to retrieve current weather parameters for a given location.");

        oas.info(info);

        final SwaggerConfiguration oasConfig = new SwaggerConfiguration()
            .openAPI(oas)
            .prettyPrint(true)
            .resourcePackages(Stream.of("com.gregbucko.api.service.weather.resources").collect(Collectors.toSet()));

        try {
            new JaxrsOpenApiContextBuilder()
                .openApiConfiguration(oasConfig)
                .buildContext(true);

            // Register OpenApi API here
            environment.jersey().register(new OpenApiImpl().openApiConfiguration(oasConfig));

            LOGGER.debug("Swagger initialized");
        } catch (final OpenApiConfigurationException e) {
            throw new Exception("Error initialising swagger", e);
        }
    }
}
