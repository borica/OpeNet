package com.br.opet.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.management.RuntimeErrorException;

import com.br.opet.dao.AbstractDAO;
import com.br.opet.dao.CursoDAO;
import com.br.opet.dao.factory.ConnectionFactory;
import com.br.opet.domain.entity.Curso;

@Stateless
public class CursoDAOImpl extends AbstractDAO implements CursoDAO {
	
	private static final String TAG = CursoDAOImpl.class.getName() + ": ";
	
	private Connection conn;
	private PreparedStatement ps;
	
	@Override
	public Boolean salvarCurso(Curso saveCurso) throws SQLException {
		try {
			conn = ConnectionFactory.getConn();
			
			if(conn != null) {
				
				StringBuilder queryStb = new StringBuilder();
				
				queryStb.append("INSERT INTO CURSO (ID, CURSO_DESC, ACTIVE) ");
				queryStb.append("VALUES (CURSO_SEQ.nextval, ?, ?) ");
				
				ps = conn.prepareStatement(queryStb.toString());
				
				ps.setString(1, saveCurso.getDescricao());
				ps.setString(2, saveCurso.getActive());
				
				logInsert(TAG, queryStb.toString());
				if (ps.executeUpdate() == 1) {
					return true;
				}
			}
		} catch (SQLException e) {
			logger.error(TAG + e.getMessage());
			throw new RuntimeErrorException(null); 
		} finally {
			conn.close();
		}
		return false;
	}

	@Override
	public Boolean salvarCursosBulk(List<Curso> saveCursosList) throws SQLException {
		Boolean saved = Boolean.FALSE;
		try {
			conn = ConnectionFactory.getConn();
			
			if(conn != null) {
				
				for (Curso curso : saveCursosList) {
					
					StringBuilder queryStb = new StringBuilder();
					
					//INSERINDO NOVO CURSO
					queryStb.append("INSERT INTO CURSO (ID, CURSO_DESC, ACTIVE) ");
					queryStb.append("VALUES (CURSO_SEQ.nextval, ?, ?) ");
					
					ps = conn.prepareStatement(queryStb.toString());
					
					ps.setString(1, curso.getDescricao());
					ps.setString(2, curso.getActive());
					
					logInsert(TAG, queryStb.toString());
					if (ps.executeUpdate() == 1) {
						saved = true;
					}
					ps.close();
					ps = null;
				}
				
				return saved;
			}
		} catch (Exception e) {
			logger.error(TAG + e.getMessage());
			throw new RuntimeErrorException(null); 
		} finally {
			conn.close();
			
		}
		return saved;
	}

	@Override
	public List<Curso> listarCursos() throws SQLException {
		List<Curso> listCursos = new ArrayList<Curso>();
		try {
			conn = ConnectionFactory.getConn();
			
			if(conn != null) {
		
				StringBuilder queryStb = new StringBuilder();
				
				queryStb.append("SELECT * FROM CURSO ");
				
				ps = conn.prepareStatement(queryStb.toString());
				
				logSelect(TAG, queryStb.toString());
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					Curso newLine = new Curso();
					newLine.setId(rs.getInt("ID"));
					newLine.setDescricao(rs.getString("CURSO_DESC"));
					newLine.setActive(rs.getString("ACTIVE"));
					listCursos.add(newLine);
				}
				
				ps.close();
				ps = null;
			}
			
			return listCursos;
		} catch (Exception e) {
			logger.error(TAG + e.getMessage());
			throw new RuntimeErrorException(null); 
		} finally {
			conn.close();
		}
	}
}
