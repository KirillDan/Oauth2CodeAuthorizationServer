package com.oauth2.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class CodeGenerator {

	public String generate() {
		return UUID.randomUUID().toString();
	}
}
