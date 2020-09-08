package com.revature.daos;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.utils.HibernateUtil;

public class UserDAO implements IUserDAO {
	private static Logger log = LogManager.getLogger(UserDAO.class);

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
	public boolean insert(User user) {
		Session ses = HibernateUtil.getSession();

		Transaction tx = ses.beginTransaction();

		try {
			ses.saveOrUpdate(user);
			tx.commit();
			return true;
		} catch (HibernateException e) {
			log.error("Failed to insert new user. Rolling back now.");
			e.printStackTrace();
			tx.rollback();
			return false;

		}
	}

	@Override
	public User findById(int id) {
		Session ses = HibernateUtil.getSession();

		User h = ses.get(User.class, id);

		return h;
	}

}
