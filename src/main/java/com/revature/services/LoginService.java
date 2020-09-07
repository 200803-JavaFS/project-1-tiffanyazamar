package com.revature.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.revature.daos.IUserDAO;
import com.revature.daos.UserDAO;
import com.revature.models.LoginDTO;
import com.revature.models.User;

public class LoginService {

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
			return user;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
