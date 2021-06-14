package com.br.opet.service.impl;

import java.util.List;
import java.util.PriorityQueue;

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

	@Override
	public Boolean usernameExists(String username) throws Exception {
		return usuarioDAO.usernameExists(username);
	}

	@Override
	public PriorityQueue<Integer> usersNotApprovedId() throws Exception {
		List<Usuario> notApprovedUsersIdList = usuarioDAO.usersNotApprovedId();
		PriorityQueue<Integer> approvedUsersIdQueue = new PriorityQueue<Integer>();
		for (Usuario usuario : notApprovedUsersIdList) {
			approvedUsersIdQueue.add(usuario.getId());
		}
		return approvedUsersIdQueue;
	}

	@Override
	public Boolean approveUser(int id) throws Exception {
		return usuarioDAO.approveUserById(id);
	}

}
