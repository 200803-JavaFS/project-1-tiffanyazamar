package com.revature.daos;

import java.util.List;
import org.hibernate.Session;

import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.utils.HibernateUtil;

public class UserDAO implements IUserDAO {

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		Session ses = HibernateUtil.getSession();

		List<User> charList = ses.createQuery(
				"FROM User WHERE username = '" + username + "' and password = '" + password + "'", User.class).list();

		if (charList != null && charList.size() > 0) {
			return charList.get(0);
		}

		return null;
	}

	@Override
	public User insert(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(int id) {
		Session ses = HibernateUtil.getSession();

		User h = ses.get(User.class, id);

		return h;
	}

}
