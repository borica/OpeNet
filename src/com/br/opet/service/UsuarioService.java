package com.br.opet.service;

import javax.ejb.Local;

import com.br.opet.domain.entity.Usuario;

@Local
public interface UsuarioService {
	
	Boolean salvarUsuario(Usuario saveUser) throws Exception;
	
}
