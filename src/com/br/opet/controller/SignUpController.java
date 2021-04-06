package com.br.opet.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.br.opet.controller.base.BaseController;
import com.br.opet.domain.entity.User;

@SessionScoped
@ManagedBean
public class SignUpController extends BaseController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private User signUpUser;
	
	@PostConstruct
	public void init() {
		this.signUpUser = new User();
	}

	public User getSignUpUser() {
		return signUpUser;
	}

	public void setSignUpUser(User signUpUser) {
		this.signUpUser = signUpUser;
	}
	
}
