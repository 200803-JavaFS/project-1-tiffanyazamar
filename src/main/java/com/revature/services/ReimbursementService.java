package com.revature.services;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.daos.ReimbursementDAO;
import com.revature.daos.ReimbursementStatusDAO;
import com.revature.daos.ReimbursementTypeDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementDTO;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;

public class ReimbursementService {
	private static Logger log = LogManager.getLogger(ReimbursementService.class);

	ReimbursementDAO reimbursementDAO = new ReimbursementDAO();

	ReimbursementTypeDAO reimbursementTypeDAO = new ReimbursementTypeDAO();

	ReimbursementStatusDAO reimbursementStatusDAO = new ReimbursementStatusDAO();

	UserDAO userDAO = new UserDAO();

	public List<Reimbursement> getAllReimbursements(String status) {

		if (status.equals("All")) {
			log.info("Select all reimbursement");
			return reimbursementDAO.selectAll();
		} else {
			log.info("Select all reimbursement with status " + status);
			return reimbursementDAO.selectAllByStatus(status);
		}

	}

	public boolean deniedReimbursement(int resolverId, int reimbursementId) {
		Reimbursement reimbursement = findById(reimbursementId);
		ReimbursementStatus status = reimbursementStatusDAO.findByName("Denied");
		User resolver = userDAO.findById(resolverId);
		reimbursement.setStatus(status);
		reimbursement.setResolved(new Date());
		reimbursement.setResolver(resolver);
		reimbursementDAO.update(reimbursement);

		log.info("Reimbursement created by " + reimbursement.getAuthor().getUsername() + " was denied by "
				+ reimbursement.getResolver().getUsername());
		return true;
	}

	public boolean approvedReimbursement(int resolverId, int reimbursementId) {
		Reimbursement reimbursement = findById(reimbursementId);
		ReimbursementStatus status = reimbursementStatusDAO.findByName("Approved");
		User resolver = userDAO.findById(resolverId);
		reimbursement.setStatus(status);
		reimbursement.setResolved(new Date());
		reimbursement.setResolver(resolver);
		reimbursementDAO.update(reimbursement);
		log.info("Reimbursement created by " + reimbursement.getAuthor().getUsername() + " was approved by "
				+ reimbursement.getResolver().getUsername());
		return true;
	}

	public boolean addReimbursement(ReimbursementDTO reimbursementDTO) {
		User author = userDAO.findById(reimbursementDTO.authorId);
		ReimbursementType reimbursementType = reimbursementTypeDAO.findByName(reimbursementDTO.type);
		ReimbursementStatus reimbursementStatus = reimbursementStatusDAO.findByName("Pending");
		Reimbursement reimbursement = new Reimbursement(reimbursementDTO.amount, reimbursementDTO.submittedDate,
				reimbursementDTO.description, author, reimbursementStatus, reimbursementType);
		reimbursementDAO.insert(reimbursement);
		log.info("A new reimbursement was created by " + reimbursement.getAuthor().getUsername());
		return true;
	}

	public List<Reimbursement> findByUserId(int id) {
		log.info("Select all reimbursement for user " + id);
		return reimbursementDAO.selectByUserId(id);
	}

	public Reimbursement findById(int reimbursementId) {
		log.info("Select reimbursement with id " + reimbursementId);
		return reimbursementDAO.selectById(reimbursementId);
	}

	public List<Reimbursement> findByUserIdAndStatus(int id, String status) {
		log.info("Select reimbursement with user id " + id + " and status " + status);
		return reimbursementDAO.selectByUserIdAndStatus(id, status);
	}
}
