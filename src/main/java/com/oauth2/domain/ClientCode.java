package com.oauth2.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ClientCode {
	@Id
	@GeneratedValue
	private Long id;
	private String code;
	private Date expired;
	private String clientId;
	private String username;
	
	public ClientCode() {
	}

	public ClientCode(String code, String clientId, String username) {
		this.code = code;
		this.expired = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
		this.clientId = clientId;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getExpired() {
		return expired;
	}

	public void setExpired(Date expired) {
		this.expired = expired;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}	
}
