package org.example.controllers;

import jakarta.validation.Valid;
import org.example.services.dtos.TodoResponseDto;
import org.example.services.dtos.TodoRequestDto;
import org.example.persistence.models.Todo;
import org.example.services.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {
	final TodoService todoService;

	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@GetMapping({"", "/"})
	public ResponseEntity<List<TodoResponseDto>> getAll() {
		return new ResponseEntity<>(todoService.findAll(),
				HttpStatus.OK);
	}



	@GetMapping({"/{id}"})
	public ResponseEntity<TodoResponseDto> get(@PathVariable int id) {
		TodoResponseDto todo = todoService.find(id);
		return new ResponseEntity<>(todo, HttpStatus.OK);
	}
	@PostMapping({"/"})
	public ResponseEntity<TodoResponseDto> add(@Valid @RequestBody TodoRequestDto todoRequestDto) {
		TodoResponseDto todoResponseDto = todoService.add(todoRequestDto);
		if (todoResponseDto != null)
			return new ResponseEntity<>(todoResponseDto, HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
		todoService.delete(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
