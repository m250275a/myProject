package com.challteam.model;

import java.util.List;



public class ChallteamService {
	private ChallteamDAO_interface dao;

	public ChallteamService() {
		dao = new ChallteamDAO();
	}

	public ChallteamVO addChallteam(Integer teamno, Integer challmode) {
		ChallteamVO challteamVO = new ChallteamVO();
		challteamVO.setTeamno(teamno);
		challteamVO.setChallmode(challmode);
		dao.addChallteam(challteamVO);
		return challteamVO;
	}

	public void delete(Integer teamno, Integer challmode) {
		dao.delete(teamno,challmode);
	};

	public List<ChallteamVO> getAll(Integer teamno) {
		return dao.getAll(teamno);
	};
}
