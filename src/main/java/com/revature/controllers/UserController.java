package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.ReimbursementDTO;
import com.revature.models.User;
import com.revature.models.UserDTO;
import com.revature.services.UserService;

public class UserController {

	private UserService userService = new UserService();
	private static ObjectMapper om = new ObjectMapper();

	public void newUser(HttpServletRequest req, HttpServletResponse res) throws IOException {
		BufferedReader reader = req.getReader();

		StringBuilder s = new StringBuilder();

		String line = reader.readLine();

		User user = (User) req.getSession().getAttribute("user");
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = new String(s);

		System.out.println(body);

		UserDTO a = om.readValue(body, UserDTO.class);

		if (userService.addNewUser(a)) {
			res.setStatus(201);
			res.getWriter().println("User was created");
		} else {
			res.setStatus(403);
		}
	}

}
