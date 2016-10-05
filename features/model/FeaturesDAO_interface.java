package com.features.model;

import java.util.List;
import java.util.Set;

import com.features.model.FeaturesVO;
import com.power.model.PowerVO;

public interface FeaturesDAO_interface {
	 public void insert(FeaturesVO featuresVO);
     public void delete(Integer feano); 
     public FeaturesVO findByPrimaryKey(Integer feano);
     public List<FeaturesVO> getAll();
	public Set<PowerVO> getPowersByFeaturesno(Integer feano);
}
