package com.court.model;

import java.util.*;
import com.game.model.GameVO;
import com.team.model.TeamVO;

public interface CourtDAO_interface {
	public void insert(CourtVO courtVO);
    public void update(CourtVO courtVO);
    public void delete(Integer courtno);
    public CourtVO findByPrimaryKey(Integer courtno);
    public List<CourtVO> getAll();
	public byte[] GetImg(Integer courtno);
	public Set<TeamVO> getTeamsByCourtno(Integer courtno);
	public List<CourtVO> getAll(Map<String, String[]> map);
	public Set<GameVO> getGamesByCourtno(Integer courtno);
}
