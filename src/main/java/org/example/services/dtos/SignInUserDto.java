package org.example.services.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInUserDto {
	@NotNull(message = "Username is required")
	String username;
	@NotNull(message = "Password is required")
	String password;
}
