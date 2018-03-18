package com.web.webservice.manager;
import com.web.common.config.ApplicationConfig;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.web.common.ssl.config.SSLContextConfig;

import org.apache.cxf.helpers.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class WebServiceManager {
	
	static int _responseCode = -1;
	static private ApplicationConfig config_ = ApplicationConfig.getInstance();
	private static final Logger LOGGER = Logger.getLogger(WebServiceManager.class);
	
	static HttpsURLConnection connection_ = null;
	static SSLContext sslContext = null;
    static  {
	
    		//SSLContextConfig sslconfig = new SSLContextConfig();
			//sslContext = sslconfig.setupSslContext();	
    	LOGGER.info("Webservice is loading...");
	}

	//------------------------------------------------------------------

	static public boolean setSSLConnection(UriInfo info) {
	System.out.println("In setSSLConnection");
	URL url = null;
	//HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
	//				.getSocketFactory());
	
				try {
					url = new URL(null, WebServiceManager.generateTargetURL(info),
							new sun.net.www.protocol.https.Handler());
				} catch (Exception e1) {
					LOGGER.error("MalformedURLException occurred " + e1.getMessage());
				}
				
			try {
				connection_ = (HttpsURLConnection) url.openConnection();
				//connection_.setSSLSocketFactory(sslContext.getSocketFactory());
	            connection_.setDoOutput( true );
	            connection_.setRequestProperty( "Content-Type", "text/xml"  );
	            connection_.connect();
				return true;
			} catch (Exception e) {
				LOGGER.error("Exception occurred while establishing connection to SSL server. Error :"
						+ e.getMessage());
				connection_.disconnect();
				connection_ = null;
				return false;
			}
	}
	//----------------------------------------------------------------------------------
	
	static public String generateTargetURL(UriInfo info) {
		LOGGER.info("In generateTargetURL()");
		String url = null;
		String regex = config_.getREGEX();
		String sevcURL[] = info.getRequestUri().toString().split(regex);
		String serviceURL = config_.getHTTPS_SERV_URL();
		url = serviceURL + sevcURL[1].replace("/"+"Github.json","").replace("/v/github/", "");
		LOGGER.info("URL is : " + url);
		return url;
		
	}
	
	static public Response sendGETmsg(UriInfo info) {
		LOGGER.info("In sendGETmsg");
		String response = null;
		int retcode = 500;
		if(!WebServiceManager.setSSLConnection(info)) {
	    	   response = "Failed to setSSLConnection ";
	    	   return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).type(MediaType.APPLICATION_JSON).build();
	       }
		try {
			
				//connection_.setRequestMethod("GET");
				retcode = connection_.getResponseCode();
				System.out.println("In sendGETmsg2 :" + retcode);
				if(retcode != 401 && retcode != 500)
				    response = IOUtils.toString(connection_.getInputStream());
				else
					response="Failed to connect to remote server :";
		}catch(Exception e) {
			e.printStackTrace();
		}
		connection_.disconnect();
		return Response.status(retcode).entity(response).type(MediaType.APPLICATION_JSON).build();
	}

	//Now send the message to the server:-
	
	public static Response sendPOSTmsg(UriInfo info, Object sendObject) {
		
	String response = null;
	
	       if(!WebServiceManager.setSSLConnection(info)) {
	    	   response = "Failed to setSSLConnection ";
	    	   return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).type(MediaType.APPLICATION_JSON).build();
	       }
	       
	       ObjectMapper mapper = new ObjectMapper();	       

	try{
				connection_.setRequestMethod("POST");
				Writer strWriter = new StringWriter();
				mapper.writeValue(strWriter, sendObject);
				String reqJSONData = strWriter.toString();
			        //Sending the request to Remote server
			      OutputStreamWriter writer = new OutputStreamWriter(connection_.getOutputStream());
		          writer.write(reqJSONData);
		          writer.flush();
		          writer.close();
		          
		          _responseCode = connection_.getResponseCode();
		          
		          LOGGER.info("Response Code :" + _responseCode);
		          
			     // reading the response
		          InputStreamReader reader = new InputStreamReader(connection_.getInputStream());
		          StringBuilder buf = new StringBuilder();
		          char[] cbuf = new char[ 2048 ];
		          int num;
		          while ( -1 != (num = reader.read( cbuf )))
		          {
		              buf.append( cbuf, 0, num );
		          }
		          response = buf.toString();
		          
			 }catch(Exception e){
				 response = "<EXCEPTION>Exception occurred while sending message</EXCEPTION>";
				 e.printStackTrace();
			 }
		connection_.disconnect();
		return Response.status(Status.OK).entity(response).type(MediaType.APPLICATION_JSON).build();
	}


}
