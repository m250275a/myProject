package com.news.model;

import java.util.*;

public interface NewsDAO_interface {
	public void insert(NewsVO newsVO);
    public void update(NewsVO newsVO);
    public void delete(Integer newsno);
    public NewsVO findByPrimaryKey(Integer newsno);
    public List<NewsVO> getAll();

}
