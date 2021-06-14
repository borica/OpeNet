package com.br.opet.service.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.br.opet.dao.LoginDAO;
import com.br.opet.dao.UsuarioDAO;
import com.br.opet.domain.entity.Usuario;
import com.br.opet.service.LoginService;

@Stateless
public class LoginServiceImpl implements LoginService {
	
	@EJB
	LoginDAO loginDao;
	
	@EJB
	UsuarioDAO usuarioDAO;
	
	@Override
	public Boolean verifyUserCredentials(Usuario subjectUser) throws Exception {
		return this.usuarioDAO.verifyUserCredentials(subjectUser);
	}

	@Override
	public Boolean generateDomainSalt() throws Exception {
		return this.usuarioDAO.generateDomainSalt();
	}

}
