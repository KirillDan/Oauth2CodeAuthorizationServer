package com.oauth2.security.filter;

import java.io.IOException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.oauth2.domain.Oauth2Client;
import com.oauth2.security.auth.CodeAuthentication;
import com.oauth2.service.ClientCodeService;
import com.oauth2.service.Oauth2ClientService;
import com.oauth2.util.CodeGenerator;

@Component
public class OAuth2AuthorizeFilter extends OncePerRequestFilter {
	@Autowired
	private Oauth2ClientService oauth2ClientService;
	@Autowired
	private CodeGenerator codeGenerator;
	@Autowired
	private ClientCodeService clientCodeService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.err.println("RequestURI  ==  " + request.getRequestURI());
		String clientId = request.getParameter("client_id");
		SecurityContext securityContex = SecurityContextHolder.getContext();
		try {
			Oauth2Client oauth2Client = this.oauth2ClientService.findOauth2ClientByClientId(clientId)
					.orElseThrow(EntityNotFoundException::new);
			Authentication usernamePasswordAuthentication = securityContex.getAuthentication();
			String username = usernamePasswordAuthentication.getName();
			String code = this.codeGenerator.generate();
			if (this.clientCodeService.saveCode(code, clientId, username) == false) {
				throw new EntityNotFoundException();
			}
			Authentication a = new CodeAuthentication(username, code);
			securityContex.setAuthentication(a);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return;
		}
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return !"/login/oauth/authorize".equals(request.getRequestURI().trim());
	}

}
