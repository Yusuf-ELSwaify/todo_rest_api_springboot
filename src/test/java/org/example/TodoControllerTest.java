package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.persistence.models.Todo;
import org.example.services.TodoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TodoControllerTest {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	private TodoService todoService;
	@Test
	public void whenGetAllTodos_returnOk() throws Exception {
		mockMvc.perform(
						get("/api/v1/todos")
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

		given(todoService.findAll()).willReturn(data);

		mockMvc.perform(
				get("/api/v1/todos")
						.contentType(MediaType.APPLICATION_JSON)
				).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].name", equalTo(todo1.getName())));

	}

	@Test
	public void whenPostTodo_thenCreateTodo() throws Exception{
		Todo todo1 = new Todo();
		todo1.setName("Title of todo");
		todo1.setContent("Title of todo");

		given(todoService.add(Mockito.any(Todo.class))).willReturn(todo1);

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(
						post("/api/v1/todos/")
								.contentType(MediaType.APPLICATION_JSON)
								.content(mapper.writeValueAsString(todo1))
				)
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(jsonPath("$.name", is(todo1.getName())));

	}
}
