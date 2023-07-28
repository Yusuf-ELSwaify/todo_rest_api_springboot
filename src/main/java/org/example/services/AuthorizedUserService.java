package org.example.services;

import org.example.persistence.models.AppUser;
import org.example.persistence.repositories.AuthorizedUserRepository;
import org.example.services.dtos.SignInUserDto;
import org.example.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizedUserService implements UserDetailsService {
	@Autowired
	AuthorizedUserRepository repository;
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		//return new BCryptPasswordEncoder(20);
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException(username + " not found.")
		);
	}
	public void add(AppUser appUser) {
		//appUser.setPassword(passwordEncoder().encode(appUser.getPassword()));
		repository.save(appUser);
	}
	public List<AppUser> findAll() {
		return repository.findAll();
	}
	public long usersCount() {
		return repository.count();
	}

	public AppUser getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object user = authentication.getPrincipal();
		if (user instanceof AppUser)
			return (AppUser) user;
		return null;
 	}
}
