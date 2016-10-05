package com.challmode.model;

import java.util.List;

public class ChallmodeService {
	private ChallmodeDAO_interface dao;

	public ChallmodeService() {
		dao = new ChallmodeJNDIDAO();
	}

	public ChallmodeVO addChallmode(String challcontent) {
		
		ChallmodeVO challmodeVO = new ChallmodeVO();

		challmodeVO.setChallcontent(challcontent);
		dao.insert(challmodeVO);

		return challmodeVO;
	}

	public ChallmodeVO updateChallmode(Integer challmode, String challcontent) {
		ChallmodeVO challmodeVO = new ChallmodeVO();

		challmodeVO.setChallmode(challmode);
		challmodeVO.setChallcontent(challcontent);
		dao.update(challmodeVO);

		return challmodeVO;
	}

	public void deleteChallmode(Integer challmode) {
		dao.delete(challmode);
	}

	public ChallmodeVO getOneChallmode(Integer challmode) {
		return dao.findByPrimaryKey(challmode);
	}

	public List<ChallmodeVO> getAll() {
		return dao.getAll();
	}


}
