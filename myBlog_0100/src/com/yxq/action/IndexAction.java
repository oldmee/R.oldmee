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
		
		/*********************�õ���ǰ�û���ʵ��************************/
		MasterBean masterBean = (MasterBean)session.getAttribute("master");
		
		MasterBean masterBeanReal = (MasterBean)session.getAttribute("masterReal");
		
		
		String MyName = session.getAttribute("MyName").toString();//��¼�û����û���
		
		ArticleDao articleDao = (ArticleDao) ctx.getBean("ArticleDao");
		
		//����������·�������������ڵ�¼��ʱ�������������session��ô�ڶ�����������ۺ�ˢ��ҳ�沢���ῴ���Լ������ۣ�
//		ArticleBean articlebean = articleDao.randomSelectArticle(masterBean);
//		session.setAttribute("single", articlebean);
		
		
		
//		System.out.println("articlebean = " + articlebean);
		
		/*
		ArticleBean ab=(ArticleBean)session.getAttribute("readSingle");
    	int articleID = ab.getArticle_id();
    	String hql = "from ArticleBean where ab.article_id <> ?";
		List<ArticleBean> list = this.getHibernateTemplate().find(hql,articleID);//.subList(0, 5);
		
	 	ArticleBean single = list.get((int)Math.random()*(list.size()));*/

		/** ******** ��ȡ����ҳ���������ʾ������ʾ������ ******** */
		// ��tb_article���ݱ��л�ȡǰ3ƪ����
		
		List articleList = articleDao.queryArticle(-1, "",masterBean);//�м�ġ�����Ϊnull�ʹ���
		request.setAttribute("articleList", articleList);

		/** ******** ��ȡ��ҳ���������ʾ������ ******** */
		/* ��tb_article���ݱ��л�ȡǰ5���Ƽ����� */
		List artTJList = articleDao.queryArticle(-1, "",masterBean);//�м�ġ�����Ϊnull�ʹ���
		session.setAttribute("artTJList", artTJList);
		
		/** ******** ��ȡ������� ****************** */
		/* ��tb_articleType���ݱ��л�ȡ������� */

		ArticleTypeDao articleTypeDao = (ArticleTypeDao) ctx
				.getBean("ArticleTypeDao");

		List artTypeList = articleTypeDao.queryTypeList(masterBean);
		session.setAttribute("artTypeList", artTypeList);

		/** ********* ���沩����Ϣ **************** */
		LogonDao logonDao = (LogonDao) ctx.getBean("LogonDao");
		masterBean = logonDao.getMaster(masterBean);
//		masterBeanReal = logonDao.getMaster(masterBeanReal);
		session.setAttribute("master", masterBean);
//		session.setAttribute("masterReal", masterBeanReal);
		
		
		/********************��ѯ�����Ƿ�ظ����û�**************************/
//		AuthorReviewDao authorReviewDao = (AuthorReviewDao) ctx.getBean("AuthorReviewDao");
		
		
//		MasterDao masterDao = (MasterDao) ctx.getBean("MasterDao");
		
		//Set authorReviews = masterBeanReal.getAuthorReview();//��ѯ�����û��������������۵�bean
		
//		Iterator it = authorReviews.iterator();
//		
//		session.setAttribute("authorReviews", it);
		session.setAttribute("111", masterBeanReal);
		
//		while(it.hasNext()){
//			AuthorReviewBean arb = (AuthorReviewBean) it.next();
//		}
		
		
		//�������е����߻ظ�
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

	/** ********* �����ݿ��л�ȡ������Ϣ **************** */
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
