package com.gregbucko.api.service.weather.resources.definitions.v1;

import io.swagger.v3.oas.annotations.Operation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path(RootResourceDefV1.ROOT_PATH + "/openapi.{type:json|yaml}")
public interface OpenApiDef {

    @GET
    @Produces({"application/json", "application/yaml"})
    @Operation(
        hidden = true
    )
    Response getOpenApi(@Context HttpHeaders headers, @Context UriInfo uriInfo, @PathParam("type") String type) throws Exception;
}
