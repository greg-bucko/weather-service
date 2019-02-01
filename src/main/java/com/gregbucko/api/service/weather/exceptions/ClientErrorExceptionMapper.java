package com.gregbucko.api.service.weather.exceptions;

import com.gregbucko.api.service.weather.exceptions.errors.ErrorLink;
import com.gregbucko.api.service.weather.exceptions.errors.ErrorResponseHelper;
import com.gregbucko.api.service.weather.exceptions.errors.ErrorResponseLogLevels;
import com.gregbucko.api.service.weather.exceptions.errors.ProcessingError;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ClientErrorExceptionMapper implements ExceptionMapper<ClientErrorException> {

    @Override
    public Response toResponse(final ClientErrorException ex) {
        return ErrorResponseHelper.generateResponseFromError(
            ex.getResponse().getStatus(),
            new ProcessingError(
                ErrorResponseHelper.logAndGetInstanceId(ErrorResponseLogLevels.DEBUG, ex),
                new ErrorLink("https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html"),
                Integer.toString(ex.getResponse().getStatus()),
                ex.getLocalizedMessage(),
                null
            )
        );
    }
}
