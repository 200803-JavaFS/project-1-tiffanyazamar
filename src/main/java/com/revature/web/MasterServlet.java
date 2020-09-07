package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.controllers.LoginController;
import com.revature.controllers.ReimbursementController;
import com.revature.models.User;

public class MasterServlet extends HttpServlet {

	private static ReimbursementController reimbursementController = new ReimbursementController();
	private static LoginController loginController = new LoginController();

	public MasterServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");
		// By default tomcat will send back a successful status code if it finds a
		// servlet method.
		// Because all requests will hit this method, we are defaulting to not found and
		// will override for success requests.
		res.setStatus(404);

		final String URI = req.getRequestURI().replace("/project0/", "");

		String[] portions = URI.split("/");

		try {
			switch (portions[0]) {
			case "reimbursement":

				HttpSession ses = req.getSession();

				if (ses != null && (boolean) ses.getAttribute("loggedin")) {
					User user = (User) req.getSession().getAttribute("user");

					if (req.getMethod().equals("GET")) {
						if (user.getUserRole().getRoleName().equals("Employee")) {
							reimbursementController.getReimbursements(res, user.getId(), portions[2]);
						} else if (user.getUserRole().getRoleName().equals("Finance Manager")) {
							reimbursementController.getAllReimbursements(res, portions[2]);
						}
					} else if (req.getMethod().equals("POST")) {
						if (portions.length > 2) {
							updateReimbursement(portions, req, res);
						} else {
							reimbursementController.addReimbursement(req, res);
						}

					}
				} else {
					res.setStatus(403);
					res.getWriter().println("You must be logged in to do that!");
				}
				break;
			case "login":
				loginController.login(req, res);
				break;
			case "logout":
				loginController.logout(req, res);
				break;
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			res.getWriter().print("The id you provided is not an integer");
			res.setStatus(400);
		}

	}

	private void updateReimbursement(String[] portions, HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		switch (portions[2]) {
		case "approve":
			reimbursementController.approveReimbursement(req, res);
			break;
		case "denied":
			reimbursementController.deniedReimbursement(req, res);
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
