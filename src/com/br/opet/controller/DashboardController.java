package com.br.opet.controller;

import java.io.IOException;
import java.util.PriorityQueue;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.br.opet.controller.base.BaseController;
import com.br.opet.domain.entity.Usuario;
import com.br.opet.service.GoogleOauthService;
import com.br.opet.service.UsuarioService;

@SessionScoped
@ManagedBean
public class DashboardController extends BaseController {
	
	@EJB
	private GoogleOauthService googleOauthBusiness;
	
	@EJB
	private UsuarioService usuarioService;
	
	private Usuario loggedUser;
	private PriorityQueue<Integer> usersToApprove;
	private Integer userToApproveId;
	
	@PostConstruct
	public void init() {
		try {
			loggedUser = (Usuario) getSessionAttribute("loggedUser");
			usersToApprove = usuarioService.usersNotApprovedId();
			setUserToApproveId(usersToApprove.peek());
			if(getLoggedUser() == null){
				contextRedirect(PAGE_LOGIN);
			}
			invalidateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void logout() {
		try {
			invalidateSession();
			contextRedirect(PAGE_LOGIN);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void approveUser() {
		try {
			if(usuarioService.approveUser(userToApproveId)) {
				logger.info("Usuário: "+ userToApproveId + " aprovado com sucesso.");
				usersToApprove.remove();
				userToApproveId = usersToApprove.peek(); 
			} else {
				logger.error("Erro ao aprovar o usuario ID:  " + userToApproveId);
			}
			
		} catch (Exception e) {
			logger.error("Erro ao aprovar o usuario ID:  "+ userToApproveId + "erro: "+ e.getMessage());
		}
	}
	
	public Usuario getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(Usuario loggedUser) {
		this.loggedUser = loggedUser;
	}

	public Integer getUserToApproveId() {
		return userToApproveId;
	}

	public void setUserToApproveId(Integer userToApproveId) {
		this.userToApproveId = userToApproveId;
	}
	
}
