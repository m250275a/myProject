package com.challteam.model;

import java.util.List;

import com.challteam.model.ChallteamVO;

public interface ChallteamDAO_interface {
	 public ChallteamVO addChallteam(ChallteamVO challteamVO);
     public void delete(Integer teamno,Integer challmode); 
     public List<ChallteamVO> getAll(Integer teamno);
}
