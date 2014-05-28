package com.devreed.homeaway.rest;

import com.devreed.homeaway.Util.JsonTranslator;
import com.devreed.homeaway.core.Person;
import com.devreed.homeaway.core.PersonService;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by rreed on 5/24/2014.
 */
@Component
@Path("/")
public class RestResource {
    private PersonService personService;

    public RestResource(PersonService personService) {
        this.personService = personService;

    }

    @GET
    @Produces("application/json")
    @Path("/person/")
    public Response getPerson() {
        try {
            List<Person> people = personService.getPerson(null);
            return Response.status(200).entity( JsonTranslator.toJson(people) ).build();
        } catch(SQLException e){
            return Response.status(200).entity("{ \"response\": \"Failed to create person due to SQL exception\"").build();
        } catch(Exception e){
            return Response.status(200).entity("{ \"response\": \"Failed to create person\"").build();
        }
    }

    @GET
    @Produces("application/json")
    @Path("/person/{personId}")
    public Response getPerson(@PathParam("personId") UUID personId) {
        try {
            List<Person> people = personService.getPerson(personId);
            return Response.status(200).entity( JsonTranslator.toJson(people) ).build();
        } catch(SQLException e){
            return Response.status(200).entity("{ \"response\": \"Failed to create person due to SQL exception\"").build();
        } catch(Exception e){
            return Response.status(200).entity("{ \"response\": \"Failed to retrieve person\"").build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/person")
    public Response createPerson(String personJson) {
        try {
            UUID personId = personService.createPerson(personJson);
            return Response.status(201).entity("{ \"id\": \"" + personId + "\" }").build();
        } catch(Exception e){
            // could also return a 200 with a message that would be useful to the user
            return Response.status(200).entity("{ \"response\": \"Failed to create person\"").build();
        }
    }

    @DELETE
    @Produces("application/json")
    @Path("/person/{personId}")
    public Response deletePerson(@PathParam("personId") UUID personId) {
        try {
            personService.deletePerson(personId);
            return Response.status(200).entity("{ \"Response\": \"Success\"").build();
        } catch(Exception e){
            return Response.status(200).entity("{ \"response\": \"Failed to delete person\"").build();
        }
    }
}
