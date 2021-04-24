package com.br.opet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.br.opet.dao.factory.ConnectionFactory;

public abstract class AbstractDAO {
	
	protected static Logger logger = Logger.getLogger(AbstractDAO.class);
	
	protected Connection conn;
	
	protected String getDomainSalt() throws SQLException {
		ResultSet rs = null;
	    PreparedStatement ps;
	    String salt = "";
	    
	    conn = ConnectionFactory.getConn();
	    
		if(conn != null) {
	
			StringBuilder queryStb = new StringBuilder();
			
			queryStb.append("SELECT * FROM DOMAIN_SALT ");
			
			ps = conn.prepareStatement(queryStb.toString());
			
			logSelect(AbstractDAO.class.getName()+": ", queryStb.toString());
			rs = ps.executeQuery();
			
			if(rs.next()) {
				salt = rs.getString("salt");
			}
			
			ps.close();
			ps = null;
		}
		
		return salt;
	}
	
	protected void logInsert(String TAG, String query) {
		logger.info(TAG + "REALIZANDO NOVO INSERT NO BANCO DE DADOS!");
		logger.info(TAG + query);
	}
	
	protected void logSelect(String TAG, String query) {
		logger.info(TAG + "REALIZANDO NOVO SELECT NO BANCO DE DADOS!");
		logger.info(TAG + query);
	}
	
	protected void logDeleteLogico(String TAG, String query) {
		logger.info(TAG + "DELETANDO LOGICAMENTE NO BANCO DE DADOS!");
		logger.info(TAG + query);
	}
	
	protected void logDelete(String TAG, String query) {
		logger.info(TAG + "DELETANDO DO BANCO DE DADOS!");
		logger.info(TAG + query);
	}
	
	protected void logUpdate(String TAG, String query) {
		logger.info(TAG + "ATUALIZANDO BANCO DE DADOS!");
		logger.info(TAG + query);
	}

}
