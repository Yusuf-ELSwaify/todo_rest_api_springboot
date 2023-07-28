package org.example.persistence.repositories;

import org.example.persistence.models.AppUser;
import org.example.persistence.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
	int countAllByNameAndUser(String name, AppUser user);
	List<Todo> findAllByUser(AppUser user);
	Optional<Todo> findByIdAndUser(int id, AppUser user);
}
