package org.example.presistence.models;

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

	@NotNull(message = "Name is required")
	@Size(min = 5, max = 150, message = "length from 5 to 150")
	@Column(nullable = false, length = 150)
	private String name;

	@Size(max = 2500, message = "max length 2500")
	@Column(length = 2500)
	private String content;
	private long timestamp;

	public Todo() {
		timestamp = System.currentTimeMillis();
	}
}
