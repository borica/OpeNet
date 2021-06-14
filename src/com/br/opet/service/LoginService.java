package com.br.opet.service;

import javax.ejb.Local;

import com.br.opet.domain.entity.Usuario;

@Local
public interface LoginService {
	
	Boolean verifyUserCredentials(Usuario subjectUser) throws Exception;
	Boolean generateDomainSalt() throws Exception;
}
