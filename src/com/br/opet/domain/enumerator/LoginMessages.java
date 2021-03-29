package com.br.opet.domain.enumerator;

public enum LoginMessages {

	LOGIN_INVALIDO("Usuário ou senha inválidos!"),
	LOGIN_VAZIO("Erro de login. Tente novamente."),
	LOGIN_GOOGLE_ERROR("Ocorreu um erro ao realizar o login com a conta Google. Tente novamente.");
	
	private String message;
	
	private LoginMessages(String message) {
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
