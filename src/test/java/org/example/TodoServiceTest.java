package org.example;

import org.example.exceptions.ElementNotFoundException;
import org.example.persistence.models.Todo;
import org.example.persistence.repositories.TodoRepository;
import org.example.services.TodoService;
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
	TodoService todoService;

	@TestConfiguration
	static class TodoServiceContextConfiguration {
		@Bean
		public TodoService todoService() {
			return new TodoService();
		}
	}

	@Test
	public void whenFindAll_ReturnTodoList() {
		//Mockup
		Todo todo1 = new Todo();
		todo1.setId(1);
		todo1.setName("name1");
		todo1.setContent("content1");
		Todo todo2 = new Todo();
		todo1.setId(2);
		todo1.setName("name2");
		todo1.setContent("content2");
		List<Todo> data = Arrays.asList(todo1, todo2);

		given(todoRepository.findAll()).willReturn(data);

		assertThat(todoService.findAll())
				.hasSize(2)
				.contains(todo1, todo2);
	}

	@Test
	public void whenGetById_TodoShouldBeFound() {
		Todo todo1 = new Todo();
		todo1.setId(1);
		todo1.setName("name1");
		todo1.setContent("content1");
		given(todoRepository.findById(anyInt())).willReturn(Optional.ofNullable(todo1));

		Todo result = todoService.find(1);

		assertThat(result.getName()).containsIgnoringCase("name1");
	}


	@Test(expected = ElementNotFoundException.class)
	public void whenInvalidId_TodoShouldNotBeFound() {
		given(todoRepository.findById(anyInt())).willReturn(Optional.empty());

		todoService.find(1);
	}
}
