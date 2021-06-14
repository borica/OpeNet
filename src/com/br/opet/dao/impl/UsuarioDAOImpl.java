package com.br.opet.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
				
				queryStb.append("INSERT INTO LOGIN_USER (ID, USERNAME, FULL_NAME, PASSWORD, CURSO_ID, ANO_INICIO, ACTIVE) ");
				queryStb.append("VALUES (LOGIN_USER_SEQ.nextval, ?, ?, ?, ?, ?, ?) ");
				
				ps = conn.prepareStatement(queryStb.toString());
				
				ps.setString(1, saveUser.getUsername());
				ps.setString(2, saveUser.getFullName());
				ps.setString(3, Crypt.crypt(saveUser.getPassword(), salt));
				ps.setInt(4, saveUser.getCurso().getId());
				ps.setInt(5, saveUser.getAnoInicio());
				ps.setString(6, "N");
				
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


	@Override
	public Boolean generateDomainSalt() throws SQLException {
		boolean validCredentials = false;
		try {
			conn = ConnectionFactory.getConn();
		    
			if(conn != null) {
		
				StringBuilder queryStb = new StringBuilder();
				
				queryStb.append("INSERT INTO DOMAIN_SALT (id, salt, salt_date) ");
				queryStb.append("values(DOMAIN_SALT_SEQ.nextval, ?, ?) ");
				
				ps = conn.prepareStatement(queryStb.toString());
				
				String crypt = Crypt.crypt("openet123");
				java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
				
				ps.setString(1, crypt);
				ps.setDate(2, sqlDate);
				
				logSelect(TAG, queryStb.toString());
				
				int rows = ps.executeUpdate();
				
				ps.close();
				
				validCredentials = (rows > 0) ? true : false; 
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
	public List<Usuario> usersNotApprovedId() throws SQLException {
		List<Usuario> notApprovedUsersList = new ArrayList<>();
		try {
			conn = ConnectionFactory.getConn();
		    ResultSet rs = null;
		    
			if(conn != null) {
		
				StringBuilder queryStb = new StringBuilder();
				
				queryStb.append("SELECT * FROM LOGIN_USER ");
				queryStb.append("WHERE ACTIVE = ? ");
				
				ps = conn.prepareStatement(queryStb.toString());
				
				ps.setString(1, "N");
				
				logSelect(TAG, queryStb.toString());
				
				rs = ps.executeQuery();
				
				while(rs.next()) {
					Usuario notApprovedUser = new Usuario();
					notApprovedUser.setId(rs.getInt("id"));
					notApprovedUsersList.add(notApprovedUser);
				}
				
				ps.close();
			}
			return notApprovedUsersList;
		} catch (Exception e) {
			logger.error(TAG + e.getMessage());
			throw new RuntimeErrorException(null); 
		} finally {
			conn.close();
		}
	}
	
	@Override
	public Boolean approveUserById(int id) throws SQLException {
		Boolean approvedUser = false;
		try {
			conn = ConnectionFactory.getConn();
		    
			if(conn != null) {
		
				StringBuilder queryStb = new StringBuilder();
				
				queryStb.append("UPDATE LOGIN_USER ");
				queryStb.append("SET ACTIVE = ? ");
				queryStb.append("WHERE ID = ? ");
				
				ps = conn.prepareStatement(queryStb.toString());
				
				ps.setString(1, "Y");
				ps.setInt(2, id);
				
				logSelect(TAG, queryStb.toString());
				
				approvedUser = ps.executeUpdate() > 0 ? true : false;
				
				ps.close();
			}
			return approvedUser;
		} catch (Exception e) {
			logger.error(TAG + e.getMessage());
			throw new RuntimeErrorException(null); 
		} finally {
			conn.close();
		}
	}
}
