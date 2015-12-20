package com.yxq.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.yxq.vo.ArticleBean;
import com.yxq.vo.ArticleTypeBean;
import com.yxq.vo.MasterBean;
import com.yxq.vo.ReviewBean;

@SuppressWarnings("unchecked")
@Transactional
public class ArticleDao extends HibernateDaoSupport {

	/**
	 * @功能 实现对文章进行增、删、改的操作
	 * @参数 oper为一个String类型变量，用来表示要进行的操作；single为ArticleBean类对象，用来存储某个文章的信息
	 * @返回值 boolean型值
	 */
	public boolean operationArticle(String oper, ArticleBean single) {
		boolean flag = false;

		if (oper.equals("add")) {// 发表新文章
			getHibernateTemplate().save(single);
		}
		if (oper.equals("modify")) { // 修改文章
			getHibernateTemplate().clear();
			getHibernateTemplate().update(single);
		}
		if (oper.equals("delete")) {// 删除文章
			getHibernateTemplate().delete(single);
		}
		if (oper.equals("readTimes")) {// 累加阅读次数
			single.setCount(single.getCount() + 1);
			getHibernateTemplate().update(single);
		}
		if (getHibernateTemplate() != null)
			flag = true;
		return flag;
	}

	/**
	 * @功能 查询指定类别的文章
	 * @参数 typeId表示文章类别ID值
	 * @返回值 List集合
	 */
	public List queryArticle(int typeId, String type,MasterBean masterBean) {
		String hql = "";
		
		/***************得到当前用户的ID****************/
		int masterID = masterBean.getMaster_id();
		
		List<ArticleBean> articlelist = new ArrayList();

		ArticleTypeBean articleType = (ArticleTypeBean) getHibernateTemplate()
				.get(ArticleTypeBean.class, typeId);
		
		if (typeId <= 0 && !type.equals("admin")) {// 不按类别查询，查询出前3条记录
			hql = "from ArticleBean where masterBean.master_id = ?";
			if (getHibernateTemplate().find(hql, masterID).size()>=5) {
				articlelist = getHibernateTemplate().find(hql, masterID).subList(0, 5);
			}
			else {
				articlelist = getHibernateTemplate().find(hql, masterID);
			}
		} else if(typeId <= 0 && type.equals("admin")) {
			hql = "from ArticleBean where masterBean.master_id = ?";
			if (getHibernateTemplate().find(hql, masterID) != null) {
				articlelist = getHibernateTemplate().find(hql, masterID);
			}
		} else {// 按类别查询
			Object article_info[] = { typeId, masterID };
			if (type == null || type.equals("") || !type.equals("all")) {
				hql = "from ArticleBean where articleType.articleType_id = ? and masterBean.master_id = ?";
				if (getHibernateTemplate().find(hql, article_info) != null
						&& articleType != null) {
					articlelist = getHibernateTemplate()
							.find(hql, article_info);
				}
			} else if (articleType != null) {
				hql = "from ArticleBean where articleType.articleType_id = ? and masterBean.master_id = ?";
				if (getHibernateTemplate().find(hql, article_info) != null) {
					articlelist = getHibernateTemplate()
							.find(hql, article_info);
				}
			}
		}
		return articlelist;
	}

	/**
	 * @功能 查询指定文章的详细内容
	 * @参数 id为文章ID值
	 * @返回值 ArticleBean类对象，封装了文章信息
	 */
	public ArticleBean queryArticleSingle(int id) {

		ArticleBean article = new ArticleBean();

		article = (ArticleBean) getHibernateTemplate().get(ArticleBean.class,
				id);

		return article;
	}
	
	
	/*public ArticleBean randomSelectArticle(MasterBean masterBean) {
		
		String hql = "from ArticleBean where masterBean.master_id <> ?";
		List<ArticleBean> list = getHibernateTemplate().find(hql,masterBean.getMaster_id());
		
		return list.get((int)(Math.random()*(list.size())));
		
	}*/
}
