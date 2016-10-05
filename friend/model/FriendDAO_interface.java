package com.friend.model;

import java.util.List;

import com.friend.model.FriendVO;

public interface FriendDAO_interface {
	 public FriendVO insert(FriendVO friendVO);
     public void delete(Integer memno,Integer frino); 
     public List<FriendVO> getAll(Integer memno);
}
