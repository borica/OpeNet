package com.br.opet.dao;

import javax.ejb.Local;

import com.br.opet.domain.entity.Usuario;

@Local
public interface UsuarioDAO {

	Boolean salvarUsuario(Usuario saveUser) throws Exception; 
	
}
