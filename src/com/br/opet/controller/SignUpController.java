package com.br.opet.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.br.opet.controller.base.BaseController;
import com.br.opet.domain.entity.User;

@SessionScoped
@ManagedBean
public class SignUpController extends BaseController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final String TAG = SignUpController.class.getName() + ": ";
	
	static Logger logger = Logger.getLogger(SignUpController.class);
	
	private User signUpUser;
	
	@PostConstruct
	public void init() {
		this.signUpUser = new User();
	}
	
	
	public void login() {
		try {
			logger.info(TAG + "Redirecionando para página de login.");
			contextRedirect(PAGE_LOGIN);
		} catch (IOException e) {
			logger.error(TAG + e.getMessage());
		}
	}
	
	public User getSignUpUser() {
		return signUpUser;
	}

	public void setSignUpUser(User signUpUser) {
		this.signUpUser = signUpUser;
	}
	
}
