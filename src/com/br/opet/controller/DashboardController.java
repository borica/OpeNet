package com.br.opet.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.br.opet.business.GoogleOauthBusiness;
import com.br.opet.controller.base.BaseController;
import com.br.opet.domain.dto.GoogleTokenDTO;
import com.br.opet.domain.entity.User;

@SessionScoped
@ManagedBean
public class DashboardController extends BaseController {
	
	@EJB
	private GoogleOauthBusiness googleOauthBusiness;
	
	private User loggedUser;
	
	@PostConstruct
	public void init() {
		try {
			loggedUser = (User) getSessionAttribute("loggedUser");
			 
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

	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}
	
}
