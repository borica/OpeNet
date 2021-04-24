package com.br.opet.service;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Local;

import com.br.opet.domain.entity.Curso;

@Local
public interface CursoService {
	
	List<Curso> listarCursos() throws SQLException;
	Boolean salvarCurso(Curso saveCurso) throws SQLException;
	Boolean salvarCursosBulk(List<Curso> saveCursosList) throws SQLException;
	
}
