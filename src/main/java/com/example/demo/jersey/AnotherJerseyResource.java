package com.example.demo.jersey;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello/again")
public class AnotherJerseyResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloJersey() {
        return "Hello again from Jersey!";
    }
}
