package com.br.opet.util;

import java.util.Comparator;

import com.br.opet.domain.entity.Curso;

public class CursoComparator implements Comparator<Curso>
{  
	@Override
	public int compare(Curso o1, Curso o2) {
		return o1.getDescricao().compareTo(o2.getDescricao()); 
	} 
}