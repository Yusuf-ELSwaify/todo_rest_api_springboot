package org.example.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	@RequestMapping("/")
	public String greeting() {
		return "Hello Y";
	}
	@GetMapping("/{name}")
	public String greeting(@PathVariable String name) {
		return "Hello " + name;
	}
}
