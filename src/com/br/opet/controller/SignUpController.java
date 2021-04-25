package com.br.opet.controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.br.opet.controller.base.BaseController;
import com.br.opet.domain.entity.Curso;
import com.br.opet.domain.entity.Usuario;
import com.br.opet.domain.enumerator.LoginMessages;
import com.br.opet.service.CursoService;
import com.br.opet.service.UsuarioService;
import com.google.common.base.Strings;

import sun.text.normalizer.UBiDiProps;

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
	
	//UI Control Variables
	private boolean usernameAvailablePanelGroup = false;
	private boolean usernameIsAvailable = false;
	private boolean usernameIsNotAvailable = false;
	
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
				this.newUser = new Usuario();
				addMessage(FacesMessage.SEVERITY_INFO, "Cadastro", "Cadastro realizado com sucesso!");
			} catch (Exception e) {
				logger.error(TAG + e.getMessage());
			}
		}
	}
	
	// AJAX REQUEST LISTENER
	public void verifyUsernameAvailable() {
		logger.info(TAG + "AJAX Function");
		if(!Strings.isNullOrEmpty(newUser.getUsername())) {
			try {
				if(usuarioService.usernameExists(newUser.getUsername())) {
					usernameIsAvailable = false;
					usernameIsNotAvailable = true;
					logger.info(TAG + "Usu�rio j� existe !");
				} else {
					usernameIsAvailable = true;
					usernameIsNotAvailable = false;
					logger.info(TAG + "Usu�rio n�o existe !");
				}
			} catch (Exception e) {
				logger.error(TAG + e.getMessage());
			}
		} else {
			usernameIsAvailable = false;
			usernameIsNotAvailable = false;
		}
	}
	
	// AJAX REQUEST LISTENER
	public void showUsernameAvailableGrid() {
		logger.info(TAG + "AJAX Function SHOW UsernameAvailablePanel");
		usernameAvailablePanelGroup = true;
	}
	
	// AJAX REQUEST LISTENER
	public void hideUsernameAvailableGrid() {
		logger.info(TAG + "AJAX Function HIDE UsernameAvailablePanel");
		usernameAvailablePanelGroup = false;
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
			logger.info(TAG + "Redirecionando para p�gina de login.");
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

	public boolean isUsernameAvailablePanelGroup() {
		return usernameAvailablePanelGroup;
	}


	public void setUsernameAvailablePanelGroup(boolean usernameAvailablePanelGroup) {
		this.usernameAvailablePanelGroup = usernameAvailablePanelGroup;
	}


	public boolean isUsernameIsAvailable() {
		return usernameIsAvailable;
	}


	public void setUsernameIsAvailable(boolean usernameIsAvailable) {
		this.usernameIsAvailable = usernameIsAvailable;
	}


	public boolean isUsernameIsNotAvailable() {
		return usernameIsNotAvailable;
	}


	public void setUsernameIsNotAvailable(boolean usernameIsNotAvailable) {
		this.usernameIsNotAvailable = usernameIsNotAvailable;
	}
	
}