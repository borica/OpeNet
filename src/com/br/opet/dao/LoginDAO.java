package com.br.opet.dao;

import javax.ejb.Local;

import com.br.opet.domain.entity.Usuario;

@Local
public interface LoginDAO {
	
	Boolean validCredentials(Usuario subjectUser) throws Exception; 
	
}
