package com.yxq.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.yxq.dao.LogonDao;
import com.yxq.vo.AdminBean;
import com.yxq.vo.MasterBean;

@SuppressWarnings("serial")
public class LogXAction extends ActionSupport {
	ActionContext ctx = ActionContext.getContext();
	HttpServletRequest request = (HttpServletRequest) ctx
			.get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) ctx
			.get(ServletActionContext.HTTP_RESPONSE);
	HttpSession session = ServletActionContext.getRequest().getSession();

	private String action;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String execute() {
		try {
			if (action == null)
				action = "";
			if (action.equals("islogon")) {
				this.isLogon(request, response);
			}
			if (action.equals("logon")) {
				this.logon(request, response);
			}
			if (action.equals("logon_admin")) {
				this.logon_admin(request, response);
			}
			if (action.equals("logout")) {
				this.logout(request, response);
			}
			if (action.equals("register")) {
				this.register(request, response);
			}
			if (action.equals("validate")) {
				this.validate(request, response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void isLogon(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String forward = "";
		HttpSession session = request.getSession();
		if (session.getAttribute("logoner") != null)
			forward = "admin/AdminIndex.jsp";
		else
			forward = "admin/logon.jsp";
		response.sendRedirect(forward);
	}

	public boolean validateLogon(HttpServletRequest request,
			HttpServletResponse response) {
		boolean mark = true;
		String messages = "";
		String name = request.getParameter("userName");
		String password = request.getParameter("userPass");
		if (name == null || name.equals("")) {
			mark = false;
			messages += "<li>������ <b>�û�����</b></li>";
		}
		if (password == null || password.equals("")) {
			mark = false;
			messages += "<li>������ <b>��&nbsp;&nbsp;�룡</b></li>";
		}
		request.setAttribute("messages", messages);
		return mark;
	}

	public boolean validateRegister(HttpServletRequest request,
			HttpServletResponse response) {
		boolean mark = true;
		String messages = "";
		String name = request.getParameter("userName");
		String password = request.getParameter("userPass");
		String password_again = request.getParameter("userPass_again");
		String sex = "��";
		String qq = "1";
		if (password.length() < 6) {
			mark = false;
			messages += "<li>���벻��С����λ��</li>";
		}
		if (name == null || name.equals("") || password == null
				|| password.equals("") || sex == null || sex.equals("")
				|| qq == null || qq.equals("")
				|| !password_again.equals(password)) {
			mark = false;
			messages += "<li>������Ϣ����</li>";
		}
		/*
		 * if (name == null || name.equals("")) { mark = false; messages += "<li>�������û�����</li>"; }
		 * if (password == null || password.equals("")) { mark = false; messages += "<li>���������룡</li>"; }
		 * if (!password_again.equals(password)) { mark = false; messages += "<li>������������벻һ�£�</li>"; }
		 * if (sex == null || sex.equals("")) { mark = false; messages += "<li>�������Ա�</li>"; }
		 * if (qq == null || qq.equals("")) { mark = false; messages += "<li>������OICQ���룡</li>"; }
		 */
		request.setAttribute("messages", messages);
		return mark;
	}

	public void logon(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean flag = validateLogon(request, response);
		RequestDispatcher rd = null;
		if (flag) {
			MasterBean logoner = new MasterBean();
			MasterBean logonerReal = new MasterBean();
			
			logonerReal.setMaster_name(request.getParameter("userName"));
			logonerReal.setMaster_password(request.getParameter("userPass"));
			session.setAttribute("MyName", request.getParameter("userName"));
//			logoner.setMaster_name("kevin_lee");
//			logoner.setMaster_password("123456");

			ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

			LogonDao logonDao = (LogonDao) ctx.getBean("LogonDao");

			boolean mark = logonDao.logon(logonerReal);

			/** *************ͨ���û��������������ֶλ��һ��������MasterBeanʵ��*************** */
			if (mark){
				logoner = logonDao.query();
				logonerReal = logonDao.queryMaster(logonerReal);
			}

			if (!mark) {
				request.setAttribute("messages", "<li>������û������������</li>");
				rd = request.getRequestDispatcher("/admin/logon.jsp");
				rd.forward(request, response);
			}  else {
				session.setAttribute("master", logoner);
				session.setAttribute("masterReal", logonerReal);
				response.sendRedirect("index.jsp");
			}
		} else {
			rd = request.getRequestDispatcher("/admin/logon.jsp");
			rd.forward(request, response);
		}
	}

	public void logon_admin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean flag = validateLogon(request, response);
		RequestDispatcher rd = null;
		if (flag) {
			AdminBean logoner = new AdminBean();
			logoner.setAdmin_name(request.getParameter("userName"));
			logoner.setAdmin_password(request.getParameter("userPass"));

			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					"applicationContext.xml");

			LogonDao logonDao = (LogonDao) ctx.getBean("LogonDao");

			boolean mark = logonDao.logon_admin(logoner);

			if (!mark) {
				request.setAttribute("messages", "<li>�û������������</li>");
				rd = request.getRequestDispatcher("admin/login_admin.jsp");
				rd.forward(request, response);
			} else {
				session.setAttribute("admin", logoner);
				response.sendRedirect("admin/index.jsp");
			}
		} else {
			rd = request.getRequestDispatcher("admin/login_admin.jsp");
			rd.forward(request, response);
		}
	}

	public void register(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean flag = validateRegister(request, response);
		RequestDispatcher rd = null;
		if (flag) {
			MasterBean logoner = new MasterBean();

			logoner.setMaster_name(request.getParameter("userName"));
			logoner.setMaster_password(request.getParameter("userPass"));
			logoner.setMaster_sex("��");
			logoner.setMaster_oicq("1");

			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					"applicationContext.xml");
			LogonDao logonDao = (LogonDao) ctx.getBean("LogonDao");

			boolean login = false;
			login = logonDao.logon_is_exist(logoner);
			boolean mark = false;
			if (login == false)
				mark = logonDao.register(logoner);
			if (login) {
				request.setAttribute("messages", "<li>�ʺ��Ѿ���ע�ᣡ</li>");
				rd = request.getRequestDispatcher("register.jsp");
				rd.forward(request, response);
				return;
			} else if (!mark) {
				request.setAttribute("messages", "<li>ע��ʧ�ܣ�</li>");
				rd = request.getRequestDispatcher("register.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("messages", "<li>ע��ɹ���</li>");
				request.getRequestDispatcher("login.jsp").forward(request,
						response);
			}
		} else {
			rd = request.getRequestDispatcher("register.jsp");
			rd.forward(request, response);
		}
	}

	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("logoner");
		RequestDispatcher rd = request
				.getRequestDispatcher("/front/FrontIndex.jsp");
		rd.forward(request, response);
	}

	public void validate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		  response.setContentType("text/html;charset=GB2312");
		  PrintWriter out = response.getWriter();
          //�˴����Է������ݿ�
          
          ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
          LogonDao logonDao = (LogonDao) ctx.getBean("LogonDao");
          
          MasterBean logoner = new MasterBean();

		  logoner.setMaster_name(request.getParameter("userName"));
          
          boolean login = false;
		  login = logonDao.logon_is_exist(logoner);
          
          if (login) 
        	  out.print("1");
          else
        	  out.print("0");
	}
}
