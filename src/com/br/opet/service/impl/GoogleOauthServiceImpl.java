package com.br.opet.service.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.br.opet.domain.dto.GoogleTokenDTO;
import com.br.opet.domain.dto.GoogleUserInfoDTO;
import com.br.opet.domain.entity.Usuario;
import com.br.opet.rest.GoogleOauthRest;
import com.br.opet.service.GoogleOauthService;
import com.br.opet.util.RestUtil;

@Stateless
public class GoogleOauthServiceImpl implements GoogleOauthService {
	
	private static final String G_API_CLIENT_ID = "863559431244-idkms7ag5ulrrsdp536c6tdh1e5apvql.apps.googleusercontent.com";
	
	@EJB
	private GoogleOauthRest googleOauthRest;
	
	@Override
	public String getGoogleOauthLink() throws Exception {
		
		final String STATE = "LOGIN";
		final String REDIRECT_URI = "http://localhost:8080/OpeNet";
		
		StringBuilder gLink = new StringBuilder();
		gLink.append(RestUtil.HTTPS);
		gLink.append("accounts.google.com/o/oauth2/v2/auth");
		gLink.append(RestUtil.PARAM);
		gLink.append(RestUtil.SCOPE);
		gLink.append(RestUtil.EQUALS);
		gLink.append("https://www.googleapis.com/auth/userinfo.profile");
		gLink.append(RestUtil.SEPARATOR);
		gLink.append(RestUtil.OFFLINE);
		gLink.append(RestUtil.EQUALS);
		gLink.append(RestUtil.OFFLINE);
		gLink.append(RestUtil.SEPARATOR);
		gLink.append(RestUtil.INCLUDE_GRANTED_SCOPES);
		gLink.append(RestUtil.EQUALS);
		gLink.append(RestUtil.TRUE);
		gLink.append(RestUtil.SEPARATOR);
		gLink.append(RestUtil.RESPONSE_TYPE);
		gLink.append(RestUtil.EQUALS);
		gLink.append(RestUtil.CODE);
		gLink.append(RestUtil.SEPARATOR);
		gLink.append(RestUtil.STATE);
		gLink.append(RestUtil.EQUALS);
		gLink.append(STATE);
		gLink.append(RestUtil.SEPARATOR);
		gLink.append(RestUtil.REDIRECT_URI);
		gLink.append(RestUtil.EQUALS);
		gLink.append(REDIRECT_URI);
		gLink.append(RestUtil.SEPARATOR);
		gLink.append(RestUtil.CLIENT_ID);
		gLink.append(RestUtil.EQUALS);
		gLink.append(G_API_CLIENT_ID);
		
		return gLink.toString();
	}

	@Override
	public GoogleTokenDTO getGoogleTokenByAuthCode(String code) throws Exception {
		return this.googleOauthRest.getGoogleTokenByAuthCode(code);
	}

	@Override
	public Usuario getGoogleUserAsUser(GoogleTokenDTO googleTokenDTO) throws Exception {
		Usuario userWithGoogleInfo = new Usuario();
		
		GoogleUserInfoDTO googleUserInfoDTO = this.googleOauthRest.getGoogleUserInfo(googleTokenDTO);
		
		if(googleUserInfoDTO != null){
			userWithGoogleInfo.setGoogleUserInfoDTO(googleUserInfoDTO);
		}
		
		return userWithGoogleInfo;
	}

}
