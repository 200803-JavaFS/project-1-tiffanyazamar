package com.revature.daos;

import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.services.ReimbursementService;
import com.revature.utils.HibernateUtil;

public class ReimbursementDAO implements IReimbursementDAO {
	private static Logger log = LogManager.getLogger(ReimbursementDAO.class);

	@Override
	public List<Reimbursement> selectAll() {
		Session ses = HibernateUtil.getSession();

		List<Reimbursement> list = ses.createQuery("FROM Reimbursement re ORDER BY re.id DESC").list();

		return list;
	}

	@Override
	public List<Reimbursement> selectByUserId(int userId) {
		Session ses = HibernateUtil.getSession();

		List<Reimbursement> charList = ses
				.createQuery("FROM Reimbursement re WHERE re.author.id =" + userId + " ORDER BY re.id DESC",
						Reimbursement.class)
				.list();

		return charList;
	}

	@Override
	public boolean update(Reimbursement reimbursement) {
		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();

		try {
			ses.saveOrUpdate(reimbursement);
			tx.commit();
			
			return true;
		} catch (HibernateException e) {
			log.error("Failed to update reimbursement. Rolling back now.");
			e.printStackTrace();
			tx.rollback();
			return false;

		}
	}

	@Override
	public boolean insert(Reimbursement reimbursement) {
		Session ses = HibernateUtil.getSession();

		Transaction tx = ses.beginTransaction();

		try {
			ses.saveOrUpdate(reimbursement);
			tx.commit();
			return true;
		} catch (HibernateException e) {
			log.error("Failed to insert new reimbursement. Rolling back now.");
			e.printStackTrace();
			tx.rollback();
			return false;

		}
	}

	@Override
	public Reimbursement selectById(int id) {
		Session ses = HibernateUtil.getSession();

		Reimbursement h = ses.get(Reimbursement.class, id);

		return h;
	}

	@Override
	public List<Reimbursement> selectByUserIdAndStatus(int userId, String status) {
		Session ses = HibernateUtil.getSession();

		List<Reimbursement> charList = ses.createQuery("FROM Reimbursement re WHERE re.author.id =" + userId
				+ " and re.status.statusName = '" + status + "' ORDER BY re.id DESC", Reimbursement.class).list();

		return charList;
	}

	public List<Reimbursement> selectAllByStatus(String status) {
		Session ses = HibernateUtil.getSession();

		List<Reimbursement> charList = ses
				.createQuery("FROM Reimbursement re WHERE re.status.statusName = '" + status + "' ORDER BY re.id DESC",
						Reimbursement.class)
				.list();

		return charList;
	}

}
