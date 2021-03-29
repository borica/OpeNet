package com.br.opet.domain.dto;

import com.google.gson.annotations.SerializedName;

public class GoogleUserInfoDTO {
	
	@SerializedName("id")
	private String googleId;
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("given_name")
	private String given_name;
	
	@SerializedName("family_name")
	private String family_name;
	
	@SerializedName("picture")
	private String picture_url;
	
	@SerializedName("locale")
	private String locale;

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGiven_name() {
		return given_name;
	}

	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public String getPicture_url() {
		return picture_url;
	}

	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
}