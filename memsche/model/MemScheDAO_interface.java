package com.memsche.model;

import java.util.List;

public interface MemScheDAO_interface {
	public void insert(MemScheVO memScheVO);
    public void update(MemScheVO memScheVO);
    public void delete(Integer memsche);
    public MemScheVO findByPrimaryKey(Integer memsche);
    public List<MemScheVO> getAll();
    public List<MemScheVO> getByMemno(Integer memno);
    //萬用複合查詢(傳入參數型態Map)(回傳 List)
//  public List<EmpVO> getAll(Map<String, String[]> map);
}
