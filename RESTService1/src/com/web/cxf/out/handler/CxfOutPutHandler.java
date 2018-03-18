package com.web.cxf.out.handler;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.log4j.Logger;

import com.web.cxf.in.handler.CxfInPutHandler;

public class CxfOutPutHandler extends AbstractPhaseInterceptor<Message> {
	private static final Logger LOGGER = Logger.getLogger(CxfOutPutHandler.class);
	 public CxfOutPutHandler() {
	        super(Phase.PRE_STREAM);
	    }

	    @Override
	    public void handleMessage(final Message message) throws Fault {

	        LOGGER.info("In out handleMessage() ");
	        if (message.getExchange().getInMessage() != null) {
	            if (message.getExchange().getInMessage().get(AbstractHTTPDestination.HTTP_REQUEST) != null) {
	                HttpServletRequest req = (HttpServletRequest) message.getExchange().getInMessage().get(AbstractHTTPDestination.HTTP_REQUEST);
	                //origin = req.getHeader("Origin");
	            }
	        } else {
	            LOGGER.info("Fail to get the http request ");
	        }

     }
	    
}

