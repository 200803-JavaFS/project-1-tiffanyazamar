package com.revature.services;

import java.util.Date;
import java.util.List;

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

	ReimbursementDAO reimbursementDAO = new ReimbursementDAO();

	ReimbursementTypeDAO reimbursementTypeDAO = new ReimbursementTypeDAO();

	ReimbursementStatusDAO reimbursementStatusDAO = new ReimbursementStatusDAO();

	UserDAO userDAO = new UserDAO();

	public List<Reimbursement> getAllReimbursements(String status) {

		if(status.equals("All")) {
			return reimbursementDAO.selectAll();			
		}else {
			return reimbursementDAO.selectAllByStatus(status);
		}
//		}
//		return null;

	}

	public boolean deniedReimbursement(int resolverId, int reimbursementId) {
		Reimbursement reimbursement = findById(reimbursementId);
		ReimbursementStatus status = reimbursementStatusDAO.findByName("Denied");
		User resolver = userDAO.findById(resolverId);
		reimbursement.setStatus(status);
		reimbursement.setResolved(new Date());
		reimbursement.setResolver(resolver);
		reimbursementDAO.update(reimbursement);
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
		return true;
	}

	public boolean addReimbursement(ReimbursementDTO reimbursementDTO) {
		User author = userDAO.findById(reimbursementDTO.authorId);
		ReimbursementType reimbursementType = reimbursementTypeDAO.findByName(reimbursementDTO.type);
		ReimbursementStatus reimbursementStatus = reimbursementStatusDAO.findByName("Pending");
		Reimbursement reimbursement = new Reimbursement(reimbursementDTO.amount, reimbursementDTO.submittedDate,
				reimbursementDTO.description, author, reimbursementStatus, reimbursementType);
		reimbursementDAO.insert(reimbursement);
		return true;
	}

	public List<Reimbursement> findByUserId(int id) {
		return reimbursementDAO.selectByUserId(id);
	}

	public Reimbursement findById(int reimbursementId) {
		return reimbursementDAO.selectById(reimbursementId);
	}

	public List<Reimbursement> findByUserIdAndStatus(int id, String status) {
		return reimbursementDAO.selectByUserIdAndStatus(id, status);
	}
}
