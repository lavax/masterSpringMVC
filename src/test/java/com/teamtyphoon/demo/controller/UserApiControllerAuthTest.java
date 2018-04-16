package com.teamtyphoon.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.teamtyphoon.demo.MasterSpringMvcApplication;
import com.teamtyphoon.demo.user.User;
import com.teamtyphoon.demo.user.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MasterSpringMvcApplication.class)
@WebAppConfiguration
public class UserApiControllerAuthTest {
	@Autowired
	private FilterChainProxy springSecurityFilter;
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private UserRepository userRepository;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilter).build();
		userRepository.reset(new User("bob@spring.io"));
	}

	@Test
	public void unauthenticated_cannot_list_users() throws Exception {
		this.mockMvc.perform(get("/api/users").accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
	}

	@Test
	public void admin_can_list_users() throws Exception {
		this.mockMvc.perform(get("/api/users").accept(MediaType.APPLICATION_JSON).header("Authorization",
				basicAuth("admin", "admin"))).andExpect(status().isOk());
	}

	private String basicAuth(String login, String password) {
		byte[] auth = (login + ":" + password).getBytes();
		return "Basic " + Base64.getEncoder().encodeToString(auth);
	}
}