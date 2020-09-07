package com.revature.daos;

import java.util.List;
import com.revature.models.Reimbursement;

public interface IReimbursementDAO {
  public List<Reimbursement> selectAll();
  
  public List<Reimbursement> selectByUserId(int userId);
  
  public boolean update(Reimbursement reimbursement);
  
  public boolean insert(Reimbursement reimbursement);
  
  public Reimbursement selectById(int id);
  
  public List<Reimbursement> selectByUserIdAndStatus(int id, String status);
  
  public List<Reimbursement> selectAllByStatus(String status);
}
