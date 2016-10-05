package com.personalmsg.model;

import java.util.*;

public interface PersonalMsgDAO_interface {
	public void insert(PersonalMsgVO personalMsgVO);
	public void update(PersonalMsgVO personalMsgVO);
	public void delete(Integer msgno);
	public List<PersonalMsgVO>getAll();
	public PersonalMsgVO findByPrimaryKey(Integer msgno);
	public List<PersonalMsgVO> getMsgsByMemedno(Integer memedno);
	public List<PersonalMsgVO>getMemednos();
	public void insertByMsg(PersonalMsgVO personalMsgVO);
	//public List<PersonalMsgVO>getAll(Map<String,string[]> map);
}
