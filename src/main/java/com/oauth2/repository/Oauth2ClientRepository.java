package com.oauth2.repository;

import org.springframework.data.repository.CrudRepository;

import com.oauth2.domain.Oauth2Client;

public interface Oauth2ClientRepository extends CrudRepository<Oauth2Client, String> {

}
