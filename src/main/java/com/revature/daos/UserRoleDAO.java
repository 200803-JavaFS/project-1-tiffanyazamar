package com.revature.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.revature.models.UserRole;
import com.revature.utils.HibernateUtil;

public class UserRoleDAO implements IUserRoleDAO {

	@Override
	public UserRole findByName(String roleName) {
		Session ses = HibernateUtil.getSession();

		String hql = "FROM UserRole E WHERE E.roleName = '" + roleName + "'" ;
		Query<UserRole> query = ses.createQuery(hql);
		List<UserRole> results = query.list();
		return results.get(0);
	}

}
