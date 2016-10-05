package com.memteam.model;

public class MemteamVO implements java.io.Serializable{
	private Integer memno;
	private Integer teamno;
	private Integer reqno;
	private Integer teamadmin;
	
	public Integer getReqno() {
		return reqno;
	}
	public void setReqno(Integer reqno) {
		this.reqno = reqno;
	}
	public Integer getTeamadmin() {
		return teamadmin;
	}
	public void setTeamadmin(Integer teamadmin) {
		this.teamadmin = teamadmin;
	}
	
	public Integer getMemno() {
		return memno;
	}
	public void setMemno(Integer memno) {
		this.memno = memno;
	}
	public Integer getTeamno() {
		return teamno;
	}
	public void setTeamno(Integer teamno) {
		this.teamno = teamno;
	}
	
	

}
