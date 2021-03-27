package com.br.opet.enumerator;

public enum LoginMessages {

	LOGIN_INVALIDO("Usuário ou senha inválidos!"),
	LOGIN_VAZIO("Erro de login. Tente novamente.");
	
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
