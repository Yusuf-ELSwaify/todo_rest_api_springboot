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
	AuthorizedUserService authorizedUserService;
	@Autowired
	TodoMapper todoMapper;

	public List<TodoResponseDto> findAll() {
		AppUser appUser = authorizedUserService.getCurrentUser();
		List<Todo> todos = null;
		if (appUser.isAdmin())
			todos =  todoRepository.findAll();
		else
			todos =  todoRepository.findAllByUser(appUser);
		return todoMapper.toTodoResponseDtos(todos);
		/*return todos.stream()
				.map(TodoResponseDto::new)
				.toList();*/
	}

	public TodoResponseDto find(int id) {
		try {
			AppUser appUser = authorizedUserService.getCurrentUser();
			Todo todo = appUser.isAdmin() ? todoRepository.findById(id).get()
						: todoRepository.findByIdAndUser(id, appUser).get();
			return todoMapper.toTodoResponseDto(todo);
		} catch (NoSuchElementException ex) {
			throw new ElementNotFoundException(String.format("the element with id [%d] not found or you are not have the authority to get it.", id));
		}
	}
	public TodoResponseDto add(TodoRequestDto todoRequestDto) {
		Todo todo = todoRequestDto.toTodo();
		AppUser appUser = authorizedUserService.getCurrentUser();
		int count = todoRepository.countAllByNameAndUser(todo.getName(), appUser);
		if (count != 0)
			throw new AlreadyExistsException(String.format("The todo [%s] is already written before [%d] times.", todo.getName(), count));
		todo.setUser(appUser);
		return todoMapper.toTodoResponseDto(todoRepository.save(todo));
	}
	public void delete(int id) {
		AppUser appUser = authorizedUserService.getCurrentUser();

		Todo todo = todoRepository.findById(id).orElseThrow(
				() -> new ElementNotFoundException(String.format("No element found with id [%d] in our Database", id))
		);
		if (!appUser.isAdmin() && todo.getUser().getId() != appUser.getId())
			throw new NotOwnerException("You are not the element owner to delete it");

		todoRepository.delete(todo);
	}
}
