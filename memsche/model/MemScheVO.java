package com.memsche.model;

import java.io.Serializable;
import java.sql.Date;

public class MemScheVO implements Serializable {
	private Integer memsche;
	private Integer memno;
	private Integer free;
	private Date mdate;
	public Integer getMemsche() {
		return memsche;
	}
	public void setMemsche(Integer memsche) {
		this.memsche = memsche;
	}
	public Integer getMemno() {
		return memno;
	}
	public void setMemno(Integer memno) {
		this.memno = memno;
	}
	public Integer getFree() {
		return free;
	}
	public void setFree(Integer free) {
		this.free = free;
	}
	public Date getMdate() {
		return mdate;
	}
	public void setMdate(Date mdate) {
		this.mdate = mdate;
	}
	
	
}
