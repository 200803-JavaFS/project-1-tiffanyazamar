package com.revature.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.revature.models.ReimbursementType;
import com.revature.utils.HibernateUtil;

public class ReimbursementTypeDAO implements IReimbursementTypeDAO {

	@Override
	public ReimbursementType findById(int id) {
		Session ses = HibernateUtil.getSession();

		ReimbursementType h = ses.get(ReimbursementType.class, id);

		return h;
	}

	@Override
	public ReimbursementType findByName(String type) {
		Session ses = HibernateUtil.getSession();

		String hql = "FROM ReimbursementType E WHERE E.typeName = '" + type + "'" ;
		Query<ReimbursementType> query = ses.createQuery(hql);
		List<ReimbursementType> results = query.list();
		return results.get(0);
	}

}
