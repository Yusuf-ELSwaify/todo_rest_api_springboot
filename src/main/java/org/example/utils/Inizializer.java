package org.example.utils;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.example.security.AppUser;
import org.example.security.AuthorizedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Inizializer implements CommandLineRunner{
	Log logger = LogFactory.getLog(Inizializer.class);
	@Autowired
	AuthorizedUserService userService;
	@Override
	public void run(String... args) throws Exception {
		if (userService.findAll().isEmpty())
		{
			System.out.println("No user found");
			AppUser u1 = new AppUser();
			u1.setUsername("yusuf");
			u1.setPassword("{noop}passs");
			u1.setAuthorities(List.of("USER2"));

			AppUser u2 = new AppUser();
			u2.setUsername("yusuf2");
			u2.setPassword("{noop}pass2");
			u2.setAuthorities(List.of("USER", "USER2"));

			userService.add(u1);
			userService.add(u2);
		}
	}
}
