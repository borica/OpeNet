package com.br.opet.dao;

import org.apache.log4j.Logger;

public abstract class AbstractDAO {
	
	protected static Logger logger = Logger.getLogger(AbstractDAO.class);
	
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
