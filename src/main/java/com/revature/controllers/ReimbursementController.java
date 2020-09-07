package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementDTO;
import com.revature.models.User;
import com.revature.services.ReimbursementService;

public class ReimbursementController {

	ReimbursementService reimbursementService = new ReimbursementService();
	private static ObjectMapper om = new ObjectMapper();

	public void getReimbursements(HttpServletResponse res, int id, String status) throws JsonProcessingException, IOException {
		List<Reimbursement> reimbursements;
		if(status.equals("All")) {
			reimbursements = reimbursementService.findByUserId(id);
		}else {
			reimbursements = reimbursementService.findByUserIdAndStatus(id, status);
		}
		String value = om.writeValueAsString(reimbursements);
		res.getWriter().print(value);
		res.setStatus(200);
//		return reimbursementService.getReimbursementsByUserId(id);
		 

	}

	public void getAllReimbursements(HttpServletResponse res, String status) throws IOException {
		List<Reimbursement> reimbursements = reimbursementService.getAllReimbursements(status);
		String value = om.writeValueAsString(reimbursements);
		res.getWriter().print(value);
		res.setStatus(200);
	}

	public void deniedReimbursement(HttpServletRequest req, HttpServletResponse res) throws IOException {
		BufferedReader reader = req.getReader();

		StringBuilder s = new StringBuilder();

		String line = reader.readLine();

		User user = (User) req.getSession().getAttribute("user");
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}
		reimbursementService.deniedReimbursement(user.getId(), Integer.parseInt(s.toString()));
		res.getWriter().print("Approved Success");
		res.setStatus(201);
	}
	
	public void approveReimbursement(HttpServletRequest req, HttpServletResponse res) throws IOException{
		BufferedReader reader = req.getReader();

		StringBuilder s = new StringBuilder();

		String line = reader.readLine();

		User user = (User) req.getSession().getAttribute("user");
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}
		
		reimbursementService.approvedReimbursement(user.getId(), Integer.parseInt(s.toString()));
		res.getWriter().print("Approved Success");
		res.setStatus(201);
	}
	public void addReimbursement(HttpServletRequest req, HttpServletResponse res) throws IOException {
		BufferedReader reader = req.getReader();

		StringBuilder s = new StringBuilder();

		String line = reader.readLine();

		User user = (User) req.getSession().getAttribute("user");
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = new String(s);

		System.out.println(body);

		ReimbursementDTO a = om.readValue(body, ReimbursementDTO.class);

		a.authorId = user.getId();
		System.out.println(a);

		if (reimbursementService.addReimbursement(a)) {
			res.setStatus(201);
			res.getWriter().println("Avenger was created");
		} else {
			res.setStatus(403);
		}
	}

}
