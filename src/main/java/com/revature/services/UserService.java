package com.revature.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.revature.daos.UserDAO;
import com.revature.daos.UserRoleDAO;
import com.revature.models.User;
import com.revature.models.UserDTO;
import com.revature.models.UserRole;

public class UserService {

	private UserDAO userDAO = new UserDAO();
	UserRoleDAO userRoleDAO = new UserRoleDAO();

	public boolean addNewUser(UserDTO userDTO) {
		UserRole role = userRoleDAO.findByName(userDTO.role);
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(userDTO.password.getBytes());
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
			User user = new User(generatedPassword, userDTO.username, userDTO.firstname, userDTO.lastname, userDTO.email,
					role);
			userDAO.insert(user);
			return true;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
