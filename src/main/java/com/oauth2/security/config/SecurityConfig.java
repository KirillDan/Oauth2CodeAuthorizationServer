package com.oauth2.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.oauth2.security.filter.OAuth2AuthorizeFilter;
import com.oauth2.security.provider.CodeAuthenticationProvider;
import com.oauth2.security.provider.UsernamePasswordAuthenticationProvider;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CodeAuthenticationProvider codeAuthenticationProvider;
	@Autowired
	private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
	@Autowired
	private OAuth2AuthorizeFilter oAuth2AuthorizeFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(codeAuthenticationProvider)
				.authenticationProvider(usernamePasswordAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests().mvcMatchers("/ui/admin/**").hasRole("ADMIN")
				.mvcMatchers("/ui/manager/**").hasRole("MANAGER").mvcMatchers("/ui/user/**").hasRole("USER")
				.mvcMatchers("/reg").anonymous()
//				.mvcMatchers("/login/oauth/authorize", "/login/oauth/access_token", "/user-info").authenticated()
				.mvcMatchers("/login/oauth/authorize").authenticated()
				.mvcMatchers("/login/oauth/access_token", "/user-info").permitAll()
				.anyRequest().hasRole("ADMIN").and()
				.formLogin(withDefaults())
				.addFilterAfter(oAuth2AuthorizeFilter, BasicAuthenticationFilter.class);
	}

}
