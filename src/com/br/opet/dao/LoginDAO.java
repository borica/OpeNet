package com.br.opet.dao;

import javax.ejb.Local;

import com.br.opet.domain.entity.User;

@Local
public interface LoginDAO {
	
	Boolean validCredentials(User subjectUser) throws Exception; 
	
}
