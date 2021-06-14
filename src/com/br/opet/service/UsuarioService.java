package com.br.opet.service;

import java.util.PriorityQueue;

import javax.ejb.Local;

import com.br.opet.domain.entity.Usuario;

@Local
public interface UsuarioService {
	
	Boolean salvarUsuario(Usuario saveUser) throws Exception;
	Boolean usernameExists(String username) throws Exception;
	PriorityQueue<Integer> usersNotApprovedId() throws Exception;
	Boolean approveUser(int id) throws Exception;
}
