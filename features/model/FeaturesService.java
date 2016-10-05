package com.features.model;

import java.util.List;
import java.util.Set;

import com.features.model.*;
import com.power.model.*;

public class FeaturesService {
	
	private FeaturesDAO_interface dao;

	public FeaturesService() {
		dao = new FeaturesJNDIDAO();
	}

	public FeaturesVO addFeatures(String feapower) {
		
		FeaturesVO featuresVO = new FeaturesVO();
		featuresVO.setFeapower(feapower);
	
		dao.insert(featuresVO);

		return featuresVO;
	}

	public void deleteFeatures(Integer feano) {
		dao.delete(feano);
	}

	public FeaturesVO getOneFeatures(Integer feano) {
		return dao.findByPrimaryKey(feano);
	}

	public List<FeaturesVO> getAll() {
		return dao.getAll();
	}

	public Set<PowerVO> getPowersByFeaturesno(Integer feano) {
		return dao.getPowersByFeaturesno(feano);
	}

}
