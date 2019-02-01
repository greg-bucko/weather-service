package com.gregbucko.api.service.weather.exceptions.errors;

public class ProcessingError {
    /**
     * A unique identifier for this particular occurrence of the problem
     */
    private final String id;

    /**
     * An object that contains link information that corresponds to the error
     */
    private final ErrorLink link;

    /**
     * The HTTP status code applicable to this problem
     */
    private final String status;

    /**
     * A short, human-readable summary of the problem that SHOULD NOT change from occurrence to occurrence of the problem, except for purposes of localization.
     */
    private final String title;

    /**
     * A human-readable explanation specific to this occurrence of the problem. This field’s value can be localized.
     */
    private final String detail;

    public ProcessingError(
        final String id,
        final ErrorLink link,
        final String status,
        final String title,
        final String detail
    ) {
        this.id = id;
        this.link = link;
        this.status = status;
        this.title = title;
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public ErrorLink getLink() {
        return link;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }
}
