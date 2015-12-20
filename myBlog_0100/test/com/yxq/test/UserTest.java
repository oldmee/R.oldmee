package com.yxq.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.yxq.dao.LogonDao;
import com.yxq.vo.MasterBean;

@Transactional
public class UserTest extends HibernateDaoSupport {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		LogonDao dao = (LogonDao) ctx.getBean("LogonDao");
		
		MasterBean mb = new MasterBean();
		mb.setMaster_name("admin");
		mb.setMaster_password("admin");
//		System.out.println(dao.logon(mb));
		
	}
	
}