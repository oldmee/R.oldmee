package com.yxq.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
import com.yxq.dao.ArticleDao;
import com.yxq.dao.ArticleTypeDao;
import com.yxq.dao.AuthorReviewDao;
import com.yxq.dao.MasterDao;
import com.yxq.dao.ReviewDao;
import com.yxq.toolsbean.MyTools;
import com.yxq.vo.ArticleBean;
import com.yxq.vo.ArticleTypeBean;
import com.yxq.vo.AuthorReviewBean;
import com.yxq.vo.MasterBean;
import com.yxq.vo.ReviewBean;

@SuppressWarnings("serial")
public class ArticleAction extends ActionSupport {

	ActionContext ctx = ActionContext.getContext();
	HttpServletRequest request = (HttpServletRequest) ctx
			.get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) ctx
			.get(ServletActionContext.HTTP_RESPONSE);
	HttpSession session = ServletActionContext.getRequest().getSession();

	private String action;
	
	private String master_id;
	
	public String getMaster_id() {
		return master_id;
	}

	public void setMaster_id(String master_id) {
		this.master_id = master_id;
	}
	
	private int pageNo;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

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
			if (action.equals("select"))
				this.selectArticle(request, response); // ��ȡĳ�������������
			if (action.equals("adminSelectList"))
				this.adminSelectList(request, response);
			if (action.equals("adminSelectList_admin"))
				this.adminSelectList_admin(request, response);
			if (action.equals("adminSelectSingle"))
				this.adminSelectSingle(request, response);
			if (action.equals("adminSelectSingle_admin"))
				this.adminSelectSingle_admin(request, response);
			if (action.equals("add")) {
				this.addArticle(request, response); // ��������
			}
			if (action.equals("delete"))
				this.deleteArticle(request, response); // ɾ������
			if (action.equals("modify"))
				this.modifyArticle(request, response); // �޸�����
			if (action.equals("read"))
				this.readArticle(request, response); // �Ķ�����
			if (action.equals("followAdd"))
				this.validateFollow(request, response); // �������»ظ�
			if (action.equals("followReply")){
				this.validateFollowReply(request, response); // �������ۻظ�
				
			}
			if (action.equals("typeAdd"))
				this.addArticleType(request, response); // �����������
			if (action.equals("typeSelect"))
				this.selectArticleType(request, response); // �鿴�������
			if (action.equals("typeModify"))
				this.modifyArticleType(request, response); // �޸��������
			if (action.equals("masterModify"))
				this.modifyMaster(request, response); // �޸��û�
			
			if (action.equals("masterDelete"))
				this.deleteMaster(request, response); // ɾ���û�
			
			if (action.equals("typeDelete"))
				this.deleteArticleType(request, response); // ɾ���������
			if (action.equals("masterSelect"))
				this.selectMaster(request, response); // �鿴�û�
			if (action.equals("reply"))
				this.reply(request, response); // �鿴�û�
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
	 * @���� ��֤������Ϣ
	 */
	public void validateFollow(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean mark = true;
		String messages = "";

		String content = request.getParameter("reContent");
		if (content == null || content.equals("")) {
			mark = false;
			messages = "<li>������ <b>�������ݣ�</b></li>";
		}
		if (mark) {
			content = MyTools.toChinese(content);
			if (content.length() > 1000) {
				mark = false;
				messages = "<li><b>��������</b> �����������1000���ַ���</li>";
			}
		}
		if (!mark) {
			request.setAttribute("messages", messages);
			RequestDispatcher rd = request
					.getRequestDispatcher("/front/article/error.jsp");
			rd.forward(request, response);
		} else {
			followAdd(request, response);
		}
	}

	
	/**
	 * @���� ��֤���ۻظ���Ϣ
	 */
	
	public void validateFollowReply(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		boolean mark = true;
		String messages = "";

		String content1 = MyTools.toChinese(request.getParameter("comment"));
		String content2 = MyTools.toChinese(request.getParameter("reply"));
		
		
		String content = content1 + content2;
//		String content = content2;
		
		if (content == null || content.equals("")) {
			mark = false;
			messages = "<li>������ <b>�������ݣ�</b></li>";
		}
		if (mark) {
			content = MyTools.toChinese(content);
			if (content.length() > 1000) {
				mark = false;
				messages = "<li><b>��������</b> �����������1000���ַ���</li>";
			}
		}
		if (!mark) {
			request.setAttribute("messages", messages);
			RequestDispatcher rd = request.getRequestDispatcher("/front/article/error.jsp");
			rd.forward(request, response);
		} else {
			followReply(request, response);
		}
	}
	
	
	/**
	 * @���� �����������
	 */
	public void followAdd(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("articleId"));

//		String author = MyTools.toChinese(request.getParameter("reAuthor"));
//		if (author == null || author.equals(""))
//			author = "��������";

		
		String content = MyTools.toChinese(request.getParameter("reContent"));
		String today = MyTools.changeTime(new Date());
		MasterBean masterBean = (MasterBean)session.getAttribute("master");
		String author = masterBean.getMaster_name();
		String MyName = session.getAttribute("MyName").toString();
		
		
		/***********�ظ��������߻ظ���ͬʱ�������ݣ�ÿ����������������������*****************/
		ReviewBean reviewBean = new ReviewBean();
//		AuthorReviewBean authorRviewBean = new AuthorReviewBean();
		
		
		reviewBean.setReAuthor(MyName);
		reviewBean.setReContent(content);
		reviewBean.setReSdTime(today);

		
//		authorRviewBean.setReSdTime(today);
//		authorRviewBean.setReview(reviewBean);
		
//		ReviewDao reviewDao = new ReviewDao();
		String messages = "";
		String forward = "";
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		ReviewDao reviewDao = (ReviewDao) ctx.getBean("ReviewDao");
//		AuthorReviewDao authorReviewDao = (AuthorReviewDao) ctx.getBean("AuthorReviewDao");
//		authorReviewDao.operationReview("add",authorReviewBean,id);
		
		boolean mark = reviewDao.operationReview("add", reviewBean, id);
		
//		session.removeAttribute("single");
//		session.setAttribute("single", reviewBean.getArticle());
		
		if (mark) {
			forward = "/front/article/success.jsp";
			messages = "<li>�������۳ɹ���</li>";

		} else {
			forward = "/front/article/error.jsp";
			messages = "<li>��������ʧ�ܣ�</li>";
		}
		request.setAttribute("messages", messages);
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);

	}
	
	
	/**
	 * @���� ������ۻظ�
	 */

	public void followReply(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("articleId"));
		int reviewId = Integer.parseInt(request.getParameter("reviewId"));
//		String author = MyTools.toChinese(request.getParameter("reAuthor"));
//		if (author == null || author.equals(""))
//			author = "��������";
		
		
		ReviewBean reviewSingle = (ReviewBean)session.getAttribute("ReviewBean");//��ȡ����Ҫ���۵��������۵�bean
		
		String realMaster = reviewSingle.getReAuthor();//��ȡ�������۵��������۵��û�
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		ReviewDao reviewDao = (ReviewDao) ctx.getBean("ReviewDao");
		
		/**********************************�����û����ҵ��û���id**************************************************/
		
		MasterDao masterDao = (MasterDao) ctx.getBean("MasterDao");
		List realmanList = masterDao.queryMasterList();
		
		//ͨ���������е��û����ҵ�������
		MasterBean mb = null;
		for(int i =0 ;i<realmanList.size();i++){
			mb = (MasterBean)realmanList.get(i);
			if(mb.getMaster_name().equals(realMaster))
				break;
		}
		
		/**********************************�����û����ҵ��û���id**************************************************/
		
		
		
		
		
		String content1 = MyTools.toChinese(request.getParameter("comment"))+"<br><br>";
		String content2 = "���߻ظ���"+MyTools.toChinese(request.getParameter("reply"));
		
		
		String today = MyTools.changeTime(new Date());
		MasterBean masterBean = (MasterBean)session.getAttribute("masterReal");//��ȡ��¼�û���bean
		String author = masterBean.getMaster_name();
		String MyName = session.getAttribute("MyName").toString();
		
		ReviewBean reviewBean = new ReviewBean();
		AuthorReviewBean authorReviewBean = new AuthorReviewBean();
		
		
		reviewBean.setReAuthor(MyName);
		reviewBean.setReContent(content1+content2);
//		reviewBean.setReContent(content2);
		reviewBean.setReSdTime(today);

//		ReviewDao reviewDao = new ReviewDao();
		
		authorReviewBean.setAuthorReplyContent(content2);
		authorReviewBean.setReSdTime(today);
		authorReviewBean.setReview(reviewBean);
		
		authorReviewBean.setMaster(mb);//���������߸�ֵ��authorReviewBean
		
		String messages = "";
		String forward = "";
		
		
		AuthorReviewDao authorReviewDao = (AuthorReviewDao) ctx.getBean("AuthorReviewDao");
		
		authorReviewDao.operationAuthorReview("add",authorReviewBean,reviewId);
		
		boolean mark = reviewDao.operationReview("add", reviewBean, id);
		
		//�ڻظ�����֮�������ݿ����һ�����ݣ������û���idname�����µ�id��
		
		
		
//		session.removeAttribute("single");
//		session.setAttribute("single", reviewBean.getArticle());
		
		if (mark) {
			forward = "/front/article/success.jsp";
			messages = "<li>�������۳ɹ���</li>";

		} else {
			forward = "/front/article/error.jsp";
			messages = "<li>��������ʧ�ܣ�</li>";
		}
		request.setAttribute("messages", messages);
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);

	}
	
	/**
	 * @���� �Ķ�����
	 * @ʵ�� 1.���������Ķ�����<br>
	 *     2.��ȡָ��������Ϣ<br>
	 *     3.��ȡ�Ը����µ���������
	 */
	@SuppressWarnings("unchecked")
	public void readArticle(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ArticleBean articleBean = new ArticleBean();
		String strId = request.getParameter("article_id");
		int id = MyTools.strToint(strId);

		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		ArticleDao articleDao = (ArticleDao) ctx.getBean("ArticleDao");

		articleBean = articleDao.queryArticleSingle(id);

		articleBean.setArticle_id(id);
		articleDao.operationArticle("readTimes",articleBean); // �ۼ��Ķ�����
		session.setAttribute("readSingle", articleBean); // ����articleBean��session������

		ReviewDao reviewDao = (ReviewDao) ctx.getBean("ReviewDao");

		List reviewlist = reviewDao.queryReview(id);

		session.setAttribute("reviewlist", reviewlist);
		MasterBean masterBean = (MasterBean)session.getAttribute("master");
		RequestDispatcher rd;
		
		if(articleBean.getMasterBean().getMaster_id() == masterBean.getMaster_id()) {
			rd = request.getRequestDispatcher("/front/article/ArticleSingle.jsp");
		} else {
			rd = request.getRequestDispatcher("/front/article/RandomArticleSingle.jsp");
		}
		rd.forward(request, response);
	}

	/**
	 * @���� �޸�����
	 */
	public void modifyArticle(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = null;
		String type = request.getParameter("type");

		ArticleBean articleBean = new ArticleBean();
		boolean mark = false;
		if (type == null)
			type = "";

		if (!type.equals("doModify")) {

			int id = MyTools.strToint(request.getParameter("id"));

			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					"applicationContext.xml");

			ArticleDao articleDao = (ArticleDao) ctx.getBean("ArticleDao");

			articleBean = articleDao.queryArticleSingle(id);
			request.setAttribute("modifySingle", articleBean);
			rd = request
					.getRequestDispatcher("/admin/article/ArticleModify.jsp");
			rd.forward(request, response);

		} else {
			String messages = "";
			String href = "";
			String forward = "";
			boolean flag = validateArticle(request, response);
			if (flag) {
				ArticleTypeBean articleType = new ArticleTypeBean();

				ApplicationContext ctx = new ClassPathXmlApplicationContext(
						"applicationContext.xml");

				ArticleTypeDao articleTypeDao = (ArticleTypeDao) ctx
						.getBean("ArticleTypeDao");

				ArticleDao articleDao = (ArticleDao) ctx
						.getBean("ArticleDao");
				
				articleBean.setArticle_id(Integer.valueOf(request
						.getParameter("id")));
				
				articleType = articleTypeDao.queryTypeSingle(Integer
						.valueOf(request.getParameter("typeId")));
				
				articleBean.setArticleType(articleType);

				articleBean.setTitle(MyTools.toChinese(request
						.getParameter("title")));
				articleBean.setCreateFrom(MyTools.toChinese(request
						.getParameter("create")));
				articleBean.setInfo(MyTools.toChinese(request
						.getParameter("info")));
				articleBean.setContent(MyTools.toChinese(request
						.getParameter("content")));
				articleBean.setSdTime(MyTools.changeTime(new Date()));
				
				/** Hibernate�ڸ������µ�ʱ��ѻظ�Ҳ�����ˣ����ǻظ�
				 *  ��article_id����Ϊ��(���ڻ���һ�ַ������ô����ݿ����ü�����)
				 *	ReviewBean review = new ReviewBean();
			     *	review.setArticle(articleBean);
				 **/
				
				/*********************�õ���ǰ�û���ʵ��************************/
				MasterBean masterBean = (MasterBean)session.getAttribute("master");
				
				articleBean.setMasterBean(masterBean);
				
				boolean boo = articleDao.operationArticle("modify", articleBean);

				if (boo == true)
					mark = true;

				if (mark) {
					messages = "<li>�޸ĳɹ���</li>";
					href = "<a href='ArticleAction?action=adminSelectList&typeId="
							+ session.getAttribute("showTypeId")
							+ "'>[�����޸���������]</a>";
					forward = "/admin/success.jsp";
				} else {
					messages = "<li>�޸�ʧ�ܣ�</li>";
					href = "<a href='javascript:window.history.go(-1)'>[����]</a>";
					forward = "/admin/error.jsp";
				}
				request.setAttribute("messages", messages);
				request.setAttribute("href", href);
			} else {
				href = "<a href='javascript:window.history.go(-1)'>[����]</a>";
				forward = "/admin/error.jsp";
				request.setAttribute("href", href);
			}
			rd = request.getRequestDispatcher(forward);
			rd.forward(request, response);
		}
	}

	/**
	 * @���� ��̨-ɾ������
	 */
	public void deleteArticle(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String messages = "";
		String href = "";
		String forward = "";
		boolean mark = false;

		ArticleBean article = new ArticleBean();

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		ArticleDao articleDao = (ArticleDao) ctx.getBean("ArticleDao");
		
		article = articleDao.queryArticleSingle(MyTools.strToint(request.getParameter("id")));
		
		boolean boo = articleDao.operationArticle("delete", article);

		if (boo == true)
			mark = true;

		if (mark) {
			String typeId = request.getParameter("typeId");
			messages += "<li>ɾ�����³ɹ���</li>";
			href = "<a href='ArticleAction?action=adminSelectList_admin&pageNo="+pageNo+"&master_id="+master_id+"&typeId="
					+ typeId + "'>[����ɾ����������]</a>";
			forward = "/admin/success.jsp";

		} else {
			messages += "<li>ɾ������ʧ�ܣ�</li>";
			href = "<a href='javascript:window.history.go(-1)'>[����]</a>";
			forward = "/admin/error.jsp";
		}
		request.setAttribute("messages", messages);
		request.setAttribute("href", href);
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}
	
	/**
	 * @���� ��̨-ɾ���û�
	 */
	public void deleteMaster(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String messages = "";
		String href = "";
		String forward = "";
		boolean mark = false;

		MasterBean master = new MasterBean();

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		MasterDao masterDao = (MasterDao) ctx.getBean("MasterDao");
		
		master = masterDao.queryMaster(MyTools.strToint(request.getParameter("masterId")));
		boolean boo = masterDao.operationMaster("delete", master);

		if (boo == true)
			mark = true;

		if (mark) {
			messages += "<li>ɾ���û��ɹ���</li>";
			href = "<a href='ArticleAction?pageNo="+pageNo+"&action=masterSelect'>[�����޸������û�]</a>";
			forward = "/admin/success_admin.jsp";

		} else {
			messages += "<li>ɾ���û�ʧ�ܣ�</li>";
			href = "<a href='javascript:window.history.go(-1)'>[����]</a>";
			forward = "/admin/error_admin.jsp";
		}
		request.setAttribute("messages", messages);
		request.setAttribute("href", href);
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}

	/**
	 * @���� ��֤����������Ϣ
	 */
	public boolean validateArticle(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean mark = true;
		String messages = "";

		String typeId = request.getParameter("typeId");
		String title = request.getParameter("title");
		String create = request.getParameter("create");
		String info = request.getParameter("info");
		String content = request.getParameter("content");

		if (typeId == null || typeId.equals("")) {
			mark = false;
			messages += "<li>��ѡ�� <b>�������</b></li>";
		}
		if (title == null || title.equals("")) {
			mark = false;
			messages += "<li>������ <b>���±��⣡</b></li>";
		}
		if (create == null || create.equals("")) {
			mark = false;
			messages += "<li>��ѡ�� <b>������Դ��</b></li>";
		}
		if (info == null || info.equals("")) {
			mark = false;
			messages += "<li>������ <b>����������</b></li>";
		}
		if (content == null || content.equals("")) {
			mark = false;
			messages += "<li>������ <b>�������ݣ�</b></li>";
		}
		if (mark) {
			title = MyTools.toChinese(title);
			content = MyTools.toChinese(content);
			if (title.length() > 20) {
				mark = false;
				messages += "<li><b>���±���</b> �����������20���ַ���</li>";
			}
			if (content.length() > 4000) {
				mark = false;
				messages += "<li><b>��������</b> �����������4000���ַ���</li>";
			}
		}
		request.setAttribute("messages", messages);
		return mark;
	}

	/**
	 * @���� ��̨-��������
	 */
	public void addArticle(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String messages = "";
		String href = "";
		String forward = "";
		boolean mark = false;
		boolean flag = validateArticle(request, response);

		if (flag) {

			ArticleBean articleBean = new ArticleBean();

			ArticleTypeBean articleType = new ArticleTypeBean();

			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					"applicationContext.xml");

			ArticleDao articleDao = (ArticleDao) ctx.getBean("ArticleDao");

			ArticleTypeDao articleTypeDao = (ArticleTypeDao)ctx.getBean("ArticleTypeDao");
			
			articleType = articleTypeDao.queryTypeSingle(MyTools.strToint(request.getParameter("typeId")));
			
			articleBean.setArticleType(articleType);
			articleBean.setTitle(MyTools.toChinese(request
					.getParameter("title")));
			articleBean.setContent(MyTools.changeHTML(MyTools.toChinese(request
					.getParameter("content"))));
			articleBean.setSdTime(MyTools.changeTime(new Date()));
			articleBean.setCreateFrom(MyTools.toChinese(request
					.getParameter("create")));
			articleBean.setInfo(MyTools.toChinese(request.getParameter("info")));
			articleBean.setCount(0);
			
			/*********************ָ����ƪ�������ĸ��û���д*********************/
			MasterBean masterBean = (MasterBean)session.getAttribute("master");
			articleBean.setMasterBean(masterBean);

			boolean boo = articleDao.operationArticle("add", articleBean);

			if (boo == true)
				mark = true;
			if (mark) {
				messages = "<li>�������³ɹ���</li>";
				href = "<a href='admin/article/ArticleAdd.jsp'>[��������]</a>";
				forward = "/admin/success.jsp";
			} else {
				messages = "<li>��������ʧ�ܣ�</li>";
				href = "<a href='javascript:window.history.go(-1)'>[����]</a>";
				forward = "/admin/error.jsp";
			}
			request.setAttribute("messages", messages);
			request.setAttribute("href", href);

		} else {
			href = "<a href='javascript:window.history.go(-1)'>[����]</a>";
			forward = "/admin/error.jsp";
			request.setAttribute("href", href);
		}
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}

	/**
	 * @���� ʵ�ֺ�̨���¹����е������������
	 */
	public void adminSelectList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String strId = request.getParameter("typeId");
		if (strId == null || strId.equals(""))
			strId = "-1";
		int typeId = Integer.parseInt(strId);
		session.setAttribute("showTypeId", typeId);
		request.setAttribute("type_id", typeId);
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
		"applicationContext.xml");

		ArticleDao articleDao = (ArticleDao) ctx.getBean("ArticleDao");
		
		String strpageNo = request.getParameter("pageNo");
		request.setAttribute("pageNo",strpageNo);
		
		MasterBean masterBean = (MasterBean)session.getAttribute("master");
		
		List articleList = articleDao.queryArticle(typeId, "admin",masterBean);
		request.setAttribute("articleList", articleList);
		RequestDispatcher rd = request
				.getRequestDispatcher("/admin/article/ArticleList.jsp");
		rd.forward(request, response);
	}
	
	public void adminSelectList_admin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String strId = request.getParameter("typeId");
		if (strId == null || strId.equals(""))
			strId = "-1";
		int typeId = Integer.parseInt(strId);
		session.setAttribute("showTypeId", typeId);
		request.setAttribute("type_id", typeId);
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
		"applicationContext.xml");

		ArticleDao articleDao = (ArticleDao) ctx.getBean("ArticleDao");
		
		request.setAttribute("pageNo",pageNo);
		
		MasterDao masterdao = (MasterDao) ctx.getBean("MasterDao");
		int masterID = Integer.parseInt(master_id);
		MasterBean masterBean = (MasterBean)masterdao.queryMaster(masterID);
		request.setAttribute("master_id",masterBean.getMaster_id());
		ArticleTypeDao articleTypeDao = (ArticleTypeDao) ctx.getBean("ArticleTypeDao");
		List artTypeList = articleTypeDao.queryTypeList(masterBean);
		session.setAttribute("artTypeList", artTypeList);
		
		
		List articleList = articleDao.queryArticle(typeId, "admin",masterBean);
		request.setAttribute("articleList", articleList);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/article/ArticleList_admin.jsp");
		rd.forward(request, response);
	}

	public void adminSelectSingle(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = MyTools.strToint(request.getParameter("id"));
		ArticleBean article = new ArticleBean();

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		ArticleDao articleDao = (ArticleDao) ctx.getBean("ArticleDao");
		article = articleDao.queryArticleSingle(id);

		request.setAttribute("articleSingle", article);

		RequestDispatcher rd = request
				.getRequestDispatcher("/admin/article/ArticleSingle.jsp");
		rd.forward(request, response);
	}
	
	public void adminSelectSingle_admin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = MyTools.strToint(request.getParameter("id"));
		ArticleBean article = new ArticleBean();

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		ArticleDao articleDao = (ArticleDao) ctx.getBean("ArticleDao");
		article = articleDao.queryArticleSingle(id);

		request.setAttribute("articleSingle", article);

		RequestDispatcher rd = request
				.getRequestDispatcher("/admin/article/ArticleSingle_admin.jsp");
		rd.forward(request, response);
	}

	/**
	 * @���� �����ݱ��л�ȡĳ����µ���������
	 */
	public void selectArticle(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String strId = request.getParameter("typeId");
		
		if (strId == null || strId.equals(""))
			strId = "-1";
		int typeId = Integer.parseInt(strId);
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
		"applicationContext.xml");

		ArticleDao articleDao = (ArticleDao) ctx.getBean("ArticleDao");
		
		String strpageNo = request.getParameter("pageNo");
		request.setAttribute("pageNo",strpageNo);
		
		/*****************�õ���ǰ�û���ʵ��******************/
		MasterBean masterBean = (MasterBean)session.getAttribute("master");
		List articleList = articleDao.queryArticle(typeId, "all",masterBean);

		request.setAttribute("articleList", articleList);
		RequestDispatcher rd = request
				.getRequestDispatcher("/front/article/ArticleList.jsp");
		rd.forward(request, response);
	}

	public boolean validateType(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean mark = true;
		String messages = "";

		String typeName = request.getParameter("typeName");
		String typeInfo = request.getParameter("typeInfo");

		if (typeName == null || typeName.equals("")) {
			mark = false;
			messages += "<li>������ <b>������ƣ�</b></li>";
		}
		if (typeInfo == null || typeInfo.equals("")) {
			mark = false;
			messages += "<li>������ <b>�����ܣ�</b></li>";
		}
		request.setAttribute("messages", messages);
		return mark;
	}

	/**
	 * @���� ��̨-�����������
	 */
	public void addArticleType(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String messages = "";
		String href = "";
		String forward = "";

		boolean flag = validateType(request, response);
		if (flag) {
			ArticleTypeBean typeBean = new ArticleTypeBean();
			typeBean.setTypeName(MyTools.toChinese(request
					.getParameter("typeName")));
			typeBean.setTypeInfo(MyTools.toChinese(request
					.getParameter("typeInfo")));
			
			/*********************ָ�����������ĸ��û������*********************/
			MasterBean masterBean = (MasterBean)session.getAttribute("master");
			typeBean.setMasterBean(masterBean);

			ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"applicationContext.xml");

			ArticleTypeDao articleTypeDao = (ArticleTypeDao) ctx.getBean("ArticleTypeDao");
			
			boolean mark = articleTypeDao.operationArticleType("add", typeBean);
			if (mark) {
				
				IndexAction index = new IndexAction();
				index.execute();
				
				messages += "<li>����������ɹ���</li>";
				href = "<a href='admin/article/ArticleTypeAdd.jsp'>[��������������]</a>";
				forward = "/admin/success.jsp";

			} else {
				messages += "<li>����������ʧ�ܣ�</li>";
				href = "<a href='javascript:window.history.go(-1)'>[����]</a>";
				forward = "/admin/error.jsp";
			}
			request.setAttribute("messages", messages);
			request.setAttribute("href", href);
		} else {
			href = "<a href='javascript:window.history.go(-1)'>[����]</a>";
			forward = "/admin/error.jsp";
			request.setAttribute("href", href);
		}
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}

	@SuppressWarnings("unchecked")
	public void selectArticleType(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		/*********************�õ���ǰ�û���ʵ��************************/
		MasterBean masterBean = (MasterBean)session.getAttribute("master");
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
		"applicationContext.xml");

		ArticleTypeDao articleTypeDao = (ArticleTypeDao) ctx.getBean("ArticleTypeDao");
		
		List typelist = articleTypeDao.queryTypeList(masterBean);
		request.setAttribute("typelist", typelist);
		RequestDispatcher rd = request
				.getRequestDispatcher("/admin/article/ArticleTypeList.jsp");
		rd.forward(request, response);
	}
	/**
	 * �鿴�����û�
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void selectMaster(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
		"applicationContext.xml");

		MasterDao masterDao = (MasterDao) ctx.getBean("MasterDao");
		
		String strpageNo = request.getParameter("pageNo");
		request.setAttribute("pageNo",strpageNo);
		
		List masterlist = masterDao.queryMasterList();
		request.setAttribute("masterlist", masterlist);
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/article/MasterList.jsp");
		rd.forward(request, response);
	}

	/**
	 * @���� ��̨-�޸��������
	 */
	public void modifyArticleType(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		ArticleTypeBean typeBean = null;
		ArticleTypeDao articleTypeDao = null;
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
		"applicationContext.xml");

		articleTypeDao = (ArticleTypeDao) ctx.getBean("ArticleTypeDao");

		String type = request.getParameter("type");
		if (type == null)
			type = "";
		if (!type.equals("doModify")) {
			int typeId = MyTools.strToint(request.getParameter("typeId"));
			
			typeBean = articleTypeDao.queryTypeSingle(typeId);
			request.setAttribute("typeSingle", typeBean);
			rd = request
					.getRequestDispatcher("/admin/article/ArticleTypeModify.jsp");
			rd.forward(request, response);
		} else {
			String messages = "";
			String href = "";
			String forward = "";
			boolean flag = validateType(request, response);
			if (flag) {
				typeBean = new ArticleTypeBean();
				typeBean.setArticleType_id(MyTools.strToint(request
						.getParameter("typeId")));
				typeBean.setTypeName(MyTools.toChinese(request
						.getParameter("typeName")));
				typeBean.setTypeInfo(MyTools.toChinese(request
						.getParameter("typeInfo")));
				
				/*********************�õ���ǰ�û���ʵ��************************/
				MasterBean masterBean = (MasterBean)session.getAttribute("master");
				typeBean.setMasterBean(masterBean);

				boolean mark = articleTypeDao.operationArticleType("modify", typeBean);
				if (mark) {
					messages = "<li>�޸����ɹ���</li>";
					href = "<a href='ArticleAction?action=typeSelect'>[�����޸��������]</a>";
					forward = "/admin/success.jsp";

				} else {
					messages = "<li>�޸�ʧ�ܣ�</li>";
					href = "<a href='javascript:window.history.go(-1)>[����]</a>";
					forward = "/admin/error.jsp";
				}
				request.setAttribute("messages", messages);
				request.setAttribute("href", href);
			} else {
				href = "<a href='javascript:window.history.go(-1)>[����]</a>";
				forward = "/admin/error.jsp";
				request.setAttribute("href", href);
			}
			rd = request.getRequestDispatcher(forward);
			rd.forward(request, response);
		}
	}
	
	/**
	 * @���� ��̨-�޸��û�Ȩ��
	 */
	public void modifyMaster(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		MasterBean masterBean = null;
		MasterDao masterDao = null;

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		masterDao = (MasterDao) ctx.getBean("MasterDao");

		String type = request.getParameter("type");
		if (type == null)
			type = "";
		if (!type.equals("doModify")) {
			int masterId = MyTools.strToint(request.getParameter("masterId"));
			masterBean = masterDao.queryMaster(masterId);
			request.setAttribute("masterSingle", masterBean);
			rd = request.getRequestDispatcher("/admin/article/MasterModify.jsp");
			rd.forward(request, response);
		} else {

			String messages = "";
			String href = "";
			String forward = "";
			int masterId = MyTools.strToint(request.getParameter("masterId"));
			masterBean = masterDao.queryMaster(masterId);
			
			boolean state = Boolean.parseBoolean(request.getParameter("state"));
			masterBean.setMaster_state(state);
			boolean mark = masterDao.operationMaster("modify", masterBean);
			if (mark) {
				messages = "<li>�޸��û��ɹ���</li>";
				href = "<a href='ArticleAction?pageNo="+pageNo+"&action=masterSelect'>[�����޸������û�]</a>";
				forward = "/admin/success_admin.jsp";

			} else {
				messages = "<li>�޸�ʧ�ܣ�</li>";
				href = "<a href='javascript:window.history.go(-1)>[����]</a>";
				forward = "/admin/error_admin.jsp";
			}
			request.setAttribute("messages", messages);
			request.setAttribute("href", href);
			rd = request.getRequestDispatcher(forward);
			rd.forward(request, response);
		}
	}

	/**
	 * @���� ��̨-ɾ���������
	 */
	public void deleteArticleType(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		ArticleTypeDao articleTypeDao = new ArticleTypeDao();
		ArticleTypeBean articleTypeBean = new ArticleTypeBean();

		String messages = "";
		String href = "";
		String forward = "";
		boolean mark = false;

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		articleTypeDao = (ArticleTypeDao) ctx.getBean("ArticleTypeDao");

		articleTypeBean = articleTypeDao.queryTypeSingle(MyTools.strToint(request.getParameter("typeId")));
		
		boolean boo = articleTypeDao.operationArticleType("delete",
				articleTypeBean);

		if (boo == true)
			mark = true;
		// boolean mark = typeDao.operationArticleType("delete", typeBean);
		if (mark) {
			messages += "<li>ɾ�����ɹ���</li>";
			href = "<a href='ArticleAction?action=typeSelect'>[����ɾ���������]</a>";
			forward = "/admin/success.jsp";

		} else {
			messages += "<li>ɾ�����ʧ�ܣ�</li>";
			href = "<a href='javascript:window.history.go(-1)'>[����]</a>";
			forward = "/admin/error.jsp";
		}
		request.setAttribute("messages", messages);
		request.setAttribute("href", href);
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}
	
	/**
	 * @���� ��̨-ɾ���������
	 */
	public void reply(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
	}

}
