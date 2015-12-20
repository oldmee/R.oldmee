package com.yxq.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.yxq.vo.ArticleTypeBean;
import com.yxq.vo.MasterBean;

@Transactional
public class MasterDao  extends HibernateDaoSupport{

	public boolean operationMaster(String operation, MasterBean single) {
		boolean flag = false;

			if (operation.equals("add")) {
				getHibernateTemplate().save(single);
			}
			if (operation.equals("modify")) {
				getHibernateTemplate().update(single);
			}
			if (operation.equals("delete")) {
				getHibernateTemplate().delete(single);
			}
			if (getHibernateTemplate() != null) {
				flag = true;
			}
		return flag;
	}

	@SuppressWarnings("unchecked")
	public List queryMasterList() {
		
		String hql = "from MasterBean";

		List typelist = getHibernateTemplate().find(hql);

		return typelist;
	}
	
	public MasterBean queryMaster(int id) {
		MasterBean master = null;
		master = (MasterBean) getHibernateTemplate().get(MasterBean.class, id);
		return master;
	}
	

	public MasterBean queryRealMaster(String masterName) {

		String hql = "from MasterBean where masterBean.masterName=?";

		MasterBean typelist = (MasterBean) getHibernateTemplate().find(hql,masterName);

		return typelist;
	}
}
