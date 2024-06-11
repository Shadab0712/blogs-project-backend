package com.blogsproject.dto;

import org.springframework.stereotype.Component;

@Component
public class JWTLoginSucessReponse {

	private String token;
	private UserDTO user;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "JWTLoginSucessReponse{" + ", token='" + token + '\'' + '}';
	}

	public JWTLoginSucessReponse(String token, UserDTO user) {
		this.token = token;
		this.user = user;
	}

	public JWTLoginSucessReponse(String token) {
		this.token = token;
	}

	public JWTLoginSucessReponse() {

	}

}
