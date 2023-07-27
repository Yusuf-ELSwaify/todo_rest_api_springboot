package org.example.persistence.repositories;

import org.example.security.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorizedUserRepository extends JpaRepository<AppUser, Integer> {
	Optional<AppUser> findByUsername(String username);
}
