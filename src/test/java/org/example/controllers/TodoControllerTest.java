package org.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.persistence.models.AppUser;
import org.example.persistence.models.Todo;
import org.example.services.TodoService;
import org.example.services.dtos.TodoRequestDto;
import org.example.services.dtos.TodoResponseDto;
import org.example.utils.TokenUtil;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;


public class TodoControllerTest extends AbstractControllerTest {
	@MockBean
	private TodoService todoService;

	@Test
	public void whenGetAllTodos_returnOk() throws Exception {
		mockMvc.perform(
				doGet("/api/v1/todos/")
								.contentType(MediaType.APPLICATION_JSON)
				).andExpect(MockMvcResultMatchers.status().isOk());

	}
	@Test
	public void whenGetAllTodos_thenReturnJsonArray() throws Exception {
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

		given(todoService.findAll()).willReturn(todoResponseDtos);

		mockMvc.perform(
						doGet("/api/v1/todos/")
						.contentType(MediaType.APPLICATION_JSON)
				).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].name", equalTo(todo1.getName())));

	}

	@Test
	public void whenPostTodo_thenCreateTodo() throws Exception{
		TodoRequestDto todoRequestDto = new TodoRequestDto("Title of todo", "Title of todo");
		Todo todo1 = todoMapper.fromTodoRequestDto(todoRequestDto);

		TodoResponseDto todoResponseDto = todoMapper.toTodoResponseDto(todo1);
		given(todoService.add(Mockito.any(TodoRequestDto.class), Mockito.any(AppUser.class))).willReturn(todoResponseDto);

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(
						doPost("/api/v1/todos/")
								.contentType(MediaType.APPLICATION_JSON)
								.content(mapper.writeValueAsString(todoRequestDto))
				)
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(jsonPath("$.name", is(todoRequestDto.getName())));

	}
}
