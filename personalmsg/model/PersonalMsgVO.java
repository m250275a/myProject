package com.personalmsg.model;

import java.sql.Timestamp;

public class PersonalMsgVO implements java.io.Serializable {
	private Integer msgno;
	private Integer memno;
	private Integer memedno;
	private String msg;
	private Timestamp msgdate;
	
	
	public Integer getMsgno() {
		return msgno;
	}
	public void setMsgno(Integer msgno) {
		this.msgno = msgno;
	}
	public Integer getMemno() {
		return memno;
	}
	public void setMemno(Integer memno) {
		this.memno = memno;
	}
	public Integer getMemedno() {
		return memedno;
	}
	public void setMemedno(Integer memedno) {
		this.memedno = memedno;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Timestamp getMsgdate() {
		return msgdate;
	}
	public void setMsgdate(Timestamp msgdate) {
		this.msgdate = msgdate;
	}
}
