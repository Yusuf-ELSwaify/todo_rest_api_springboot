package org.example;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {
	final TodoService service;

	public TodoController(TodoService service) {
		this.service = service;
	}

	@RequestMapping({"", "/"})
	public ResponseEntity<List<Todo>> getAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	@RequestMapping({"/{id}"})
	public ResponseEntity<Todo> get(@PathVariable int id) {
		Todo todo = service.find(id);
		if (todo == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(todo, HttpStatus.OK);
	}
	@PostMapping({"/"})
	public ResponseEntity<Todo> add(@Valid @RequestBody Todo todo) {
		if (service.add(todo) != null)
			return new ResponseEntity<>(todo, HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
		boolean deleted = service.delete(id);
		if (!deleted)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
