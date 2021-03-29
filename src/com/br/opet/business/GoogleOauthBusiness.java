package com.br.opet.business;

import javax.ejb.Local;

import com.br.opet.domain.dto.GoogleTokenDTO;
import com.br.opet.domain.entity.User;

@Local
public interface GoogleOauthBusiness {
	
	String getGoogleOauthLink() throws Exception;
	
	GoogleTokenDTO getGoogleTokenByAuthCode(String code) throws Exception;

	User getGoogleUserAsUser(GoogleTokenDTO googleTokenDTO) throws Exception;
	
}
