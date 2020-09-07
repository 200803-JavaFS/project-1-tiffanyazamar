package com.revature.daos;

import com.revature.models.User;

public interface IUserDAO {

	User findByUsernameAndPassword(String username, String password);

	User insert(User user);

	User findById(int authorId);
}
