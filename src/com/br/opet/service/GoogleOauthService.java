package com.br.opet.service;

import javax.ejb.Local;

import com.br.opet.domain.dto.GoogleTokenDTO;
import com.br.opet.domain.entity.Usuario;

@Local
public interface GoogleOauthService {
	
	String getGoogleOauthLink() throws Exception;
	
	GoogleTokenDTO getGoogleTokenByAuthCode(String code) throws Exception;

	Usuario getGoogleUserAsUser(GoogleTokenDTO googleTokenDTO) throws Exception;
	
}
