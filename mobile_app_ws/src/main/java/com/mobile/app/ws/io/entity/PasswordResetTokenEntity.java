package com.mobile.app.ws.io.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity(name = "password_reset_tokens")
public class PasswordResetTokenEntity implements Serializable {

	private static final long serialVersionUID = -976943850193348434L;
	
	@Id
	@GeneratedValue
	protected long id;

	protected String token;
	
	@OneToOne
	@JoinColumn(name = "users_id")
	protected UserEntity userDetails;

	public long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public UserEntity getUserDetails() {
		return userDetails;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUserDetails(UserEntity userDetails) {
		this.userDetails = userDetails;
	}
	
	
}
