package com.br.opet.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.Stateless;
import javax.management.RuntimeErrorException;

import org.apache.commons.codec.digest.Crypt;

import com.br.opet.dao.AbstractDAO;
import com.br.opet.dao.UsuarioDAO;
import com.br.opet.dao.factory.ConnectionFactory;
import com.br.opet.domain.entity.Usuario;

@Stateless
public class UsuarioDAOImpl extends AbstractDAO implements UsuarioDAO {
	
	private static final String TAG = UsuarioDAOImpl.class.getName() + ": ";
	
	private PreparedStatement ps;
	
	@Override
	public Boolean salvarUsuario(Usuario saveUser) throws SQLException {
		try {
			conn = ConnectionFactory.getConn();
			String salt = getDomainSalt();
			
			if(conn != null) {
				
				StringBuilder queryStb = new StringBuilder();
				
				queryStb.append("INSERT INTO LOGIN_USER (ID, USERNAME, FULL_NAME, PASSWORD, ACTIVE) ");
				queryStb.append("VALUES (LOGIN_USER_SEQ.nextval, ?, ?, ?, ?) ");
				
				ps = conn.prepareStatement(queryStb.toString());
				
				ps.setString(1, saveUser.getUsername());
				ps.setString(2, saveUser.getFullName());
				ps.setString(3, Crypt.crypt(saveUser.getPassword(), salt));
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


	@Override
	public Boolean verifyUserCredentials(Usuario verifyUser) throws Exception {
		boolean validCredentials = false;
		try {
			conn = ConnectionFactory.getConn();
		    ResultSet rs = null;
		    String salt = getDomainSalt();
		    
			if(conn != null) {
		
				StringBuilder queryStb = new StringBuilder();
				
				queryStb.append("SELECT COUNT(*) FROM LOGIN_USER ");
				queryStb.append("WHERE USERNAME = ? AND PASSWORD = ? ");
				
				ps = conn.prepareStatement(queryStb.toString());
				
				String crypt = Crypt.crypt(verifyUser.getPassword(), salt);
				
				ps.setString(1, verifyUser.getUsername());
				ps.setString(2, crypt);
				
				logSelect(TAG, queryStb.toString());
				
				rs = ps.executeQuery();
				
				if(rs.next()) {
					int numberOfRows = rs.getInt(1);
					
					if(numberOfRows == 0) {
						validCredentials = false;
					} else {
						validCredentials = true;
					}
				}
				
				ps.close();
				ps = null;
			}
			return validCredentials;
		} catch (Exception e) {
			logger.error(TAG + e.getMessage());
			throw new RuntimeErrorException(null); 
		} finally {
			conn.close();
		}
	}


	@Override
	public Boolean usernameExists(String username) throws SQLException   {
		boolean exists = false;
		try {
			conn = ConnectionFactory.getConn();
		    ResultSet rs = null;
		    
			if(conn != null) {
		
				StringBuilder queryStb = new StringBuilder();
				
				queryStb.append("SELECT COUNT(*) FROM LOGIN_USER ");
				queryStb.append("WHERE USERNAME = ? ");
				
				ps = conn.prepareStatement(queryStb.toString());
				
				ps.setString(1, username);
				
				logSelect(TAG, queryStb.toString());
				
				rs = ps.executeQuery();
				
				if(rs.next()) {
					int numberOfRows = rs.getInt(1);
					
					if(numberOfRows == 0) {
						exists = false;
					} else {
						exists = true;
					}
				}
				
				ps.close();
				ps = null;
			}
			return exists;
		} catch (Exception e) {
			logger.error(TAG + e.getMessage());
			throw new RuntimeErrorException(null); 
		} finally {
			conn.close();
		}
	}
}
