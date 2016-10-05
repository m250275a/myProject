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
	 //�η|����y��
	public List<MemteamVO> getTeamBYMemno(Integer memno){
		return dao.getTeamBYMemno(memno);
	}
	
	 //�ư��y������
	public List<MemteamVO> getAllTeamName(){
		return dao.getAllTeamName();
	}
	
	//�ư��w�[�J���y��
	public List<MemteamVO> getTeamBesideMem(Integer memno){
		return dao.getTeamBesideMem(memno);
	}
	//�|���ӽХ[�J�y��
	public MemteamVO joinTeam(Integer memno, Integer teamno){
		MemteamVO memteamVO = new MemteamVO();
		memteamVO.setMemno(memno);
		memteamVO.setTeamno(teamno);
		
		dao.insert(memteamVO);
		return memteamVO;
	}
	//�h��
	public void deleteTeam(Integer memno,Integer teamno){
		dao.delete(memno, teamno);
	}
	
}
