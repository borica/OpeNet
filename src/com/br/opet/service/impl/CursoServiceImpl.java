package com.br.opet.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.br.opet.dao.CursoDAO;
import com.br.opet.domain.entity.Curso;
import com.br.opet.service.CursoService;
import com.br.opet.util.CursoComparator;

@Stateless
public class CursoServiceImpl implements CursoService {
	
	@EJB
	CursoDAO cursoDAO; 
	
	@Override
	public List<Curso> listarCursos() throws SQLException {
		List<Curso> listCursos = cursoDAO.listarCursos();
		listCursos.sort(new CursoComparator());
		return listCursos;
	}

	@Override
	public Boolean salvarCurso(Curso saveCurso) throws SQLException {
		return salvarCurso(saveCurso);
	}

	@Override
	public Boolean salvarCursosBulk(List<Curso> saveCursosList) throws SQLException {
		return cursoDAO.salvarCursosBulk(saveCursosList);
	}

}
