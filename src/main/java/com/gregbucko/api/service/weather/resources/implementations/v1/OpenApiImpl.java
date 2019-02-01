package com.gregbucko.api.service.weather.resources.implementations.v1;

import com.gregbucko.api.service.weather.resources.definitions.v1.OpenApiDef;
import io.swagger.v3.jaxrs2.integration.resources.BaseOpenApiResource;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.*;

/**
 * This class provides implementation for the OpenApiDef. The only reason why it is needed is to version this API.
 */
public class OpenApiImpl extends BaseOpenApiResource implements OpenApiDef {
    @Context
    ServletConfig config;
    @Context
    Application app;

    public Response getOpenApi(HttpHeaders headers, UriInfo uriInfo, String type) throws Exception {
        return super.getOpenApi(headers, this.config, this.app, uriInfo, type);
    }
}
