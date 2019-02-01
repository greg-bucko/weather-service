package com.gregbucko.api.service.weather.exceptions;

import com.gregbucko.api.service.weather.exceptions.errors.ProcessingError;

import java.util.ArrayList;

public class ServiceApiException extends RuntimeException {
    private final int status;
    private final String message;
    private final ArrayList<ProcessingError> errors;

    public ServiceApiException(
        final int status,
        final ProcessingError error
    ) {
        super(error.getTitle());
        this.status = status;
        this.message = error.getTitle();
        this.errors = new ArrayList<>();
        this.errors.add(error);
    }

    public ServiceApiException(
        final int status,
        final String message,
        final ArrayList<ProcessingError> errors
    ) {
        super(message);
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ArrayList<ProcessingError> getErrors() {
        return errors;
    }
}
