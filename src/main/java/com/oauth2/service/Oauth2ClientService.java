package com.oauth2.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oauth2.domain.Oauth2Client;
import com.oauth2.repository.Oauth2ClientRepository;

@Component
public class Oauth2ClientService {

private Oauth2ClientRepository oauth2ClientRepository;
	
	@Autowired
	public Oauth2ClientService(Oauth2ClientRepository oauth2ClientRepository) {
		this.oauth2ClientRepository = oauth2ClientRepository;
	}
	
	public Optional<Oauth2Client> findOauth2ClientByClientIdAndKey(String client_id, String client_secret) {
		Optional<Oauth2Client> result = Optional.empty();
		Oauth2Client oauth2Client = this.oauth2ClientRepository.findById(client_id).orElseThrow(EntityNotFoundException::new);
		if (oauth2Client.getClient_secret().equals(client_secret)) {
			result = Optional.of(oauth2Client);
		}
		return result;
	}
	
	public Optional<Oauth2Client> findOauth2ClientByClientId(String client_id) {
		return this.oauth2ClientRepository.findById(client_id);
	}
}
