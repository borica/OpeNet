package com.br.opet.rest.impl;

import java.net.URL;
import java.util.HashMap;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import com.br.opet.domain.dto.GoogleTokenDTO;
import com.br.opet.domain.dto.GoogleUserInfoDTO;
import com.br.opet.rest.GoogleOauthRest;
import com.br.opet.rest.impl.client.ClientResponse;
import com.br.opet.rest.impl.client.DefaultRestClient;
import com.br.opet.util.RestUtil;
import com.google.common.base.Strings;

@Stateless
public class GoogleOauthRestImpl extends DefaultRestClient implements GoogleOauthRest {
	
	private static final String G_API_CLIENT_ID = "863559431244-idkms7ag5ulrrsdp536c6tdh1e5apvql.apps.googleusercontent.com";
	private static final String G_API_SECRET = "r7wyEgddK_neVIyfZZE63I0D";
	
	private URL googleTokenEndpoint; 
	
	final String REDIRECT_URI = "http://localhost:8080/OpeNet";
	
	@Override
	public GoogleTokenDTO getGoogleTokenByAuthCode(String code) throws Exception {
		
		googleTokenEndpoint = new URL("https://oauth2.googleapis.com/token");
		
		GoogleTokenDTO googleTokenDTO = null;
		
		setupClient();
		
		HashMap<String, String> params = new HashMap<>();
		params.put(RestUtil.CODE, code);
		params.put(RestUtil.CLIENT_ID, G_API_CLIENT_ID);
		params.put(RestUtil.CLIENT_SECRET, G_API_SECRET);
		params.put(RestUtil.REDIRECT_URI, REDIRECT_URI);
		params.put(RestUtil.GRANT_TYPE, RestUtil.AUTHORIZATION_CODE);
		
		ClientResponse googleOauthResponse = doPost(googleTokenEndpoint, params);
		validateResponse(googleOauthResponse, googleTokenEndpoint);
		
		if(!Strings.isNullOrEmpty(googleOauthResponse.getResponseBody()) && googleOauthResponse.getResponseStatus() == Response.Status.OK.getStatusCode())
			googleTokenDTO = gson.fromJson(googleOauthResponse.getResponseBody(), GoogleTokenDTO.class);
		
		return googleTokenDTO;
	}

	@Override
	public GoogleUserInfoDTO getGoogleUserInfo(GoogleTokenDTO googleTokenDTO) throws Exception {
		
		googleTokenEndpoint = new URL("https://www.googleapis.com/oauth2/v1/userinfo?"+RestUtil.ACCESS_TOKEN+RestUtil.EQUALS+googleTokenDTO.getAccessToken());
		
		GoogleUserInfoDTO googleUserInfoDTO = new GoogleUserInfoDTO();
		
		setupClient();
		
		ClientResponse googleUserInfoResponse = doGet(googleTokenEndpoint, null);
		validateResponse(googleUserInfoResponse, googleTokenEndpoint);
		
		if(!Strings.isNullOrEmpty(googleUserInfoResponse.getResponseBody()) && googleUserInfoResponse.getResponseStatus() == Response.Status.OK.getStatusCode())
			googleUserInfoDTO = gson.fromJson(googleUserInfoResponse.getResponseBody(), GoogleUserInfoDTO.class);
		
		return googleUserInfoDTO;
	}

}
