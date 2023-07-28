package org.example.controllers;

import jakarta.validation.Valid;
import org.example.services.dtos.SignInUserDto;
import org.example.persistence.models.AppUser;
import org.example.services.AuthorizedUserService;
import org.example.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	TokenUtil tokenUtil;
	@Autowired
	AuthorizedUserService service;
	@Autowired
	AuthenticationManager authenticationManager;
	@PostMapping({"", "/"})
	public ResponseEntity<String> authenticate(@Valid @RequestBody SignInUserDto user) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
		);
		return ResponseEntity.ok(service.authenticateAndGetToken(user, authentication));
	}
}
