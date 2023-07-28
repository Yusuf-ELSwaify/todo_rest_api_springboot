package org.example;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.example.persistence.models.AppUser;
import org.example.services.AuthorizedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Initializer implements CommandLineRunner{
	Log logger = LogFactory.getLog(Initializer.class);
	@Autowired
	AuthorizedUserService userService;
	@Override
	public void run(String... args) throws Exception {
		if (userService.usersCount() == 0)
		{
			System.out.println("No user found");
			AppUser u1 = new AppUser();
			u1.setUsername("admin");
			u1.setPassword("{noop}pass");
			u1.setAuthorities(List.of("ADMIN"));

			AppUser u2 = new AppUser();
			u2.setUsername("user");
			u2.setPassword("{noop}pass");
			u2.setAuthorities(List.of("USER1", "USER2"));

			userService.add(u1);
			userService.add(u2);
		}
	}

}
