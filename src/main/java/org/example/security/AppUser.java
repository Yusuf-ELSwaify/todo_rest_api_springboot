package org.example.security;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.*;

@Entity(name = "authorized_users")
public class AppUser implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String username;
	@JsonIgnore
	String password;
	@Temporal(TemporalType.DATE)
	Date creationDate;
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> authorities = new ArrayList<>();
	public AppUser(String username, String password) {
		this.username = username;
		this.password = password;
		this.creationDate = new Date();
	}
	public AppUser() {
		this.creationDate = new Date();
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities
				.stream()
				.map(SimpleGrantedAuthority::new)
				.toList();
	}

	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
