package com.personalmsg.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public class PersonalMsgService {

	private PersonalMsgDAO_interface dao;

	public PersonalMsgService() {

		dao = new PersonalMsgDAO();
	}

	public PersonalMsgVO addPersonalMsg(Integer memno, Integer memedno, String msg, Timestamp msgdate) {

		PersonalMsgVO personalMsgVO = new PersonalMsgVO();

		personalMsgVO.setMemno(memno);
		personalMsgVO.setMemedno(memedno);
		personalMsgVO.setMsg(msg);
		personalMsgVO.setMsgdate(msgdate);
		dao.insert(personalMsgVO);

		return personalMsgVO;
	}

	public PersonalMsgVO updatePersonalMsg(Integer msgno, Integer memno, Integer memedno, String msg, 
			Timestamp msgdate){
		
		PersonalMsgVO personalMsgVO = new PersonalMsgVO();
		
		personalMsgVO.setMsgno(msgno);
		personalMsgVO.setMemno(memno);
		personalMsgVO.setMemedno(memedno);
		personalMsgVO.setMsg(msg);
		personalMsgVO.setMsgdate(msgdate);
		dao.update(personalMsgVO);
		
		return personalMsgVO;
		
	}
	
	public void deletePersonalMsg(Integer msgno){
		dao.delete(msgno);
	}
	
	public PersonalMsgVO getOnePersonalMsg(Integer msgno){
		return dao.findByPrimaryKey(msgno);
	}
	
	public List<PersonalMsgVO> getAll(){
		return dao.getAll();
	}
	
	public List<PersonalMsgVO>getMsgsByMemedno(Integer memedno){
		return dao.getMsgsByMemedno(memedno);
	}
	
	public List<PersonalMsgVO>getMemednos(){
		return dao.getMemednos();
	}
	
	public PersonalMsgVO insertByMsg(Integer memno, Integer memedno, String msg, Timestamp msgdate) {

		PersonalMsgVO personalMsgVO = new PersonalMsgVO();

		personalMsgVO.setMemno(memno);
		personalMsgVO.setMemedno(memedno);
		personalMsgVO.setMsg(msg);
		personalMsgVO.setMsgdate(msgdate);
		dao.insertByMsg(personalMsgVO);

		return personalMsgVO;
	}
}
