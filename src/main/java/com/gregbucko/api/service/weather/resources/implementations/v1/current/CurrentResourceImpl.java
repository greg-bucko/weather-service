package com.gregbucko.api.service.weather.resources.implementations.v1.current;

import com.gregbucko.api.service.weather.engines.weather.WeatherEngine;
import com.gregbucko.api.service.weather.resources.apis.v1.current.CurrentResourceApi;
import com.gregbucko.api.service.weather.resources.definitions.v1.current.CurrentResourceDef;
import com.gregbucko.api.service.weather.resources.entities.v1.current.CurrentEntity;
import com.gregbucko.api.service.weather.resources.requests.v1.current.CurrentRequest;
import com.gregbucko.api.service.weather.utils.ResponseFormat;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import org.openweathermap.api.WeatherClientRequestException;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;

public class CurrentResourceImpl implements CurrentResourceDef {
    private final CurrentResourceApi api;

    public CurrentResourceImpl(final WeatherEngine weatherEngine) {
        this.api = new CurrentResourceApi(weatherEngine);
    }

    /**
     * This function will return weather parameters for a given country and city. It will also make sure that correct
     * the response is in a correct format which is JSON by default. OpenWeatherMap API returns FileNotFoundException
     * if it could not find a country and city combination, I am catching it and throwing NotFoundException as I am not
     * returning a file but a resource. If OpenWeatherMap API throws any other exception, I am catching it and throwing
     * InternalServerErrorException.
     *
     * @param request CurrentRequest object
     * @return Response
     */
    @Override
    public Response get(final CurrentRequest request) {
        try {
            CurrentEntity currentEntity = api.get(request.getCountry(), request.getCity(), request.getUnits());

            if (ResponseFormat.YAML.equals(request.getFormat())) {
                return Response.status(Response.Status.OK).entity(Yaml.pretty(currentEntity)).type("application/yaml").build();
            } else {
                return Response.status(Response.Status.OK).entity(Json.pretty(currentEntity)).type(MediaType.APPLICATION_JSON_TYPE).build();
            }
        } catch (WeatherClientRequestException e) {
            if (e.getCause() instanceof FileNotFoundException) {
                throw new NotFoundException();
            } else {
                throw new InternalServerErrorException(e);
            }
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
    }
}
