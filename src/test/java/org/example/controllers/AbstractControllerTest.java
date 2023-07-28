package org.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.example.persistence.models.AppUser;
import org.example.services.AuthorizedUserService;
import org.example.services.dtos.SignInUserDto;
import org.example.services.mappers.TodoMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.BDDMockito.given;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

	private final String USERNAME_FOR_TEST = "admin";
	private final String PASSWORD_FOR_TEST = "pass";
	private final String AUTH_HEADER = "Authorization";

	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected TodoMapper todoMapper;
	@MockBean
	private AuthorizedUserService authorizedUserService;
	@Before
	public void setup() {
		final AppUser user = new AppUser(USERNAME_FOR_TEST, "{noop}" + PASSWORD_FOR_TEST);
		user.setId(1);
		user.setAuthorities(List.of("ADMIN"));

		given(authorizedUserService.loadUserByUsername(user.getUsername())).willReturn(user);
		given(authorizedUserService.getCurrentUser()).willReturn(user);
	}

	public ResultActions login(String username, String password) throws Exception{
		SignInUserDto signInRequest = new SignInUserDto(username, password);
		return mockMvc.perform(
				post("/api/v1/auth/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(signInRequest))
		);
	}

	public MockHttpServletRequestBuilder doGet(String url) {
		return get(url).header(AUTH_HEADER, getHeader());
	}

	public MockHttpServletRequestBuilder doPost(String url) {
		return post(url).header(AUTH_HEADER, getHeader());
	}



	private String getHeader() {
		try {
			MvcResult result = login(USERNAME_FOR_TEST, PASSWORD_FOR_TEST).andReturn();
			String token = JsonPath.read(result.getResponse().getContentAsString(), "$");
			return String.format("Bearer %s", token);
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}