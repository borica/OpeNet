package com.br.opet.controller;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.br.opet.business.GoogleOauthBusiness;
import com.br.opet.business.LoginBusiness;
import com.br.opet.controller.base.BaseController;
import com.br.opet.entity.User;
import com.br.opet.enumerator.LoginMessages;
import com.google.common.base.Strings;

@ManagedBean
@SessionScoped
public class LoginController extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private LoginBusiness loginBusiness;
	@EJB
	private GoogleOauthBusiness googleOauthBusiness;
	
	private String username;
	private String password;
	private String googleLoginUrl;
	
	@PostConstruct
	public void init() {
		try {
			this.googleLoginUrl = googleOauthBusiness.getGoogleOauthLink();
			cleanFields();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkForGCode() {
		String gCode = (String) getSessionParameter("code");
		
		if(Strings.isNullOrEmpty(gCode)){
			//TODO IMPLEMENT REST CALLS
		}
	}
	
	public void login() {
		try {
			if(validateLogin()) {
				setSessionAttribute("loggedUser", new User(username, password, new Date()));
				cleanFields();
				contextRedirect(PAGE_DASHBOARD);
			} else {
				cleanFields();
				addMessage(FacesMessage.SEVERITY_ERROR, "Erro!", LoginMessages.LOGIN_VAZIO.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loginWithGoogle() {
		try {
			externalRedirect(googleLoginUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean validateLogin() throws Exception {
		boolean isValid = true;

		if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) {
			isValid = false;
		}

		if (!loginBusiness.validCredentials(new User(username, password, null))) {
			isValid = false;
		}

		return isValid;
	}

	private void cleanFields() {
		this.username = "";
		this.password = "";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
