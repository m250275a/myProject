package com.blacklist.model;

import java.util.List;

public interface BlackListDAO_interface {
	 public void insert(BlackListVO blackListVO);
     public void update(BlackListVO blackListVO);
     public void delete(Integer blackno);
     public BlackListVO findByPrimaryKey(Integer blackno);
     public List<BlackListVO> getAll();
     public List<BlackListVO> getByMemno(Integer memno);
     //萬用複合查詢(傳入參數型態Map)(回傳 List)
//   public List<EmpVO> getAll(Map<String, String[]> map);
}
