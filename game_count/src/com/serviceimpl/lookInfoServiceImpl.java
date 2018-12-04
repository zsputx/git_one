package com.serviceimpl;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.lookInfoDao;
import com.entity.lookInfo;
import com.service.lookInfoService;

@Service("lookInfoServiceImpl")
@Scope("prototype")
@Transactional
public class lookInfoServiceImpl implements lookInfoService {

	
	private lookInfoDao lookInfod;

	public lookInfoDao getLookInfo() {
		return lookInfod;
	}

	@Resource(name = "lookInfoDaoImpl")
	public void setLookInfo(lookInfoDao lookInfod) {
		this.lookInfod = lookInfod;
	}

	@Override
	public void save(lookInfo info) {
		lookInfod.add(info);
		
	}
	
	
	
	
}
