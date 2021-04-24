package com.br.opet.service;

import javax.ejb.Local;

import com.br.opet.domain.entity.Usuario;

@Local
public interface LoginService {
	
	Boolean validCredentials(Usuario subjectUser) throws Exception;
	
}
