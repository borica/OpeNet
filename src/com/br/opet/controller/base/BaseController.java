package com.br.opet.controller.base;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {
	
	protected final String PAGE_LOGIN = "/login.xhtml";
	protected final String PAGE_DASHBOARD = "/dashboard/dashboard.xhtml";
	
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
