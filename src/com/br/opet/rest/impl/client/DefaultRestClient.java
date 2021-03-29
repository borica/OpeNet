package com.br.opet.rest.impl.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.br.opet.util.RestUtil;
import com.google.common.base.Strings;
import com.google.gson.Gson;

public abstract class DefaultRestClient {
	
	protected Gson gson;
	
	protected void setupClient() {
		
		if(gson == null)
			gson = new Gson();
		
	}
	
	protected ClientResponse doPost(URL endpoint, HashMap<String, String> params) throws IOException {
		
		ClientResponse response = new ClientResponse();

	    HttpURLConnection httpConn = (HttpURLConnection) endpoint.openConnection();
	    httpConn.setRequestMethod(HttpMethod.POST);
	    httpConn.setRequestProperty(RestUtil.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED);
	    httpConn.setRequestProperty( "Accept", "*/*" );
	    
	    if(params != null) {
	    	 httpConn.setDoOutput(true);
	         DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
	
	         dos.writeBytes(RestUtil.getParamsString(params));
	         dos.flush();
	         dos.close();
	    }
	
	    response.setResponseStatus(httpConn.getResponseCode());
	
	    Reader streamReader;
	    StringBuffer content = new StringBuffer();
	
	    if (httpConn.getResponseCode() > 299) {
	        streamReader = new InputStreamReader(httpConn.getErrorStream());
	    } else {
	        streamReader = new InputStreamReader(httpConn.getInputStream());
	    }
	
	    BufferedReader in = new BufferedReader(streamReader);
	    String inputLine;
	
	    while ((inputLine = in.readLine()) != null) {
	        content.append(inputLine);
	    }
	
	    in.close();
	    response.setResponseBody(content.toString());
	    httpConn.disconnect();
	
	    return response;
	}
	
	protected ClientResponse doGet(URL endpoint, HashMap<String, String> params) throws IOException {
		
		ClientResponse response = new ClientResponse();

	    HttpURLConnection httpConn = (HttpURLConnection) endpoint.openConnection();
	    httpConn.setRequestMethod(HttpMethod.GET);
	    httpConn.setRequestProperty(RestUtil.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED);
	    httpConn.setRequestProperty( "Accept", "*/*" );
	    
	    if(params != null) {
	    	 httpConn.setDoOutput(true);
	         DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
	
	         dos.writeBytes(RestUtil.getParamsString(params));
	         dos.flush();
	         dos.close();
	    }
	
	    response.setResponseStatus(httpConn.getResponseCode());
	
	    Reader streamReader;
	    StringBuffer content = new StringBuffer();
	
	    if (httpConn.getResponseCode() > 299) {
	        streamReader = new InputStreamReader(httpConn.getErrorStream());
	    } else {
	        streamReader = new InputStreamReader(httpConn.getInputStream());
	    }
	
	    BufferedReader in = new BufferedReader(streamReader);
	    String inputLine;
	
	    while ((inputLine = in.readLine()) != null) {
	        content.append(inputLine);
	    }
	
	    in.close();
	    response.setResponseBody(content.toString());
	    httpConn.disconnect();
	
	    return response;
	}
	
	
	protected void validateResponse(ClientResponse response, URL endpoint) throws ExecutionException {
		if (response.getResponseStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
			throw new ExecutionException("BAD REQUEST. " + response.getResponseBody() + " URI:" + endpoint.toString(), new Throwable());
		}
		if (response.getResponseStatus() == Response.Status.UNAUTHORIZED.getStatusCode() || response.getResponseStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
			throw new ExecutionException("UNAUTHORIZED. " + response.getResponseBody() + " URI:" + endpoint.toString(), new Throwable());
		}

		if (response.getResponseStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
			throw new ExecutionException("NOT FOUND. " + response.getResponseBody() + " URI:" + endpoint.toString(), new Throwable());
		}

		if (response.getResponseStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new ExecutionException("INTERNAL SERVER ERROR. " + response.getResponseBody() + " URI:" + endpoint.toString(), new Throwable());
		}
		if (response.getResponseStatus() == Response.Status.LENGTH_REQUIRED.getStatusCode()) {
			throw new ExecutionException("BODY IS INVALID. " + response.getResponseBody() + " URI:" + endpoint.toString(), new Throwable());
		}
	}
	
}
