package com.br.opet.business.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.br.opet.business.LoginBusiness;
import com.br.opet.dao.LoginDAO;
import com.br.opet.domain.entity.User;

@Stateless
public class LoginBusinessImpl implements LoginBusiness {
	
	@EJB
	LoginDAO loginDao;
	
	@Override
	public Boolean validCredentials(User subjectUser) throws Exception {
		return this.loginDao.validCredentials(subjectUser);
	}

}
