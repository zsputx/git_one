package com.daoimpl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dao.lookInfoDao;

import com.entity.lookInfo;




@Transactional
@Repository("lookInfoDaoImpl")
public class lookInfoDaoImpl implements lookInfoDao {
	

	private SessionFactory sessionFactory;

	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource(name="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	

	@Override
	public void add(lookInfo a) {
		this.getCurrentSession().save(a);
		
	}

	@Override
	public lookInfo findById(String id) {
		
		return (lookInfo) this.getCurrentSession().get(lookInfo.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<lookInfo> findAll() {
		String hql="from lookInfo";
		 return this.getCurrentSession().createQuery(hql).list();
	}

	@Override
	public lookInfo findByName(String name) {
		
		 String hql="from lookInfo u where u.name='"+name+"'";
		 return (lookInfo) this.getCurrentSession().createQuery(hql).list().get(0);
	}

	@Override
	public void delLookInfo(String id) {
		lookInfo balance=new lookInfo();
	  balance.setInfoId(Integer.parseInt(id));
	this.getCurrentSession().delete(balance);
		
	}

	@Override
	public void updateLookInfo(lookInfo info) {
		this.getCurrentSession().update(info);
		
	}

	

}
