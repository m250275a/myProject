package com.member.model;

import java.sql.Date;

public class MemberVO implements java.io.Serializable{
	private Integer memno          ;
	private String memname        ;
	private String memadd	       ;
	private Date memage	       ;
	private String mempassword    ;
	private String memvarname     ;
	private String memphone       ;
	private String mememail       ;
	private String memsex	       ;
	private String memcheck       ;
	private Integer memshit        ;
	private Integer memwow         ;
	private Integer memballage     ;
	private Integer memreb         ;
	private Integer memscore       ;
	private Integer memblock       ;
	private Integer memast	       ;
	private Integer memsteal       ;
	private byte[] memimg;
	public byte[] getMemimg() {
		return memimg;
	}
	public void setMemimg(byte[] memimg) {
		this.memimg = memimg;
	}
	public Integer getMemno() {
		return memno;
	}
	public void setMemno(Integer memno) {
		this.memno = memno;
	}
	public String getMemname() {
		return memname;
	}
	public void setMemname(String memname) {
		this.memname = memname;
	}
	public String getMemadd() {
		return memadd;
	}
	public void setMemadd(String memadd) {
		this.memadd = memadd;
	}
	public Date getMemage() {
		return memage;
	}
	public void setMemage(Date memage) {
		this.memage = memage;
	}
	public String getMempassword() {
		return mempassword;
	}
	public void setMempassword(String mempassword) {
		this.mempassword = mempassword;
	}
	public String getMemvarname() {
		return memvarname;
	}
	public void setMemvarname(String memvarname) {
		this.memvarname = memvarname;
	}
	public String getMemphone() {
		return memphone;
	}
	public void setMemphone(String memphone) {
		this.memphone = memphone;
	}
	public String getMememail() {
		return mememail;
	}
	public void setMememail(String mememail) {
		this.mememail = mememail;
	}
	public String getMemsex() {
		return memsex;
	}
	public void setMemsex(String memsex) {
		this.memsex = memsex;
	}
	public String getMemcheck() {
		return memcheck;
	}
	public void setMemcheck(String memcheck) {
		this.memcheck = memcheck;
	}
	public Integer getMemshit() {
		return memshit;
	}
	public void setMemshit(Integer memshit) {
		this.memshit = memshit;
	}
	public Integer getMemwow() {
		return memwow;
	}
	public void setMemwow(Integer memwow) {
		this.memwow = memwow;
	}
	public Integer getMemballage() {
		return memballage;
	}
	public void setMemballage(Integer memballage) {
		this.memballage = memballage;
	}
	public Integer getMemreb() {
		return memreb;
	}
	public void setMemreb(Integer memreb) {
		this.memreb = memreb;
	}
	public Integer getMemscore() {
		return memscore;
	}
	public void setMemscore(Integer memscore) {
		this.memscore = memscore;
	}
	public Integer getMemblock() {
		return memblock;
	}
	public void setMemblock(Integer memblock) {
		this.memblock = memblock;
	}
	public Integer getMemast() {
		return memast;
	}
	public void setMemast(Integer memast) {
		this.memast = memast;
	}
	public Integer getMemsteal() {
		return memsteal;
	}
	public void setMemsteal(Integer memsteal) {
		this.memsteal = memsteal;
	}
	@Override
	public String toString() {
		return "MemberVO [memno=" + memno + ", memname=" + memname + ", memadd=" + memadd + ", memage=" + memage
				+ ", mempassword=" + mempassword + ", memvarname=" + memvarname + ", memphone=" + memphone
				+ ", mememail=" + mememail + ", memsex=" + memsex + ", memcheck=" + memcheck + ", memshit=" + memshit
				+ ", memwow=" + memwow + ", memballage=" + memballage + ", memreb=" + memreb + ", memscore=" + memscore
				+ ", memblock=" + memblock + ", memast=" + memast + ", memsteal=" + memsteal + "]";
	}
	
	

}
