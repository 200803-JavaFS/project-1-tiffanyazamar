package com.revature.daos;

import com.revature.models.ReimbursementStatus;

public interface IReimbursementStatusDAO {

	public ReimbursementStatus findById(int id);

	public ReimbursementStatus findByName(String name);

}
