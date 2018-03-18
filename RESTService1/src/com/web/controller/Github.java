package com.web.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/v/github")
public interface Github {
	///RESTService1/rest/example/v/github/Github.json/repositories   
	@GET 
	@Path("/Github.json/repositories") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGithubList(@Context UriInfo info);
	
	/*
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/orders")
	public Response registerCar(@Context UriInfo info CarDetailBean carDetailBean,);	
	*/
}