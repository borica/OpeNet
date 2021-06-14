package com.br.opet.dao.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "openet";
    private static final String PASSWORD = "openet123";
	
    public static Connection getConn() {
    	
    	Connection conn = null;
    	
    	try {
    		
    		Class.forName("oracle.jdbc.driver.OracleDriver");
        	conn = DriverManager.getConnection(URL, USER, PASSWORD);
        	
        } catch (Exception e) {
            System.out.println("falha ao conectar: "+ e);
        }
        return conn;
    }

    public void closeConn(Connection conn){
        if(conn != null) {
            try {
            	conn.close();
                System.out.println("conexao fechada com sucesso");
            } catch (SQLException e) {
                System.out.println("erro ao desconectar: "+ e);
            }
        }
    }
}
