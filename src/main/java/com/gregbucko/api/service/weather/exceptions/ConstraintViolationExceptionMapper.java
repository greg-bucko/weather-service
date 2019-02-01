package com.gregbucko.api.service.weather.exceptions;

import com.gregbucko.api.service.weather.exceptions.errors.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.gregbucko.api.service.weather.exceptions.errors.GenericResponseMessages.GENERIC_CLIENT_ERROR_MESSAGE;

public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException ex) {
        final ArrayList<ProcessingError> errors = new ArrayList<>();

        ex.getConstraintViolations().forEach(violation -> {
            final ArrayList<String> propertyPath = getViolationPropertyPath(violation);
            final String propertyName = propertyPath.get(propertyPath.size() - 1);

            errors.add(new ProcessingError(
                ErrorResponseHelper.logAndGetInstanceId(ErrorResponseLogLevels.DEBUG, "Failed to validation update JSON"),
                new ErrorLink("https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html"),
                "400",
                GENERIC_CLIENT_ERROR_MESSAGE,
                "Parameter '" + propertyName + "' " + violation.getMessage() + "."
            ));
        });

        return ErrorResponseHelper.generateResponseFromErrors(400, new ProcessingErrors(errors));
    }

    private ArrayList<String> getViolationPropertyPath(final ConstraintViolation<?> violation) {
        final HashSet<String> topLevelFields = new HashSet<>();
        Arrays.stream(violation.getLeafBean().getClass().getDeclaredFields()).forEach(field -> topLevelFields.add(field.getName()));

        final ArrayList<String> propertyPath = new ArrayList<>();
        final AtomicBoolean recordPath = new AtomicBoolean(false);

        violation.getPropertyPath().spliterator().forEachRemaining(node -> {
            if (topLevelFields.contains(node.getName())) {
                recordPath.set(true);
            }
            if (recordPath.get()) {
                propertyPath.add(node.getName());
            }
        });
        return propertyPath;
    }
}
