package com.br.opet.business;

import javax.ejb.Local;

import com.br.opet.entity.User;

@Local
public interface LoginBusiness {
	
	Boolean validCredentials(User subjectUser) throws Exception;
	
}
