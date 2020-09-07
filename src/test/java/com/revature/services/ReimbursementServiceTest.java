package com.revature.services;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.revature.models.Reimbursement;

public class ReimbursementServiceTest {

	ReimbursementService reimbursementService;
	@Before
	public void setUp() throws Exception {
		reimbursementService = new ReimbursementService();
	}

	@Test
	public void testGetAllReimbursements() {
		List<Reimbursement> re1 = reimbursementService.getAllReimbursements("ALL");
		assertNotNull(re1);
		List<Reimbursement> re2 = reimbursementService.getAllReimbursements("Approved");
		assertNotNull(re2);
		List<Reimbursement> re3 = reimbursementService.getAllReimbursements("Denied");
		assertNotNull(re3);
		List<Reimbursement> re4 = reimbursementService.getAllReimbursements("Pending");
		assertNotNull(re4);

	}
	@Test
	public void testFindByUserId() {
		List<Reimbursement> re1 = reimbursementService.findByUserId(1);
		assertNotNull(re1);
	}
	
	@Test
	public void testFindByUserIdAndStatus() {
		List<Reimbursement> re1 = reimbursementService.findByUserIdAndStatus(1, "ALL");
		assertNotNull(re1);
	}
	@Test
	public void testAddReimbursement() {
		
		reimbursementService.addReimbursement(reimbursementDTO);
		assertNotNull(re1);
	}
}
