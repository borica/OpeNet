package com.br.opet.dao.impl;

import javax.ejb.Stateless;

import com.br.opet.dao.LoginDAO;
import com.br.opet.domain.entity.User;

@Stateless
public class LoginDAOImpl implements LoginDAO {
	
	String validUsername = "teste";
	String validPassword = "123";
	
	@Override
	public Boolean validCredentials(User subjectUser) throws Exception {
		
		if(subjectUser.getUsername().equals(this.validUsername) && subjectUser.getPassword().equals(validPassword))
			return Boolean.TRUE;
		
		return Boolean.FALSE;
	}

}
