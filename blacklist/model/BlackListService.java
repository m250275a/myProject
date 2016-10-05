package com.blacklist.model;

import java.util.List;

public class BlackListService {
	private BlackListDAO_interface dao;
	
	public BlackListService(){
		dao = new BlackListDAO();
	}
	public BlackListVO addBlack(Integer memno,Integer memedno){
		
		BlackListVO blackListVO = new BlackListVO();
		blackListVO.setMemno(memno);
		blackListVO.setMemedno(memedno);
		dao.insert(blackListVO);
		
		return blackListVO;
	}
	
	public BlackListVO updateBlack(Integer blackno,Integer memno,Integer memedno){
		
		BlackListVO blackListVO = new BlackListVO();
		blackListVO.setBlackno(blackno);
		blackListVO.setMemno(memno);
		blackListVO.setMemedno(memedno);
		dao.update(blackListVO);
		
		return blackListVO;
	}
	
	public void deleteBlack(Integer blackno){
		dao.delete(blackno);
		
	}
	public BlackListVO getOneBlack(Integer blackno){
		return dao.findByPrimaryKey(blackno);

	}
	
	public List<BlackListVO> getAll() {
		return dao.getAll();
	}
	public List<BlackListVO> getByMemno(Integer memno) {
		return dao.getByMemno(memno);
	}
	
}
