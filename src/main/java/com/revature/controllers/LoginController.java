package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.LoginDTO;
import com.revature.models.User;
import com.revature.services.LoginService;

public class LoginController {
	
	private static LoginService loginService = new LoginService();
	private static ObjectMapper objectMapper = new ObjectMapper();

	
	public void login(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// this is how a login should generally be handled. Sending credentials in the
		// body of a POST request.
		BufferedReader reader = req.getReader();

		StringBuilder sb = new StringBuilder();

		String line = reader.readLine();

		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}

		String body = new String(sb);

		LoginDTO l = objectMapper.readValue(body, LoginDTO.class);

			User user = loginService.login(l);
		if (user!=null) {
			HttpSession ses = req.getSession();
			
//			HttpSession ses = req.getSession(true);
			ses.setAttribute("user", user);
			ses.setAttribute("loggedin", true);
			res.setStatus(200);
			res.getWriter().print(objectMapper.writeValueAsString(user));
		} else {
			HttpSession ses = req.getSession(false);
			if (ses != null) {
				ses.invalidate();
			}
			res.setStatus(401);
			res.getWriter().println("Login failed");
		}

	}

	public void logout(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession ses = req.getSession(false);

		if (ses != null) {
			User l = (User) ses.getAttribute("user");
			ses.invalidate();
			res.setStatus(200);
			res.getWriter().println(l.getUsername() + " has logged out successfully");
		} else {
			res.setStatus(400);
			res.getWriter().println("You must be logged in to logout!");
		}
	}

}
