package com.memteam.model;

import java.util.List;
import java.util.Set;

public class MemteamService {

	private MemteamDAO_interface dao;

	public MemteamService() {
		dao = new MemteamDAO();
	}

	public MemteamVO addMemteam(Integer memno, Integer teamno) {
		MemteamVO memteamVO = new MemteamVO();
		memteamVO.setMemno(memno);
		memteamVO.setTeamno(teamno);
		
		dao.insert(memteamVO);
		return memteamVO;
	}

	public void delete(Integer memno, Integer teamno) {
		dao.delete(memno, teamno);
	}

	public List<MemteamVO> getAll() {
		return dao.getAll();
	}
	 //用會員找球隊
	public List<MemteamVO> getTeamBYMemno(Integer memno){
		return dao.getTeamBYMemno(memno);
	}
	
	 //排除球隊重複
	public List<MemteamVO> getAllTeamName(){
		return dao.getAllTeamName();
	}
	
	//排除已加入的球隊
	public List<MemteamVO> getTeamBesideMem(Integer memno){
		return dao.getTeamBesideMem(memno);
	}
	//會員申請加入球隊
	public MemteamVO joinTeam(Integer memno, Integer teamno){
		MemteamVO memteamVO = new MemteamVO();
		memteamVO.setMemno(memno);
		memteamVO.setTeamno(teamno);
		
		dao.insert(memteamVO);
		return memteamVO;
	}
	//退隊
	public void deleteTeam(Integer memno,Integer teamno){
		dao.delete(memno, teamno);
	}
	
}
