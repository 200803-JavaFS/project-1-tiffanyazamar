package com.revature.services;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.revature.models.UserDTO;

public class UserServiceTest {

	private UserService userService;
	@Before
	public void setUp() throws Exception {
		userService = new UserService();
	}

	@Test
	public void testInsert() {
		UserDTO test = new UserDTO();
		test.email = "test@revature.net";
		test.username = "test";
		test.password = "test";
		test.firstname = "test";
		test.lastname = "test";
		test.role = "Employee";
		boolean result = userService.addNewUser(test);
		assertTrue(result);
	}

}
