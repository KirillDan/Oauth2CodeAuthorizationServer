package com.oauth2.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.oauth2.domain.ClientCode;

public interface ClientCodeRepository extends CrudRepository<ClientCode, Long> {
	Optional<ClientCode> findByClientIdAndUsername(String clientId, String username);
	@Transactional
	void removeByClientIdAndUsername(String clientId, String username);
	Boolean existsByClientIdAndUsername(String clientId, String username);
}
