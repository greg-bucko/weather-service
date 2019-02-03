package com.gregbucko.api.service.weather.resources.definitions.v1.current;

import com.gregbucko.api.service.weather.exceptions.errors.ProcessingErrors;
import com.gregbucko.api.service.weather.resources.definitions.v1.RootResourceDefV1;
import com.gregbucko.api.service.weather.resources.entities.v1.current.CurrentEntity;
import com.gregbucko.api.service.weather.resources.requests.v1.current.CurrentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(RootResourceDefV1.ROOT_PATH + "/current")
public interface CurrentResourceDef extends RootResourceDefV1 {

    @GET
    @Path("")
    @Operation(
        tags = {"Current"},
        summary = "Get current weather parameters for a specified city within specified country.",
        description = "Get current weather parameters for a specific location. The temperature is specified in either " +
                "Celsius of Fahrenheit. The wind speed is specified in either kilometers per hour or miles per hour." +
                "The dates are always specified in UTC timezone.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "OK",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = CurrentEntity.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad Request",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = ProcessingErrors.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Not Found",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = ProcessingErrors.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = ProcessingErrors.class)
                )
            )
        }
    )
    @Produces({MediaType.APPLICATION_JSON, "application/yaml"})
    Response get(@BeanParam @Valid CurrentRequest request);
}
