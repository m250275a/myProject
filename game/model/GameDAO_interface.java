package com.game.model;

import java.util.*;

import com.team.model.TeamVO;

public interface GameDAO_interface {
	public void insert(GameVO gameVO);
    public void update(GameVO gameVO);
    public void delete(Integer gameno);
    public GameVO findByPrimaryKey(Integer gameno);
    public Set<GameVO> getGamesByMemno(Integer memno);
    public List<GameVO> getAll();
	public List<GameVO> getGamesByGametype(Integer gametype);
	public List<GameVO> getGamesByTeam(Integer teamno);
	public List<GameVO> getGamesByCourtno(Integer courtno);
	//修改比賽結果欄位gameresult
    public void updateGameresult(GameVO gameVO);
    public List<GameVO> getAllGameList();
    public List<GameVO> getGamesByCourtnoToChange(Integer courtno);
}
