package com.friend.model;

import java.util.List;

import com.friend.model.FriendVO;

public class FriendService {

	private FriendDAO_interface dao;

	public FriendService() {
		dao = new FriendDAO();
	}

	public FriendVO addFriend(Integer memno, Integer frino) {
		FriendVO friendVO = new FriendVO();
		friendVO.setMemno(memno);
		friendVO.setFrino(frino);
		dao.insert(friendVO);
		return friendVO;
	}

	public void delete(Integer memno, Integer frino) {
		dao.delete(memno, frino);
	};

	public List<FriendVO> getAll(Integer memno) {
		return dao.getAll(memno);
	};

}
