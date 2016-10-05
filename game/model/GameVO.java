package com.game.model;

import java.sql.Date;

public class GameVO implements java.io.Serializable {
	private Integer gameno;
	private Date gamedate;
	private Integer memno;
	private Integer teamno;
	private Integer teamno2;
	private Integer courtno;
	private Integer gametype;
	private String gameresult;
	public Integer getGameno() {
		return gameno;
	}
	public void setGameno(Integer gameno) {
		this.gameno = gameno;
	}
	public Date getGamedate() {
		return gamedate;
	}
	public void setGamedate(Date gamedate) {
		this.gamedate = gamedate;
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
	public Integer getTeamno2() {
		return teamno2;
	}
	public void setTeamno2(Integer teamno2) {
		this.teamno2 = teamno2;
	}
	public Integer getCourtno() {
		return courtno;
	}
	public void setCourtno(Integer courtno) {
		this.courtno = courtno;
	}
	public Integer getGametype() {
		return gametype;
	}
	public void setGametype(Integer gametype) {
		this.gametype = gametype;
	}
	public String getGameresult() {
		return gameresult;
	}
	public void setGameresult(String gameresult) {
		this.gameresult = gameresult;
	}
	
}
