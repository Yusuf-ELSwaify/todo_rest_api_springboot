package org.example.services;

import org.example.exceptions.AlreadyExistsException;
import org.example.exceptions.ElementNotFoundException;
import org.example.persistence.models.Todo;
import org.example.persistence.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TodoService {
	@Autowired
	TodoRepository todoRepository;

	public List<Todo> findAll() {
		return todoRepository.findAll();
	}

	public Todo find(int id) {
		try {
			return todoRepository.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new ElementNotFoundException(String.format("No element found with id [%d] in our Database", id));
		}
	}
	public Todo add(Todo todo) {
		int count = todoRepository.countAllByName(todo.getName());
		if (count != 0)
			throw new AlreadyExistsException(String.format("The name [%s] is already exist in DB [%d] times.", todo.getName(), count));
		return todoRepository.save(todo);
	}
	public boolean delete(int id) {
		todoRepository.deleteById(id);
		return true;
	}
}
