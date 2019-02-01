package com.gregbucko.api.service.weather.exceptions.errors;

public class ErrorLink {
    private final String link;

    public ErrorLink(final String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
