package com.revature.daos;

import com.revature.models.UserRole;

public interface IUserRoleDAO {

	public UserRole findByName(String roleName);
}
