package org.example.services.mappers;

import org.example.persistence.models.Todo;
import org.example.services.dtos.TodoRequestDto;
import org.example.services.dtos.TodoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {
	TodoResponseDto toTodoResponseDto(Todo todo);
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "timestamp", ignore = true)
	@Mapping(target = "id", ignore = true)
	Todo fromTodoRequestDto(TodoRequestDto todoRequestDto);

	List<TodoResponseDto> toTodoResponseDtos(List<Todo> todos);
}
