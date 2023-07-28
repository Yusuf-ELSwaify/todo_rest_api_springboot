package org.example.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class TodoResponseDto {
	private int id;
	private String name;
	private String content;
	private long timestamp;
//	public TodoReponseDto(Todo todo) {
//		setId(todo.getId());
//		setName(todo.getName());
//		setContent(todo.getContent());
//		setTimestamp(todo.getTimestamp());
//	}
//	public TodoReponseDto fromTodo(Todo todo) {
//		setId(todo.getId());
//		setName(todo.getName());
//		setContent(todo.getContent());
//		setTimestamp(todo.getTimestamp());
//		return this;
//	}
}
