package com.br.opet.service.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.br.opet.dao.LoginDAO;
import com.br.opet.domain.entity.Usuario;
import com.br.opet.service.LoginService;

@Stateless
public class LoginServiceImpl implements LoginService {
	
	@EJB
	LoginDAO loginDao;
	
	@Override
	public Boolean validCredentials(Usuario subjectUser) throws Exception {
		return this.loginDao.validCredentials(subjectUser);
	}

}
