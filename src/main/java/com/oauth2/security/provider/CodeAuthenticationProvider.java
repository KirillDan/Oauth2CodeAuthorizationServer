package com.oauth2.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.oauth2.security.auth.CodeAuthentication;

@Component
public class CodeAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
        String code = String.valueOf(authentication.getCredentials());
        if (!username.isBlank() || !code.isBlank()) {
        	return new CodeAuthentication(username, code);
        } else {
        	throw new BadCredentialsException("Bad credentials");
        }
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return CodeAuthentication.class.isAssignableFrom(authentication);
	}

}
