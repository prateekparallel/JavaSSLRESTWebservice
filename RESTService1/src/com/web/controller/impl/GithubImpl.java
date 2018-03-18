package com.web.controller.impl;


import com.web.webservice.manager.WebServiceManager;
import com.web.controller.Github;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;


public class GithubImpl implements Github{
	
	@Override
	public Response getGithubList(UriInfo info) {
		return WebServiceManager.sendGETmsg(info);
	}

}
