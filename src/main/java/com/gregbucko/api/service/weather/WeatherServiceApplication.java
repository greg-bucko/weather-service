package com.gregbucko.api.service.weather;

import com.gregbucko.api.service.weather.exceptions.*;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.commons.configuration2.EnvironmentConfiguration;

public class WeatherServiceApplication extends Application<WeatherServiceDropwizardConfiguration> {

    public static void main(final String[] args) throws Exception {
        new WeatherServiceApplication().run(args);
    }

    @Override
    public final void initialize(final Bootstrap<WeatherServiceDropwizardConfiguration> bootstrap) {
        // Substitute yaml config parameters with environment variables
        bootstrap.setConfigurationSourceProvider(
            new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor(false)
            )
        );

        // Bundle for Swagger UI static files
        bootstrap.addBundle(new AssetsBundle("/META-INF/resources/webjars/swagger-ui-dist/3.20.5", "/docs", "index.html", "swagger"));
    }

    @Override
    public final void run(final WeatherServiceDropwizardConfiguration config, final Environment environment) {
        // Exception Mappers
        environment.jersey().register(new GenericExceptionMapper());
        environment.jersey().register(new ServiceApiExceptionMapper());
        environment.jersey().register(new ClientErrorExceptionMapper());
        environment.jersey().register(new ConstraintViolationExceptionMapper());
        environment.jersey().register(new ServerErrorExceptionMapper());

        // Engines
        final Engines engines = new Engines(new EnvironmentConfiguration(), environment);
        environment.lifecycle().manage(engines);
    }
}
