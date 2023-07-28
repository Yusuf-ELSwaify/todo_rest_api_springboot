package org.example.controllers;

import jakarta.validation.Valid;
import org.example.persistence.models.AppUser;
import org.example.persistence.models.Todo;
import org.example.services.AuthorizedUserService;
import org.example.services.dtos.TodoResponseDto;
import org.example.services.dtos.TodoRequestDto;
import org.example.services.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {
	final TodoService todoService;
	final AuthorizedUserService authorizedUserService;

	public TodoController(TodoService todoService, AuthorizedUserService authorizedUserService) {
		this.todoService = todoService;
		this.authorizedUserService = authorizedUserService;
	}

	@GetMapping({"", "/"})
	public ResponseEntity<List<TodoResponseDto>> getAll() {
		AppUser appUser = authorizedUserService.getCurrentUser();
		List<TodoResponseDto> todos = null;
		if (appUser.isAdmin())
			todos =  todoService.findAll();
		else
			todos =  todoService.findAllByUser(appUser);
		return new ResponseEntity<>(todos,
				HttpStatus.OK);
	}

	@GetMapping({"/{id}"})
	public ResponseEntity<TodoResponseDto> get(@PathVariable int id) {
		AppUser appUser = authorizedUserService.getCurrentUser();
		TodoResponseDto todoResponseDto = appUser.isAdmin() ? todoService.findById(id)
				: todoService.findByIdAndUser(id, appUser);
		return new ResponseEntity<>(todoResponseDto, HttpStatus.OK);
	}
	@PostMapping({"/"})
	public ResponseEntity<TodoResponseDto> add(@Valid @RequestBody TodoRequestDto todoRequestDto) {
		AppUser appUser = authorizedUserService.getCurrentUser();
		TodoResponseDto todoResponseDto = todoService.add(todoRequestDto, appUser);
		if (todoResponseDto != null)
			return new ResponseEntity<>(todoResponseDto, HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
		AppUser appUser = authorizedUserService.getCurrentUser();
		todoService.delete(id, appUser);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
