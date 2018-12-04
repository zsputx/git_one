package com.dao;

import java.util.List;
import com.entity.lookInfo;
public interface lookInfoDao {
	
	
	 public void add(lookInfo info);
	 public lookInfo findById(String id);
	 public List<lookInfo>findAll();
	 public lookInfo findByName(String name);
	 public  void delLookInfo(String id);
	 public void updateLookInfo(lookInfo info);
}
