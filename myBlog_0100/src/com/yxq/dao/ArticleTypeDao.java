package com.yxq.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.yxq.vo.ArticleTypeBean;
import com.yxq.vo.MasterBean;

@Transactional
public class ArticleTypeDao  extends HibernateDaoSupport{

	public boolean operationArticleType(String operation, ArticleTypeBean single) {
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

	public ArticleTypeBean queryTypeSingle(int id) {
		ArticleTypeBean articleType = null;

		articleType = (ArticleTypeBean) getHibernateTemplate().get(
				ArticleTypeBean.class, id);

		return articleType;
	}
	
	@SuppressWarnings("unchecked")
	public List queryTypeList(MasterBean masterBean) {
		
		/***************得到当前用户的ID****************/
		int masterID = masterBean.getMaster_id();
		
		String hql = "from ArticleTypeBean where masterBean.master_id = ?";

		List typelist = getHibernateTemplate().find(hql,masterID);

		return typelist;
	}

}
