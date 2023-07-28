package org.example.services;

import org.example.services.dtos.TodoResponseDto;
import org.example.services.dtos.TodoRequestDto;
import org.example.exceptions.AlreadyExistsException;
import org.example.exceptions.ElementNotFoundException;
import org.example.exceptions.NotOwnerException;
import org.example.persistence.models.AppUser;
import org.example.persistence.models.Todo;
import org.example.persistence.repositories.TodoRepository;
import org.example.services.mappers.TodoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TodoService {
	@Autowired
	TodoRepository todoRepository;
	@Autowired
	TodoMapper todoMapper;

	public List<TodoResponseDto> findAll() {
		return todoMapper.toTodoResponseDtos(todoRepository.findAll());
	}
	public List<TodoResponseDto> findAllByUser(AppUser appUser) {
		return todoMapper.toTodoResponseDtos(todoRepository.findAllByUser(appUser));
	}

	public TodoResponseDto findById(int id) {
		try {
			Todo todo = todoRepository.findById(id).get();
			return todoMapper.toTodoResponseDto(todo);
		} catch (NoSuchElementException ex) {
			throw new ElementNotFoundException(String.format("the element with id [%d] not found.", id));
		}
	}
	public TodoResponseDto findByIdAndUser(int id, AppUser appUser) {
		try {
			Todo todo = todoRepository.findByIdAndUser(id, appUser).get();
			return todoMapper.toTodoResponseDto(todo);
		} catch (NoSuchElementException ex) {
			throw new ElementNotFoundException(String.format("the element with id [%d] not found or you are not have the authority to get it.", id));
		}
	}
	public TodoResponseDto add(TodoRequestDto todoRequestDto, AppUser appUser) {
		Todo todo = todoRequestDto.toTodo();
		int count = todoRepository.countAllByNameAndUser(todo.getName(), appUser);
		if (count != 0)
			throw new AlreadyExistsException(String.format("The todo [%s] is already written before [%d] times.", todo.getName(), count));
		todo.setUser(appUser);
		return todoMapper.toTodoResponseDto(todoRepository.save(todo));
	}
	public void delete(int id, AppUser appUser) {
		Todo todo = todoRepository.findById(id).orElseThrow(
				() -> new ElementNotFoundException(String.format("No element found with id [%d] in our Database", id))
		);
		if (!appUser.isAdmin() && todo.getUser().getId() != appUser.getId())
			throw new NotOwnerException("You are not the element owner to delete it");

		todoRepository.delete(todo);
	}
}
