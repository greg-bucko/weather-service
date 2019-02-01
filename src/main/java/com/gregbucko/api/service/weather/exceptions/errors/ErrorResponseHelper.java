package com.gregbucko.api.service.weather.exceptions.errors;

import io.swagger.v3.core.util.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.UUID;

/**
 * This class contains error response utilities which can be used to create error response and optionally log the error.
 */
public final class ErrorResponseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorResponseHelper.class);

    private ErrorResponseHelper() {
        throw new IllegalStateException("Utility class should not be instantiated.");
    }

    public static String logAndGetInstanceId(final ErrorResponseLogLevels logLevel, final String message) {
        final String logMessage = "Responding with error. Instance ID: {}. Message: {}";

        return logAndGetInstanceId(logLevel, logMessage, message);
    }

    public static String logAndGetInstanceId(final ErrorResponseLogLevels logLevel, final Throwable exception) {
        final String logMessage = "Responding with error. Instance ID: {}, exception:";

        return logAndGetInstanceId(logLevel, logMessage, exception);
    }

    private static String logAndGetInstanceId(final ErrorResponseLogLevels logLevel, final String logMessage, final Object exception) {
        final String instanceId = UUID.randomUUID().toString();

        switch (logLevel) {
            case ERROR:
                LOGGER.error(logMessage, instanceId, exception);
            case DEBUG:
                LOGGER.debug(logMessage, instanceId, exception);
        }

        return instanceId;
    }

    public static Response generateResponseFromError(final int status, final ProcessingError error) {
        final ArrayList<ProcessingError> errors = new ArrayList<>();
        errors.add(error);

        return generateResponseFromErrors(status, new ProcessingErrors(errors));
    }

    public static Response generateResponseFromErrors(final int status, final ProcessingErrors errors) {
        return Response
            .status(status)
            .entity(Json.pretty(errors))
            .type(MediaType.APPLICATION_JSON)
            .build();
    }
}
