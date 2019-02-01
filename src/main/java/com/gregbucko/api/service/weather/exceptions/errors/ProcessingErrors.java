package com.gregbucko.api.service.weather.exceptions.errors;

import java.util.ArrayList;

public class ProcessingErrors {
    private final ArrayList<ProcessingError> errors;

    public ProcessingErrors(final ArrayList<ProcessingError> errors) {
        this.errors = errors;
    }

    public ArrayList<ProcessingError> getErrors() {
        return this.errors;
    }
}
