package com.revature.daos;

import com.revature.models.ReimbursementType;

public interface IReimbursementTypeDAO {

	public ReimbursementType findById(int id);

	ReimbursementType findByName(String type);
}
