package com.br.opet.business;

import javax.ejb.Local;

@Local
public interface GoogleOauthBusiness {
	
	String getGoogleOauthLink() throws Exception;
	
}
