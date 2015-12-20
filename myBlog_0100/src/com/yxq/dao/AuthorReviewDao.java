package com.yxq.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.yxq.vo.ArticleBean;
import com.yxq.vo.AuthorReviewBean;
import com.yxq.vo.ReviewBean;

@Transactional
public class AuthorReviewDao extends HibernateDaoSupport {

	public boolean operationAuthorReview(String operation,AuthorReviewBean single, int id) {
		boolean flag = false;
		ReviewBean review = null;

		review = (ReviewBean) getHibernateTemplate().get(ReviewBean.class, id);

		single.setReview(review);

		if (operation.equals("add")) {
			getHibernateTemplate().save(single);
		}
		if (getHibernateTemplate() != null)
			flag = true;

		return flag;
	}
	
	
	/********************≤È—ØµΩ**********************/
	@SuppressWarnings("unchecked")
	public List queryReview(int reviewId) {
		List list = null;

		ReviewBean review = (ReviewBean) getHibernateTemplate().get(ReviewBean.class,reviewId);

		String hql = "from AuthorReviewBean where review.review_id = ? order by reSdTime DESC";

		list = getHibernateTemplate().find(hql,review.getReview_id());
			
		return list;
	}

}
