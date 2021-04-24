package com.br.opet.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.ejb.Stateless;

import org.apache.commons.codec.digest.Crypt;

import com.br.opet.dao.AbstractDAO;
import com.br.opet.dao.UsuarioDAO;
import com.br.opet.dao.factory.ConnectionFactory;
import com.br.opet.domain.entity.Usuario;

@Stateless
public class UsuarioDAOImpl extends AbstractDAO implements UsuarioDAO {
	
	private static final String TAG = UsuarioDAOImpl.class.getName() + ": ";
	
	private Connection conn;
	private PreparedStatement ps;
	
	
	@Override
	public Boolean salvarUsuario(Usuario saveUser) throws SQLException {
		try {
			conn = ConnectionFactory.getConn();
			
			if(conn != null) {
				
				StringBuilder queryStb = new StringBuilder();
				
				queryStb.append("INSERT INTO LOGIN_USER (ID, USERNAME, FULL_NAME, PASSWORD, ACTIVE) ");
				queryStb.append("VALUES (LOGIN_USER_SEQ.nextval, ?, ?, ?, ?) ");
				
				ps = conn.prepareStatement(queryStb.toString());
				
				ps.setString(1, saveUser.getUsername());
				ps.setString(2, saveUser.getFullName());
				ps.setString(3, Crypt.crypt(saveUser.getPassword()));
				ps.setString(4, "Y");
				
				logInsert(TAG, queryStb.toString());
				if (ps.executeUpdate() == 1) {
					return true;
				}
			}
		} catch (SQLException e) {
			logger.error(TAG + e.getMessage());
			throw new RuntimeException();
		} finally {
			conn.close();
		}
		return false;
	}

}
