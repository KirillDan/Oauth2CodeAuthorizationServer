package com.oauth2.security.provider;

import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
        String code = String.valueOf(authentication.getCredentials());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (!username.isBlank() || !code.isBlank()) {
        	return new UsernamePasswordAuthenticationToken(username, code, authorities);
        } else {
        	throw new BadCredentialsException("Bad credentials");
        }
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}