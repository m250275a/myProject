package com.features.model;



public class FeaturesVO implements java.io.Serializable{
	private Integer feano;
	private String feapower;
	public Integer getFeano() {
		return feano;
	}
	public void setFeano(Integer feano) {
		this.feano = feano;
	}
	public String getFeapower() {
		return feapower;
	}
	public void setFeapower(String feapower) {
		this.feapower = feapower;
	}
	@Override
	public String toString() {
		return "FeaturesVO [feano=" + feano + ", feapower=" + feapower + "]";
	}
}
