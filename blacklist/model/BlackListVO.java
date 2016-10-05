package com.blacklist.model;

import java.io.Serializable;

public class BlackListVO implements Serializable {
	private	Integer blackno;
	private Integer memno ;
	private Integer memedno ;
	
	public Integer getBlackno() {
		return blackno;
	}
	public void setBlackno(Integer blackno) {
		this.blackno = blackno;
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
	
	
	
	
}
