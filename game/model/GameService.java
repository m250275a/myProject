package com.game.model;

import java.util.List;
import java.util.Set;

import com.team.model.TeamVO;

public class GameService {
	private GameDAO_interface dao;

	public GameService() {
		dao = new GameJNDIDAO();
	}

	public GameVO addGame(java.sql.Date gamedate, Integer memno, Integer teamno, Integer courtno,
			Integer gametype) {
		
		GameVO gameVO = new GameVO();

		gameVO.setGamedate(gamedate);
		gameVO.setMemno(memno);
		gameVO.setTeamno(teamno);
		gameVO.setCourtno(courtno);
		gameVO.setGametype(gametype);
		dao.insert(gameVO);

		return gameVO;
	}

	public GameVO updateGame(Integer gameno, Integer teamno2) 
	{
		GameVO gameVO = new GameVO();

		gameVO.setGameno(gameno);
		//gameVO.setGamedate(gamedate);
		//gameVO.setMemno(memno);
		//gameVO.setTeamno(teamno);
		gameVO.setTeamno2(teamno2);
//		gameVO.setCourtno(courtno);
		//gameVO.setGametype(gametype);
//		gameVO.setGameresult(gameresult);
		dao.update(gameVO);

		return gameVO;
	}

	public void deleteGame(Integer gameno) {
		dao.delete(gameno);
	}

	public GameVO getOneGame(Integer gameno) {
		return dao.findByPrimaryKey(gameno);
	}
	
	public Set<GameVO> getGamesByMemno(Integer memno) {
		return dao.getGamesByMemno(memno);
	}
	
	public List<GameVO> getGamesByGametype(Integer gametype) {
		return dao.getGamesByGametype(gametype);
	}
	
	public List<GameVO> getGamesByTeam(Integer teamno) {
		return dao.getGamesByTeam(teamno);
	}
	
	public List<GameVO> getGamesByCourtno(Integer courtno) {
		return dao.getGamesByCourtno(courtno);
	}
	
	public List<GameVO> getGamesByCourtnoToChange(Integer courtno) {
		return dao.getGamesByCourtnoToChange(courtno);
	}

	public List<GameVO> getAll() {
		return dao.getAll();
	}
	
	public List<GameVO> getAllGameList() {
		return dao.getAllGameList();
	}
	//修改比賽結果欄位
		public GameVO updateGameresult(Integer gameno, String gameresult) 
		{
			GameVO gameVO = new GameVO();

			gameVO.setGameno(gameno);
			gameVO.setGameresult(gameresult);
			dao.updateGameresult(gameVO);

			return gameVO;
		}
}
