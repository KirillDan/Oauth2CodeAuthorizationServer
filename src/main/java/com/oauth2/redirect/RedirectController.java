package com.oauth2.redirect;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.oauth2.service.ClientCodeService;
import com.oauth2.service.Oauth2ClientService;
import com.oauth2.util.CodeGenerator;

@Controller
@RequestMapping("/")
public class RedirectController {

	private ClientCodeService clientCodeService;
	private CodeGenerator codeGenerator;

	@Autowired
	public RedirectController(ClientCodeService clientCodeService, CodeGenerator codeGenerator) {
		this.clientCodeService = clientCodeService;
		this.codeGenerator = codeGenerator;
	}

//https://github.com/login/oauth/authorize?client_id=a4622293877c8efdcddc&redirect_uri=http://localhost:8080/login/oauth2/code/github
//http://localhost:8080/login/oauth2/code/github?code=cb806f520aa30adcae64

//http://localhost:8085/login/oauth/authorize?client_id=a4622293877c8efdcddc&redirect_uri=http://localhost:8080/login/oauth2/code/github
	@GetMapping("/login/oauth/authorize")
	public RedirectView firstStepOauth(RedirectAttributes attributes, @RequestParam(required = true) String client_id,
			@RequestParam(required = false) String redirect_uri, @RequestParam(required = false) String state) {
		RedirectView result = new RedirectView();
		result.setStatusCode(HttpStatus.BAD_REQUEST);
		String redirectUri = null;
		try {
			redirectUri = new URI(redirect_uri).toString();
			result = new RedirectView(redirectUri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String code;
		SecurityContext securityContex = SecurityContextHolder.getContext();
		Authentication authentication = securityContex.getAuthentication();
		if (this.clientCodeService
				.find(client_id, authentication.getPrincipal().toString())
				.isPresent()) {
			code = codeGenerator.generate();
			attributes.addAttribute("code", code);
			result.setStatusCode(HttpStatus.FOUND);
		}
		attributes.addAttribute("state", state);
		return result;
	}

	/*
	 * curl -d
	 * "client_id=a4622293877c8efdcddc&client_secret=6f56170652febf1babc768bef0adafeb23775a1e&code=49c018ef53ba786ef596&redirect_uri=http://localhost:8080/login/oauth2/code/github"
	 * -X POST https://github.com/login/oauth/access_token
	 */
//access_token=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&scope=public_repo%2Cread%3Auser&token_type=bearer

//curl -d "client_id=a4622293877c8efdcddc&client_secret=6f56170652febf1babc768bef0adafeb23775a1e&code=49c018ef53ba786ef596&redirect_uri=http://localhost:8080/login/oauth2/code/github"  -X POST  http://localhost:8085/login/oauth/access_token
	@ResponseBody
	@PostMapping("/login/oauth/access_token")
	public String secondStepOauth(@RequestParam(required = true) String client_id,
			@RequestParam(required = false) String client_secret, @RequestParam(required = false) String code,
			@RequestParam(required = false) String redirect_uri, @RequestParam(required = false) String refresh_token,
			@RequestParam(required = false) String grant_type) {
		System.err.println(client_secret);
		if (!code.isBlank() && !redirect_uri.isBlank()) {

		} else if (!refresh_token.isBlank() && !grant_type.equalsIgnoreCase("refresh_token")) {

		}
//		String access_token = "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";
//		String scope = "read";
//		String token_type = "bearer";
//		String response = "access_token=" + access_token + "&scope=" + scope + "&token_type=" + token_type;
//		System.err.println("access_token STOP");
//		return response;
		return "{\n" + "  \"access_token\":\"MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3\",\n" + "  \"token_type\":\"bearer\",\n"
				+ "  \"expires_in\":3600,\n" + "  \"refresh_token\":\"IwOGYzYTlmM2YxOTQ5MGE3YmNmMDFkNTVk\",\n"
				+ "  \"scope\":\"read\"\n" + "}";
	}

	@ResponseBody
	@GetMapping("/user-info")
	public String user() {
		System.err.println("user STOP");
		return "{\n" + "  \"login\": \"KirillDan\",\n" + "\"user_name\":\"KirillDan\"," + "  \"id\": 52418581,\n"
				+ "  \"node_id\": \"MDQ6VXNlcjUyNDE4NTgx\",\n"
				+ "  \"avatar_url\": \"https://avatars.githubusercontent.com/u/52418581?v=4\",\n"
				+ "  \"gravatar_id\": \"\",\n" + "  \"url\": \"https://api.github.com/users/KirillDan\",\n"
				+ "  \"html_url\": \"https://github.com/KirillDan\",\n"
				+ "  \"followers_url\": \"https://api.github.com/users/KirillDan/followers\",\n"
				+ "  \"following_url\": \"https://api.github.com/users/KirillDan/following{/other_user}\",\n"
				+ "  \"gists_url\": \"https://api.github.com/users/KirillDan/gists{/gist_id}\",\n"
				+ "  \"starred_url\": \"https://api.github.com/users/KirillDan/starred{/owner}{/repo}\",\n"
				+ "  \"subscriptions_url\": \"https://api.github.com/users/KirillDan/subscriptions\",\n"
				+ "  \"organizations_url\": \"https://api.github.com/users/KirillDan/orgs\",\n"
				+ "  \"repos_url\": \"https://api.github.com/users/KirillDan/repos\",\n"
				+ "  \"events_url\": \"https://api.github.com/users/KirillDan/events{/privacy}\",\n"
				+ "  \"received_events_url\": \"https://api.github.com/users/KirillDan/received_events\",\n"
				+ "  \"type\": \"User\",\n" + "  \"site_admin\": false,\n" + "  \"name\": \"KirillDan\",\n"
				+ "  \"company\": null,\n" + "  \"blog\": \"\",\n" + "  \"location\": null,\n" + "  \"email\": null,\n"
				+ "  \"hireable\": null,\n" + "  \"bio\": null,\n" + "  \"twitter_username\": null,\n"
				+ "  \"public_repos\": 15,\n" + "  \"public_gists\": 0,\n" + "  \"followers\": 0,\n"
				+ "  \"following\": 0,\n" + "  \"created_at\": \"2019-07-01T15:07:27Z\",\n"
				+ "  \"updated_at\": \"2021-06-25T13:42:41Z\",\n" + "  \"private_gists\": 0,\n"
				+ "  \"total_private_repos\": 2,\n" + "  \"owned_private_repos\": 2,\n" + "  \"disk_usage\": 51585,\n"
				+ "  \"collaborators\": 0,\n" + "  \"two_factor_authentication\": false,\n" + "  \"plan\": {\n"
				+ "    \"name\": \"free\",\n" + "    \"space\": 976562499,\n" + "    \"collaborators\": 0,\n"
				+ "    \"private_repos\": 10000\n" + "  }\n" + "}\n";
	}
}
