package org.example.services.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.persistence.models.Todo;

@Data @NoArgsConstructor @AllArgsConstructor
public class TodoRequestDto {

	@NotNull(message = "Name is required")
	@Size(min = 5, max = 150, message = "length from 5 to 150")
	private String name;
	@Size(max = 2500, message = "max length 2500")
	private String content;
	public Todo toTodo() {
		Todo todo = new Todo();
		todo.setName(name);
		todo.setContent(content);
		return todo;
	}
}
