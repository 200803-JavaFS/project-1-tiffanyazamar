package com.revature.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.utils.HibernateUtil;

public class ReimbursementStatusDAO implements IReimbursementStatusDAO {

	@Override
	public ReimbursementStatus findById(int id) {
		Session ses = HibernateUtil.getSession();

		ReimbursementStatus h = ses.get(ReimbursementStatus.class, id);

		return h;
	}

	@Override
	public ReimbursementStatus findByName(String name) {
		Session ses = HibernateUtil.getSession();

		String hql = "FROM ReimbursementStatus E WHERE E.statusName = '" + name + "'" ;
		Query<ReimbursementStatus> query = ses.createQuery(hql);
		List<ReimbursementStatus> results = query.list();
		return results.get(0);
	}

}
