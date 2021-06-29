package com.oauth2.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oauth2.domain.ClientCode;
import com.oauth2.repository.ClientCodeRepository;
import com.oauth2.repository.Oauth2ClientRepository;
import com.oauth2.repository.UserRepository;

@Component
public class ClientCodeService {

	private ClientCodeRepository clientCodeRepository;
	private Oauth2ClientRepository oauth2ClientRepository;
	private UserRepository userRepository;

	@Autowired
	public ClientCodeService(UserRepository userRepository, ClientCodeRepository clientCodeRepository,
			Oauth2ClientRepository oauth2ClientRepository) {
		this.clientCodeRepository = clientCodeRepository;
		this.oauth2ClientRepository = oauth2ClientRepository;
		this.userRepository = userRepository;
	}

	public boolean saveCode(String code, String clientId, String username) {
		boolean result = false;
		if (this.clientCodeRepository.existsByClientIdAndUsername(clientId, username)) {
			this.clientCodeRepository.removeByClientIdAndUsername(clientId, username);
		}
		if (this.oauth2ClientRepository.existsById(clientId) && this.userRepository.existsByUsername(username)) {
			return null != this.clientCodeRepository.save(new ClientCode(code, clientId, username));
		}
		return result;
	}

	public Optional<ClientCode> find(String clientId, String username) {
		return this.clientCodeRepository.findByClientIdAndUsername(clientId, username);
	}
}
