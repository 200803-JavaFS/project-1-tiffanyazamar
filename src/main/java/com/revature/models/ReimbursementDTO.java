package com.revature.models;

import java.util.Date;

public class ReimbursementDTO {
	public double amount;
	public Date submittedDate = new Date();
	public String description;
	public int authorId;
	public String type;
}
