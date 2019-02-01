package com.gregbucko.api.service.weather.exceptions.errors;

public final class GenericResponseMessages {
    private GenericResponseMessages() {
        throw new IllegalStateException("Utility class should not be instantiated.");
    }

    public static final String GENERIC_SERVER_ERROR_MESSAGE = "Something went wrong. Please try again later.";
    public static final String GENERIC_CLIENT_ERROR_MESSAGE = "It appears that something is wrong with the data you sent! Please verify your input and try again.";
}