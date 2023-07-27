package org.example.security;

import org.example.persistence.repositories.AuthorizedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
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
		AppUser appUser = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + "not found."));
		return appUser;
	}
	public void add(AppUser appUser) {
		//appUser.setPassword(passwordEncoder().encode(appUser.getPassword()));
		repository.save(appUser);
	}
	public List<AppUser> findAll() {
		return repository.findAll();
	}
}
