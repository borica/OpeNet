package com.br.opet.business;

import javax.ejb.Local;

import com.br.opet.domain.entity.User;

@Local
public interface LoginBusiness {
	
	Boolean validCredentials(User subjectUser) throws Exception;
	
}
