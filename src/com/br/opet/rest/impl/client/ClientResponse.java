package com.br.opet.rest.impl.client;

public class ClientResponse {
	
	   private int responseStatus;
	    private String responseBody;

	    public int getResponseStatus() {
	        return responseStatus;
	    }

	    public void setResponseStatus(int responseStatus) {
	        this.responseStatus = responseStatus;
	    }

	    public String getResponseBody() {
	        return responseBody;
	    }

	    public void setResponseBody(String responseBody) {
	        this.responseBody = responseBody;
	    }
	
}
