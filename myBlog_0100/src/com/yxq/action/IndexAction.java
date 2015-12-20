package com.yxq.action;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.yxq.dao.ArticleDao;
import com.yxq.dao.ArticleTypeDao;
import com.yxq.dao.AuthorReviewDao;
import com.yxq.dao.LogonDao;
import com.yxq.dao.MasterDao;
import com.yxq.dao.ReviewDao;
import com.yxq.vo.ArticleBean;
import com.yxq.vo.AuthorReviewBean;
import com.yxq.vo.MasterBean;

@SuppressWarnings("serial")
public class IndexAction extends ActionSupport {
	
	ActionContext ctx = ActionContext.getContext();
	HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
	HttpSession session = ServletActionContext.getRequest().getSession();

	@SuppressWarnings("unchecked")
	public String execute() {

		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		/*********************得到当前用户的实体************************/
		MasterBean masterBean = (MasterBean)session.getAttribute("master");
		
		MasterBean masterBeanReal = (MasterBean)session.getAttribute("masterReal");
		
		
		String MyName = session.getAttribute("MyName").toString();//登录用户的用户名
		
		ArticleDao articleDao = (ArticleDao) ctx.getBean("ArticleDao");
		
		//调用随机文章方法（后发现如果在登录的时候设置随机文章session那么在对随机文章评论后刷新页面并不会看到自己的评论）
//		ArticleBean articlebean = articleDao.randomSelectArticle(masterBean);
//		session.setAttribute("single", articlebean);
		
		
		
//		System.out.println("articlebean = " + articlebean);
		
		/*
		ArticleBean ab=(ArticleBean)session.getAttribute("readSingle");
    	int articleID = ab.getArticle_id();
    	String hql = "from ArticleBean where ab.article_id <> ?";
		List<ArticleBean> list = this.getHibernateTemplate().find(hql,articleID);//.subList(0, 5);
		
	 	ArticleBean single = list.get((int)Math.random()*(list.size()));*/

		/** ******** 获取在主页面的内容显示区中显示的内容 ******** */
		// 从tb_article数据表中获取前3篇文章
		
		List articleList = articleDao.queryArticle(-1, "",masterBean);//中间的“”设为null就错了
		request.setAttribute("articleList", articleList);

		/** ******** 获取在页面侧栏中显示的内容 ******** */
		/* 从tb_article数据表中获取前5章推荐文章 */
		List artTJList = articleDao.queryArticle(-1, "",masterBean);//中间的“”设为null就错了
		session.setAttribute("artTJList", artTJList);
		
		/** ******** 获取文章类别 ****************** */
		/* 从tb_articleType数据表中获取文章类别 */

		ArticleTypeDao articleTypeDao = (ArticleTypeDao) ctx
				.getBean("ArticleTypeDao");

		List artTypeList = articleTypeDao.queryTypeList(masterBean);
		session.setAttribute("artTypeList", artTypeList);

		/** ********* 保存博主信息 **************** */
		LogonDao logonDao = (LogonDao) ctx.getBean("LogonDao");
		masterBean = logonDao.getMaster(masterBean);
//		masterBeanReal = logonDao.getMaster(masterBeanReal);
		session.setAttribute("master", masterBean);
//		session.setAttribute("masterReal", masterBeanReal);
		
		
		/********************查询作者是否回复了用户**************************/
//		AuthorReviewDao authorReviewDao = (AuthorReviewDao) ctx.getBean("AuthorReviewDao");
		
		
//		MasterDao masterDao = (MasterDao) ctx.getBean("MasterDao");
		
		//Set authorReviews = masterBeanReal.getAuthorReview();//查询到该用户下所有作者评论的bean
		
//		Iterator it = authorReviews.iterator();
//		
//		session.setAttribute("authorReviews", it);
		session.setAttribute("111", masterBeanReal);
		
//		while(it.hasNext()){
//			AuthorReviewBean arb = (AuthorReviewBean) it.next();
//		}
		
		
		//遍历所有的作者回复
		/*Set authorReviews =  masterBean.getAuthorReview();
		
		
		Iterator it_authorReviews = null;
		while(authorReviews.isEmpty()) {
			it_authorReviews = authorReviews.iterator();
		}
		while(it_authorReviews.hasNext()){
			ArticleBean articleBean = (ArticleBean)it_articles.next();
			articleBean.getReviews()
		}*/
		

		return SUCCESS;
	}

	/** ********* 在数据库中获取博主信息 **************** */
	/*
	static {
		// LogonDao logonDao = new LogonDao();
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		session.
		LogonDao logonDao = (LogonDao) ctx.getBean("LogonDao");
		masterBean = logonDao.getMaster();
	}
	*/
}
