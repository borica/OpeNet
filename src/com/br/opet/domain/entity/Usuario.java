package com.br.opet.domain.entity;

import java.util.Date;

import com.br.opet.domain.dto.GoogleTokenDTO;
import com.br.opet.domain.dto.GoogleUserInfoDTO;

public class Usuario {
	
	private Integer id;
	private String username;
	private String password;
	private String fullName;
	private Date lastLogin;
	private Integer anoInicio; 
	private Curso curso;
	private GoogleTokenDTO googleTokenDTO;
	private GoogleUserInfoDTO googleUserInfoDTO;
	private boolean isGoogleUser;
	
	public Usuario(){
		this.curso = new Curso();
	}
	
	public Usuario(String username, String password, Date lastLogin) {
		this.username = username;
		this.password = password;
		this.lastLogin = lastLogin;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public GoogleUserInfoDTO getGoogleUserInfoDTO() {
		return googleUserInfoDTO;
	}

	public void setGoogleUserInfoDTO(GoogleUserInfoDTO googleUserInfoDTO) {
		this.googleUserInfoDTO = googleUserInfoDTO;
	}

	public GoogleTokenDTO getGoogleTokenDTO() {
		return googleTokenDTO;
	}

	public void setGoogleTokenDTO(GoogleTokenDTO googleTokenDTO) {
		this.googleTokenDTO = googleTokenDTO;
	}

	public boolean isGoogleUser() {
		return isGoogleUser;
	}

	public void setGoogleUser(boolean isGoogleUser) {
		this.isGoogleUser = isGoogleUser;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Integer getAnoInicio() {
		return anoInicio;
	}

	public void setAnoInicio(Integer anoInicio) {
		this.anoInicio = anoInicio;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", password=" + password + ", fullName=" + fullName
				+ ", lastLogin=" + lastLogin + ", anoInicio=" + anoInicio + ", curso=" + curso + ", googleTokenDTO="
				+ googleTokenDTO + ", googleUserInfoDTO=" + googleUserInfoDTO + ", isGoogleUser=" + isGoogleUser + "]";
	}

}
