package com.br.opet.rest;

import javax.ejb.Local;

import com.br.opet.domain.dto.GoogleTokenDTO;
import com.br.opet.domain.dto.GoogleUserInfoDTO;

@Local
public interface GoogleOauthRest {
	
	GoogleTokenDTO getGoogleTokenByAuthCode(String code) throws Exception;

	GoogleUserInfoDTO getGoogleUserInfo(GoogleTokenDTO googleTokenDTO) throws Exception;
	
}
