package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
	@Autowired
	TodoRepository todoRepository;

	public List<Todo> findAll() {
		return todoRepository.findAll();
	}

	public Todo find(int id) {
		return todoRepository.findById(id).orElse(null);
	}
	public Todo add(Todo todo) {
		return todoRepository.save(todo);
	}
	public boolean delete(int id) {
		todoRepository.deleteById(id);
		return true;
	}
}
