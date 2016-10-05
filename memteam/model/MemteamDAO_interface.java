package com.memteam.model;

import java.util.List;
import java.util.Set;

import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import com.friend.model.FriendVO;
import com.team.model.TeamVO;

public interface MemteamDAO_interface {
	 public int insert(MemteamVO memteamVO);
	 //�h��
     public int delete(Integer memno,Integer teamno); 
     
     public List<MemteamVO> getAll();
     
     //�η|����y��
     public List<MemteamVO> getTeamBYMemno(Integer memno);
     //�ư��y������
     public List<MemteamVO> getAllTeamName();
     //�ư��ӷ|���w�[�J���y��
     public List<MemteamVO> getTeamBesideMem(Integer memno);
     //�|���[�J�y��
     public void joinTeam(MemteamVO memteamVO);
    
}

