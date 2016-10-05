package com.court.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.game.model.GameVO;
import com.team.model.TeamVO;

public class CourtService {
	
	private CourtDAO_interface dao;

	public CourtService() {
		dao = new CourtJNDIDAO();
	}

	public CourtVO addCourt(Double courtlat, Double courtlng, String courtloc, String courtname, Integer courtnum, Integer basketnum, String courttype,
			String opentime, String courtlight, String courthost, String courtphone, String courtdesc, Integer courtmoney, byte[] courtimg, Integer whoname)
	{
		CourtVO courtVO = new CourtVO();
		
		courtVO.setCourtlat(courtlat);
		courtVO.setCourtlng(courtlng);
		courtVO.setCourtloc(courtloc);
		courtVO.setCourtname(courtname);
		courtVO.setCourtnum(courtnum);
		courtVO.setBasketnum(basketnum);
		courtVO.setCourttype(courttype);
		courtVO.setOpentime(opentime);
		courtVO.setCourtlight(courtlight);
		courtVO.setCourthost(courthost);
		courtVO.setCourtphone(courtphone);
		courtVO.setCourtdesc(courtdesc);
		courtVO.setCourtmoney(courtmoney);
		courtVO.setCourtimg(courtimg);
		courtVO.setWhoname(whoname);
		dao.insert(courtVO);

		return courtVO;
	}

	public CourtVO updateCourt(Integer courtno,String courtname, Integer whoname) 
	{
		CourtVO courtVO = new CourtVO();

		courtVO.setCourtno(courtno);
//		courtVO.setCourtlat(courtlat);
//		courtVO.setCourtlng(courtlng);
//		courtVO.setCourtloc(courtloc);
		courtVO.setCourtname(courtname);
//		courtVO.setCourtnum(courtnum);
//		courtVO.setBasketnum(basketnum);
//		courtVO.setCourttype(courttype);
//		courtVO.setOpentime(opentime);
//		courtVO.setCourtlight(courtlight);
//		courtVO.setCourthost(courthost);
//		courtVO.setCourtphone(courtphone);
//		courtVO.setCourtdesc(courtdesc);
//		courtVO.setCourtmoney(courtmoney);
//		courtVO.setCourtimg(courtimg);
		courtVO.setWhoname(whoname);
		dao.update(courtVO);

		return courtVO;
	}

	public void deleteCourt(Integer courtno) {
		dao.delete(courtno);
	}

	public CourtVO getOneCourt(Integer courtno) {
		return dao.findByPrimaryKey(courtno);
	}

	public List<CourtVO> getAll() {
		return dao.getAll();
	}
	
	public List<CourtVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}

	public byte[] getOneImg(Integer courtno) {
		return dao.GetImg(courtno);
	}

	public Set<TeamVO> getTeamsByCourtno(Integer courtno) {
		return dao.getTeamsByCourtno(courtno);
	}
	public Set<GameVO> getGamesByCourtno(Integer courtno) {
		return dao.getGamesByCourtno(courtno);
	}
	
}
