package com.memsche.model;

import java.sql.Date;
import java.util.List;

public class MemScheService {
	private MemScheDAO_interface dao;
	
	public MemScheService(){
		dao = new MemScheDAO();
	}
	
	public MemScheVO addMemSche(Integer memno,Integer free,Date mdate ){
		
		MemScheVO memscheVO = new MemScheVO();
		memscheVO.setMemno(memno);
		memscheVO.setFree(free);
		memscheVO.setMdate(mdate);
		dao.insert(memscheVO);
		
		return memscheVO;
	}
	
	public MemScheVO updateMemSche(Integer memsche,Integer memno,Integer free,Date mdate ){
		
		MemScheVO memscheVO = new MemScheVO();
		memscheVO.setMemsche(memsche);
		memscheVO.setMemno(memno);
		memscheVO.setFree(free);
		memscheVO.setMdate(mdate);
		dao.update(memscheVO);
		
		return memscheVO;
	}
	
	public void deleteMemSche(Integer memsche) {
		dao.delete(memsche);
	}

	public MemScheVO getOneMemSche(Integer memsche) {
		return dao.findByPrimaryKey(memsche);
	}

	public List<MemScheVO> getAll() {
		return dao.getAll();
	}
	public List<MemScheVO> getByMemno(Integer memno) {
		return dao.getByMemno(memno);
	}
	
}
