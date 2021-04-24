package com.br.opet.controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.br.opet.controller.base.BaseController;
import com.br.opet.domain.entity.Curso;
import com.br.opet.domain.entity.Usuario;
import com.br.opet.service.CursoService;
import com.br.opet.service.UsuarioService;
import com.google.common.base.Strings;

@SessionScoped
@ManagedBean
public class SignUpController extends BaseController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final String TAG = SignUpController.class.getName() + ": ";
	
	@EJB
	private CursoService cursoService;
	
	@EJB
	private UsuarioService usuarioService;
	
	private List<Curso> listCurso;
	
	private Usuario newUser;
	
	@PostConstruct
	public void init() {
		this.newUser = new Usuario();
		
		try {
			this.setListCurso(cursoService.listarCursos());
		} catch (SQLException e) {
			logger.error(TAG + e.getMessage());
		}
	}
	
	
	public void cadastrar() {
		if(cadastroIsValid()) {
			try {
				usuarioService.salvarUsuario(newUser);
			} catch (Exception e) {
				logger.error(TAG + e.getMessage());
			}
		}
	}
	
	private Boolean cadastroIsValid() {
		boolean isValid = true;
		
		if(Strings.isNullOrEmpty(newUser.getFullName())) {
			isValid = false;
		}
		if(Strings.isNullOrEmpty(newUser.getUsername())) {
			isValid = false;
		}
		if(Strings.isNullOrEmpty(newUser.getPassword())) {
			isValid = false;
		}
		if(newUser.getCurso().getId() == 0) {
			isValid = false;
		}
		return isValid;
	}
	
	public void login() {
		try {
			logger.info(TAG + "Redirecionando para página de login.");
			contextRedirect(PAGE_LOGIN);
		} catch (IOException e) {
			logger.error(TAG + e.getMessage());
		}
	}
	

	public List<Curso> getListCurso() {
		return listCurso;
	}

	public void setListCurso(List<Curso> listCurso) {
		this.listCurso = listCurso;
	}

	public Usuario getNewUser() {
		return newUser;
	}


	public void setNewUser(Usuario newUser) {
		this.newUser = newUser;
	}
	
}
