package com.revature.services;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.controllers.LoginController;
import com.revature.daos.IUserDAO;
import com.revature.daos.UserDAO;
import com.revature.models.LoginDTO;
import com.revature.models.User;

public class LoginService {
	private static Logger log = LogManager.getLogger(LoginService.class);

	private IUserDAO userDAO = new UserDAO();

	public User login(LoginDTO l) {
		// Create MessageDigest instance for MD5
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(l.password.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			String generatedPassword = sb.toString();

			User user = userDAO.findByUsernameAndPassword(l.username.toLowerCase(), generatedPassword);

			if(user!=null) {
				log.info(user.getUsername() + " is logged in.");
			}
			return user;
		} catch (NoSuchAlgorithmException e) {
			log.error("Failed to hash password");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
