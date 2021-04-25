package com.br.opet.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.br.opet.controller.base.BaseController;
import com.br.opet.domain.dto.GoogleTokenDTO;
import com.br.opet.domain.entity.Usuario;
import com.br.opet.service.GoogleOauthService;

@SessionScoped
@ManagedBean
public class DashboardController extends BaseController {
	
	@EJB
	private GoogleOauthService googleOauthBusiness;
	
	private Usuario loggedUser;
	
	@PostConstruct
	public void init() {
		try {
			loggedUser = (Usuario) getSessionAttribute("loggedUser");
			 
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

	public Usuario getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(Usuario loggedUser) {
		this.loggedUser = loggedUser;
	}
	
}
