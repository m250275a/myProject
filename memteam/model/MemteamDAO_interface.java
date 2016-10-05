package com.memteam.model;

import java.util.List;
import java.util.Set;

import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import com.friend.model.FriendVO;
import com.team.model.TeamVO;

public interface MemteamDAO_interface {
	 public int insert(MemteamVO memteamVO);
	 //退隊
     public int delete(Integer memno,Integer teamno); 
     
     public List<MemteamVO> getAll();
     
     //用會員找球隊
     public List<MemteamVO> getTeamBYMemno(Integer memno);
     //排除球隊重複
     public List<MemteamVO> getAllTeamName();
     //排除該會員已加入的球隊
     public List<MemteamVO> getTeamBesideMem(Integer memno);
     //會員加入球隊
     public void joinTeam(MemteamVO memteamVO);
    
}

