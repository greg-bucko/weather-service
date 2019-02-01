package com.gregbucko.api.service.weather.exceptions;

import com.gregbucko.api.service.weather.exceptions.errors.ErrorResponseHelper;
import com.gregbucko.api.service.weather.exceptions.errors.ProcessingErrors;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ServiceApiExceptionMapper implements ExceptionMapper<ServiceApiException> {

    @Override
    public Response toResponse(final ServiceApiException ex) {
        return ErrorResponseHelper.generateResponseFromErrors(
            ex.getStatus(),
            new ProcessingErrors(ex.getErrors())
        );
    }
}