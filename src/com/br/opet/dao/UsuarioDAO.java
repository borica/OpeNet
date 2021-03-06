package com.br.opet.dao;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Local;

import com.br.opet.domain.entity.Usuario;

@Local
public interface UsuarioDAO {

	Boolean salvarUsuario(Usuario saveUser) throws Exception; 
	Boolean verifyUserCredentials(Usuario verifyUser) throws Exception;
	Boolean usernameExists(String username) throws SQLException;
	Boolean generateDomainSalt() throws SQLException;
	List<Usuario> usersNotApprovedId() throws SQLException;
	Boolean approveUserById(int id) throws SQLException;
}
