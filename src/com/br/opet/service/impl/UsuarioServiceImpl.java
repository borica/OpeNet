package com.br.opet.service.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.br.opet.dao.UsuarioDAO;
import com.br.opet.domain.entity.Usuario;
import com.br.opet.service.UsuarioService;

@Stateless
public class UsuarioServiceImpl implements UsuarioService {
	
	@EJB
	UsuarioDAO usuarioDAO;
	
	@Override
	public Boolean salvarUsuario(Usuario saveUser) throws Exception {
		return usuarioDAO.salvarUsuario(saveUser);
	}

}
