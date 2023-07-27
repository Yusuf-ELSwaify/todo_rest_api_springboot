package org.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig{
	@Autowired
	private AuthenticationFilter authenticationFilter;
/*	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		//return new BCryptPasswordEncoder(20);
	}*/
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails userDetails = User.builder()
//				.username("yusuf")
//				.password("{noop}pass")
//				.roles("USER")
//				.build();
//		UserDetails userDetails2 = User.builder()
//				.username("yusuf2")
//				.password("{noop}pass2")
//				.roles("USER2")
//				.build();
//		return new InMemoryUserDetailsManager(userDetails, userDetails2);
//	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.authorizeHttpRequests(auth -> {
					auth.requestMatchers( HttpMethod.DELETE, "/api/v1/todos/*")
							.hasAuthority("USER2");
					auth.requestMatchers( HttpMethod.POST, "/api/v1/todos/")
							.hasAuthority("USER");
					auth.requestMatchers( HttpMethod.GET, "/api/v1/todos/1")
							.hasAuthority("USER");
					auth.requestMatchers( HttpMethod.POST, "/api/v1/**")
							.permitAll();
					auth.requestMatchers( HttpMethod.GET, "/api/v1/todos/*")
							.permitAll();

/*					auth.requestMatchers(  "/**")
							.permitAll();*/
				})
				.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.httpBasic(Customizer.withDefaults())
				.csrf(AbstractHttpConfigurer::disable)
				.cors(cors -> cors.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
