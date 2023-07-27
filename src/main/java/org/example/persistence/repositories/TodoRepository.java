package org.example.presistence.repositories;

import org.example.presistence.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
	int countAllByName(String name);
}
