package com.br.opet.business.impl;

import javax.ejb.Stateless;

import com.br.opet.business.GoogleOauthBusiness;
import com.br.opet.util.RestUtil;

@Stateless
public class GoogleOauthBusinessImpl implements GoogleOauthBusiness {
	
	private static final String G_API_CLIENT_ID = "863559431244-idkms7ag5ulrrsdp536c6tdh1e5apvql.apps.googleusercontent.com";
	private static final String G_API_SECRET = "r7wyEgddK_neVIyfZZE63I0D";
	
	@Override
	public String getGoogleOauthLink() throws Exception {
		
		final String STATE = "LOGIN";
		final String REDIRECT_URI = "http://localhost:8080/OpeNet";
		
		StringBuilder gLink = new StringBuilder();
		gLink.append(RestUtil.HTTPS);
		gLink.append("accounts.google.com/o/oauth2/v2/auth");
		gLink.append(RestUtil.PARAM);
		gLink.append("scope");
		gLink.append(RestUtil.EQUALS);
		gLink.append("https%3A//www.googleapis.com/auth/drive.metadata.readonly");
		gLink.append(RestUtil.SEPARATOR);
		gLink.append("access_type");
		gLink.append(RestUtil.EQUALS);
		gLink.append("offline");
		gLink.append(RestUtil.SEPARATOR);
		gLink.append("include_granted_scopes");
		gLink.append(RestUtil.EQUALS);
		gLink.append("true");
		gLink.append(RestUtil.SEPARATOR);
		gLink.append("response_type");
		gLink.append(RestUtil.EQUALS);
		gLink.append("code");
		gLink.append(RestUtil.SEPARATOR);
		gLink.append("state");
		gLink.append(RestUtil.EQUALS);
		gLink.append(STATE);
		gLink.append(RestUtil.SEPARATOR);
		gLink.append("redirect_uri");
		gLink.append(RestUtil.EQUALS);
		gLink.append(REDIRECT_URI);
		gLink.append(RestUtil.SEPARATOR);
		gLink.append("client_id");
		gLink.append(RestUtil.EQUALS);
		gLink.append(G_API_CLIENT_ID);
		
		return gLink.toString();
	}

}
