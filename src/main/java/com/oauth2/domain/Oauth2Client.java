package com.oauth2.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Oauth2Client {
	@Id
	private String client_id;
	private String client_secret;
	
	public Oauth2Client() {
	}

	public Oauth2Client(String client_id, String client_secret) {
		this.client_id = client_id;
		this.client_secret = client_secret;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
}
