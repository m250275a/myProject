package com.memsche.model;

import java.util.List;

public interface MemScheDAO_interface {
	public void insert(MemScheVO memScheVO);
    public void update(MemScheVO memScheVO);
    public void delete(Integer memsche);
    public MemScheVO findByPrimaryKey(Integer memsche);
    public List<MemScheVO> getAll();
    public List<MemScheVO> getByMemno(Integer memno);
    //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//  public List<EmpVO> getAll(Map<String, String[]> map);
}
