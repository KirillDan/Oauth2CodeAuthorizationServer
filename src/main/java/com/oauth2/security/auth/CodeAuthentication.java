package com.oauth2.security.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CodeAuthentication extends UsernamePasswordAuthenticationToken {
	public CodeAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
