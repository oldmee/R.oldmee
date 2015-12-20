package com.yxq.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.yxq.vo.AdminBean;
import com.yxq.vo.MasterBean;

@Transactional
public class LogonDao extends HibernateDaoSupport {

	public MasterBean getMaster(MasterBean masterBean) {
		
		int id = masterBean.getMaster_id(); 
		masterBean = (MasterBean) getHibernateTemplate().get(MasterBean.class,id);
		return masterBean;
	}

	@SuppressWarnings("unchecked")
	public boolean logon_is_exist(MasterBean logoner) {

		boolean mark = false;
		if (logoner != null) {

			String hql = "from MasterBean where master_name=?";
			List list = null;
			list = getHibernateTemplate().find(hql, logoner.getMaster_name());

			if (list.size() != 0)
				mark = true;
			else
				mark = false;

		}
		return mark;
	}

	/**
	 * @author javaer_lee@yahoo.cn
	 * �ж��û��������Ƿ���ȷ
	 */
	@SuppressWarnings("unchecked")
	public boolean logon(MasterBean logoner) {

		boolean mark = false;
		if (logoner != null) {

			String hql = "from MasterBean where master_name=? and master_password=?";
			String logoner_info[] = { logoner.getMaster_name(),logoner.getMaster_password() };
			List list = null;

			list = getHibernateTemplate().find(hql, logoner_info);

			if (list.size() != 0)
				mark = true;
			else
				mark = false;
		}
		return mark;
	}
	
	public boolean logon_admin(AdminBean logoner) {

		boolean mark = false;
		if (logoner != null) {

			String hql = "from AdminBean where admin_name=? and admin_password=?";
			String logoner_info[] = { logoner.getAdmin_name(),
					logoner.getAdmin_password() };
			List list = null;

			list = getHibernateTemplate().find(hql, logoner_info);

			if (list.size() != 0)
				mark = true;
			else
				mark = false;
		}
		return mark;
	}
	
	/**
	 * 
	 * @param masterBean
	 * @return һ��������MasterBeanʵ��
	 */
	@SuppressWarnings("unchecked")
	public MasterBean queryMaster(MasterBean masterBean) {
		String hql = "from MasterBean where master_name=? and master_password=?";
		String logoner_info[] = { masterBean.getMaster_name(),masterBean.getMaster_password() };

		List list = getHibernateTemplate().find(hql, logoner_info);

		for (int i=0;i<list.size();i++) {
			masterBean = (MasterBean)list.get(i);
		}
		return masterBean;
	}
	
	@SuppressWarnings("unchecked")
	
	
	public MasterBean query() {
		String hql = "from MasterBean where master_name=? and master_password=?";
		String logoner_info[] = { "kevin_lee","123456"};//�����е�¼�û�����λ��kevin_lee����ҳ

		List list = getHibernateTemplate().find(hql, logoner_info);

		MasterBean masterBean = null;
		//���ѵ�����ֻ��һ��ֵ��
		for (int i=0;i<list.size();i++) {
			masterBean = (MasterBean)list.get(i);
		}
		return masterBean;
	}
	

	public boolean register(MasterBean logoner) {

		boolean mark = false;
		getHibernateTemplate().save(logoner);
		if (getHibernateTemplate() != null) {
			mark = true;
		}
		return mark;
	}
}
