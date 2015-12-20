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
	 * @���� ʵ�ֶ����½�������ɾ���ĵĲ���
	 * @���� operΪһ��String���ͱ�����������ʾҪ���еĲ�����singleΪArticleBean����������洢ĳ�����µ���Ϣ
	 * @����ֵ boolean��ֵ
	 */
	public boolean operationArticle(String oper, ArticleBean single) {
		boolean flag = false;

		if (oper.equals("add")) {// ����������
			getHibernateTemplate().save(single);
		}
		if (oper.equals("modify")) { // �޸�����
			getHibernateTemplate().clear();
			getHibernateTemplate().update(single);
		}
		if (oper.equals("delete")) {// ɾ������
			getHibernateTemplate().delete(single);
		}
		if (oper.equals("readTimes")) {// �ۼ��Ķ�����
			single.setCount(single.getCount() + 1);
			getHibernateTemplate().update(single);
		}
		if (getHibernateTemplate() != null)
			flag = true;
		return flag;
	}

	/**
	 * @���� ��ѯָ����������
	 * @���� typeId��ʾ�������IDֵ
	 * @����ֵ List����
	 */
	public List queryArticle(int typeId, String type,MasterBean masterBean) {
		String hql = "";
		
		/***************�õ���ǰ�û���ID****************/
		int masterID = masterBean.getMaster_id();
		
		List<ArticleBean> articlelist = new ArrayList();

		ArticleTypeBean articleType = (ArticleTypeBean) getHibernateTemplate()
				.get(ArticleTypeBean.class, typeId);
		
		if (typeId <= 0 && !type.equals("admin")) {// ��������ѯ����ѯ��ǰ3����¼
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
		} else {// ������ѯ
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
	 * @���� ��ѯָ�����µ���ϸ����
	 * @���� idΪ����IDֵ
	 * @����ֵ ArticleBean����󣬷�װ��������Ϣ
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
