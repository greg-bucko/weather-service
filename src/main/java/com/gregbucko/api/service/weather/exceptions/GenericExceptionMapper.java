package com.gregbucko.api.service.weather.exceptions;

import com.gregbucko.api.service.weather.exceptions.errors.ErrorLink;
import com.gregbucko.api.service.weather.exceptions.errors.ErrorResponseHelper;
import com.gregbucko.api.service.weather.exceptions.errors.ProcessingError;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static com.gregbucko.api.service.weather.exceptions.errors.ErrorResponseLogLevels.ERROR;
import static com.gregbucko.api.service.weather.exceptions.errors.GenericResponseMessages.GENERIC_SERVER_ERROR_MESSAGE;

public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(final Throwable ex) {
        return ErrorResponseHelper.generateResponseFromError(
            Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
            new ProcessingError(
                ErrorResponseHelper.logAndGetInstanceId(ERROR, ex),
                new ErrorLink("https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html"),
                Integer.toString(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()),
                GENERIC_SERVER_ERROR_MESSAGE,
                null
            )
        );
    }
}
