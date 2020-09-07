package com.revature.services;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.revature.models.LoginDTO;
import com.revature.models.User;

public class LoginServiceTest {
	
	LoginService loginService;

	@Before
	public void setUp() throws Exception {
		loginService = new LoginService();
	}

	@Test
	public void testLogin() {
		LoginDTO l = new LoginDTO();
		l.password = "welcome";
		l.username = "eunice";
		User user = loginService.login(l);
		
		assertNotNull(user);
		
		l.password = "wrong";
		User user2 = loginService.login(l);
		assertNull(user2);
	}

}
