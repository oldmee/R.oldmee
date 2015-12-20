package com.yxq.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.yxq.vo.ArticleBean;
import com.yxq.vo.ReviewBean;

@Transactional
public class ReviewDao extends HibernateDaoSupport {

	public boolean operationReview(String operation, ReviewBean single ,int id) {
		boolean flag = false;
		ArticleBean article = null;
		
			
			article = (ArticleBean)getHibernateTemplate().get(ArticleBean.class,id);

			single.setArticle(article);
			
			if (operation.equals("add")) {
				getHibernateTemplate().save(single);
			}
			if (getHibernateTemplate() != null)
				flag = true;

		return flag;
	}

	public List queryReview(int articleId) {
		List list = null;

			ArticleBean article = (ArticleBean) getHibernateTemplate().get(ArticleBean.class,
					articleId);

			String hql = "from ReviewBean where article.article_id = ? order by reSdTime DESC";

			list = getHibernateTemplate().find(hql,article.getArticle_id());
			
		return list;
	}
	
	public List queryMessage(int articleId) {
		
		List list = null;
		ArticleBean article = (ArticleBean) getHibernateTemplate().get(ArticleBean.class,articleId);
		String hql = "from ReviewBean where article.article_id = ? order by reSdTime DESC";
		list = getHibernateTemplate().find(hql,article.getArticle_id());
		
		return list;
	}
	
}
