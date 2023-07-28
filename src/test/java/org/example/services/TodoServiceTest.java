package org.example.services;

import org.example.exceptions.ElementNotFoundException;
import org.example.persistence.models.Todo;
import org.example.persistence.repositories.TodoRepository;
import org.example.services.dtos.TodoResponseDto;
import org.example.services.mappers.TodoMapper;
import org.example.services.mappers.TodoMapperImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;
@RunWith(SpringRunner.class)
public class TodoServiceTest {

	@MockBean
	private TodoRepository todoRepository;
	@Autowired
	private TodoService todoService;
	@Autowired
	protected TodoMapper todoMapper;
	@MockBean
	private AuthorizedUserService authorizedUserService;
	@TestConfiguration
	static class TodoServiceContextConfiguration {
		@Bean
		public TodoService todoService() {
			return new TodoService();
		}
		@Bean
		public TodoMapper todoMapper() {return new TodoMapperImpl();}

	}
	@Test
	public void whenFindAll_ReturnTodoList() {
		Todo todo1 = new Todo();
		todo1.setId(1);
		todo1.setName("name1");
		todo1.setContent("content1");
		Todo todo2 = new Todo();
		todo1.setId(2);
		todo1.setName("name2");
		todo1.setContent("content2");
		List<Todo> data = Arrays.asList(todo1, todo2);
		List<TodoResponseDto> todoResponseDtos = todoMapper.toTodoResponseDtos(data);

		given(todoRepository.findAll()).willReturn(data);

		assertThat(todoService.findAll())
				.hasSize(2)
				.contains(todoResponseDtos.get(0), todoResponseDtos.get(1));
	}

	@Test
	public void whenGetById_TodoShouldBeFound() {
		Todo todo1 = new Todo();
		todo1.setId(1);
		todo1.setName("name1");
		todo1.setContent("content1");
		given(todoRepository.findById(anyInt())).willReturn(Optional.ofNullable(todo1));

		TodoResponseDto result = todoService.findById(1);

		assertThat(result.getName()).containsIgnoringCase("name1");
	}


	@Test(expected = ElementNotFoundException.class)
	public void whenInvalidId_TodoShouldNotBeFound() {
		given(todoRepository.findById(anyInt())).willReturn(Optional.empty());

		todoService.findById(1);
	}
}
