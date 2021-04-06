package com.br.opet.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.br.opet.business.GoogleOauthBusiness;
import com.br.opet.business.LoginBusiness;
import com.br.opet.controller.base.BaseController;
import com.br.opet.domain.dto.GoogleTokenDTO;
import com.br.opet.domain.entity.User;
import com.br.opet.domain.enumerator.LoginMessages;
import com.google.common.base.Strings;

@ManagedBean
@SessionScoped
public class LoginController extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String TAG = LoginController.class.getName();
	
	static Logger logger = Logger.getLogger(LoginController.class);
	
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
			logger.error(e.getMessage());
		}
	}
	
	public void checkForGCode() {
		try {
			logger.info(TAG + " Realizando verificação para codigo de retorno da API Google.");
			String gCode = (String) getSessionParameter("code");
			GoogleTokenDTO googleTokenDTO = (GoogleTokenDTO) getSessionAttribute("googleTokenDTO");
			
			if(!Strings.isNullOrEmpty(gCode)){
				googleTokenDTO = googleOauthBusiness.getGoogleTokenByAuthCode(gCode);
			}
			if(googleTokenDTO != null) {
				User gUser = googleOauthBusiness.getGoogleUserAsUser(googleTokenDTO);
				gUser.setGoogleTokenDTO(googleTokenDTO);
				gUser.setLastLogin(new Date());
				gUser.setGoogleUser(true);
				
				setSessionAttribute("loggedUser", gUser);
				
				contextRedirect(PAGE_DASHBOARD);
				logger.info(TAG + " Tentativa de login com Google foi bem sucedida.");
			}
			if(!Strings.isNullOrEmpty(gCode) && googleTokenDTO == null){
				addMessage(FacesMessage.SEVERITY_ERROR, "Erro!", LoginMessages.LOGIN_GOOGLE_ERROR.getMessage());
				logger.warn(TAG + " Tentativa de login com Google falhou.");
			}
		} catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Erro!", LoginMessages.LOGIN_GOOGLE_ERROR.getMessage());
			logger.error(e.getMessage());
		}
	}
	
	public void login() {
		logger.info(TAG + " Realizando tentativa de login.");
		try {
			if(validateLogin()) {
				User loggedUser = new User();
				loggedUser.setUsername(username);
				loggedUser.setLastLogin(new Date());
				setSessionAttribute("loggedUser", loggedUser);
				cleanFields();
				contextRedirect(PAGE_DASHBOARD);
				logger.info(TAG + " Tentativa bem sucedida.");
			} else {
				cleanFields();
				addMessage(FacesMessage.SEVERITY_ERROR, "Erro!", LoginMessages.LOGIN_VAZIO.getMessage());
				logger.warn(TAG + " Tentativa de login falhou.");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void loginWithGoogle() {
		try {
			logger.info(TAG + " Redirecionando para pagina de login com Google.");
			externalRedirect(googleLoginUrl);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void signUp() {
		try {
			logger.info(TAG + " Redirecionando para pagina de cadastro.");
			contextRedirect(PAGE_SIGNUP);
		} catch (IOException e) {
			logger.error(e.getMessage());
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
