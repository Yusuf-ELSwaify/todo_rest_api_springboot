package org.example.persistence.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.io.Serializable;

@Data
@Entity
@Table(name = "todo")
@EnableAutoConfiguration
public class Todo implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 150)
	private String name;

	@Column(length = 2500)
	private String content;
	private long timestamp;
	@ManyToOne(optional = false)
	AppUser user;

	public Todo() {
		timestamp = System.currentTimeMillis();
	}
}
