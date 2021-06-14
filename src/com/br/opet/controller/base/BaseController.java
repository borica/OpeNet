package com.br.opet.controller.base;

import java.io.IOException;
import java.util.PriorityQueue;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.br.opet.controller.SignUpController;
import com.br.opet.domain.entity.Usuario;

public abstract class BaseController {
	
	protected final String PAGE_LOGIN = "/login.xhtml";
	protected final String PAGE_DASHBOARD = "/dashboard/dashboard.xhtml";
	protected final String PAGE_SIGNUP = "/signup/signup.xhtml";
	
	protected static Logger logger = Logger.getLogger(BaseController.class);
	
	protected void externalRedirect(String route) throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().redirect(route);
	}
	
	protected void contextRedirect(String route) throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		String path = context.getExternalContext().getRequestContextPath();
		context.getExternalContext().redirect(path + route);
	}
	
	protected void setSessionAttribute(String nome, Object object) {
		FacesContext context = FacesContext.getCurrentInstance();
		((HttpServletRequest)context.getExternalContext().getRequest()).getSession().setAttribute(nome, object);
	}
	
	protected Object getSessionAttribute(String atributo) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest)context.getExternalContext().getRequest();
		return req.getSession().getAttribute(atributo);
	}
	
	protected PriorityQueue<Integer> getUsersToApproveQueue() {
		PriorityQueue<Integer> sessionQueue = (PriorityQueue<Integer>) getSessionAttribute("usersToApproveQueue");
		if(sessionQueue == null) {
			sessionQueue = new PriorityQueue<Integer>();
			setSessionAttribute("usersToApproveQueue", sessionQueue);
		}
		return sessionQueue;
	}
	
	protected void invalidateSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest)context.getExternalContext().getRequest();
		req.getSession().invalidate();
	}
	
	protected void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
	
	protected Object getSessionParameter(String atributo) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest)context.getExternalContext().getRequest();
		return req.getParameter(atributo);
	}
	
}
