package com.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.oauth2.domain.Oauth2Client;
import com.oauth2.domain.User;
import com.oauth2.repository.Oauth2ClientRepository;
import com.oauth2.repository.UserRepository;

@SpringBootApplication
public class TestingApplication {

	@Autowired
	private Oauth2ClientRepository oauth2ClientRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(TestingApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			Oauth2Client oauth2Client = new Oauth2Client("client", "secret");
			this.oauth2ClientRepository.save(oauth2Client);
			User user = new User("admin", this.passwordEncoder.encode("admin"), "ROLE_ADMIN");
			this.userRepository.save(user);
		};
	}
}
