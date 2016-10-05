package com.challmode.model;

import java.util.*;

public interface ChallmodeDAO_interface {
	public void insert(ChallmodeVO challmodeVO);
    public void update(ChallmodeVO challmodeVO);
    public void delete(Integer challmode);
    public ChallmodeVO findByPrimaryKey(Integer challmode);
    public List<ChallmodeVO> getAll();

}
