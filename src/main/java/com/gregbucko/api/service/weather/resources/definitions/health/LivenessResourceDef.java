package com.gregbucko.api.service.weather.resources.definitions.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/live")
public interface LivenessResourceDef {

    @GET
    @Operation(
        tags = {"Health"},
        summary = "Get liveness information",
        description = "Get liveness information",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "No Content"
            ),
            @ApiResponse(
                responseCode = "503",
                description = "Service Unavailable"
            )
        }
    )
    void getLiveness();
}
