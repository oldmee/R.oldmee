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
				this.selectArticle(request, response); // 获取某类别下所有文章
			if (action.equals("adminSelectList"))
				this.adminSelectList(request, response);
			if (action.equals("adminSelectList_admin"))
				this.adminSelectList_admin(request, response);
			if (action.equals("adminSelectSingle"))
				this.adminSelectSingle(request, response);
			if (action.equals("adminSelectSingle_admin"))
				this.adminSelectSingle_admin(request, response);
			if (action.equals("add")) {
				this.addArticle(request, response); // 增加文章
			}
			if (action.equals("delete"))
				this.deleteArticle(request, response); // 删除文章
			if (action.equals("modify"))
				this.modifyArticle(request, response); // 修改文章
			if (action.equals("read"))
				this.readArticle(request, response); // 阅读文章
			if (action.equals("followAdd"))
				this.validateFollow(request, response); // 发表文章回复
			if (action.equals("followReply")){
				this.validateFollowReply(request, response); // 发表评论回复
				
			}
			if (action.equals("typeAdd"))
				this.addArticleType(request, response); // 增加文章类别
			if (action.equals("typeSelect"))
				this.selectArticleType(request, response); // 查看文章类别
			if (action.equals("typeModify"))
				this.modifyArticleType(request, response); // 修改文章类别
			if (action.equals("masterModify"))
				this.modifyMaster(request, response); // 修改用户
			
			if (action.equals("masterDelete"))
				this.deleteMaster(request, response); // 删除用户
			
			if (action.equals("typeDelete"))
				this.deleteArticleType(request, response); // 删除文章类别
			if (action.equals("masterSelect"))
				this.selectMaster(request, response); // 查看用户
			if (action.equals("reply"))
				this.reply(request, response); // 查看用户
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
	 * @功能 验证评论信息
	 */
	public void validateFollow(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean mark = true;
		String messages = "";

		String content = request.getParameter("reContent");
		if (content == null || content.equals("")) {
			mark = false;
			messages = "<li>请输入 <b>评论内容！</b></li>";
		}
		if (mark) {
			content = MyTools.toChinese(content);
			if (content.length() > 1000) {
				mark = false;
				messages = "<li><b>评论内容</b> 最多允许输入1000个字符！</li>";
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
	 * @功能 验证评论回复信息
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
			messages = "<li>请输入 <b>评论内容！</b></li>";
		}
		if (mark) {
			content = MyTools.toChinese(content);
			if (content.length() > 1000) {
				mark = false;
				messages = "<li><b>评论内容</b> 最多允许输入1000个字符！</li>";
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
	 * @功能 添加文章评论
	 */
	public void followAdd(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("articleId"));

//		String author = MyTools.toChinese(request.getParameter("reAuthor"));
//		if (author == null || author.equals(""))
//			author = "匿名好友";

		
		String content = MyTools.toChinese(request.getParameter("reContent"));
		String today = MyTools.changeTime(new Date());
		MasterBean masterBean = (MasterBean)session.getAttribute("master");
		String author = masterBean.getMaster_name();
		String MyName = session.getAttribute("MyName").toString();
		
		
		/***********回复表与作者回复表同时插入数据，每个动作都进行这两个操作*****************/
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
			messages = "<li>发表评论成功！</li>";

		} else {
			forward = "/front/article/error.jsp";
			messages = "<li>发表评论失败！</li>";
		}
		request.setAttribute("messages", messages);
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);

	}
	
	
	/**
	 * @功能 添加评论回复
	 */

	public void followReply(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("articleId"));
		int reviewId = Integer.parseInt(request.getParameter("reviewId"));
//		String author = MyTools.toChinese(request.getParameter("reAuthor"));
//		if (author == null || author.equals(""))
//			author = "匿名好友";
		
		
		ReviewBean reviewSingle = (ReviewBean)session.getAttribute("ReviewBean");//获取作者要评论的那条评论的bean
		
		String realMaster = reviewSingle.getReAuthor();//获取作者评论的哪条评论的用户
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		ReviewDao reviewDao = (ReviewDao) ctx.getBean("ReviewDao");
		
		/**********************************利用用户名找到用户的id**************************************************/
		
		MasterDao masterDao = (MasterDao) ctx.getBean("MasterDao");
		List realmanList = masterDao.queryMasterList();
		
		//通过遍历所有的用户，找到评论者
		MasterBean mb = null;
		for(int i =0 ;i<realmanList.size();i++){
			mb = (MasterBean)realmanList.get(i);
			if(mb.getMaster_name().equals(realMaster))
				break;
		}
		
		/**********************************利用用户名找到用户的id**************************************************/
		
		
		
		
		
		String content1 = MyTools.toChinese(request.getParameter("comment"))+"<br><br>";
		String content2 = "作者回复："+MyTools.toChinese(request.getParameter("reply"));
		
		
		String today = MyTools.changeTime(new Date());
		MasterBean masterBean = (MasterBean)session.getAttribute("masterReal");//获取登录用户的bean
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
		
		authorReviewBean.setMaster(mb);//将被评论者赋值给authorReviewBean
		
		String messages = "";
		String forward = "";
		
		
		AuthorReviewDao authorReviewDao = (AuthorReviewDao) ctx.getBean("AuthorReviewDao");
		
		authorReviewDao.operationAuthorReview("add",authorReviewBean,reviewId);
		
		boolean mark = reviewDao.operationReview("add", reviewBean, id);
		
		//在回复评论之后往数据库插入一条数据，包括用户的idname，文章的id。
		
		
		
//		session.removeAttribute("single");
//		session.setAttribute("single", reviewBean.getArticle());
		
		if (mark) {
			forward = "/front/article/success.jsp";
			messages = "<li>发表评论成功！</li>";

		} else {
			forward = "/front/article/error.jsp";
			messages = "<li>发表评论失败！</li>";
		}
		request.setAttribute("messages", messages);
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);

	}
	
	/**
	 * @功能 阅读文章
	 * @实现 1.增加文章阅读次数<br>
	 *     2.获取指定文章信息<br>
	 *     3.获取对该文章的所有评论
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
		articleDao.operationArticle("readTimes",articleBean); // 累加阅读次数
		session.setAttribute("readSingle", articleBean); // 保存articleBean到session对象中

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
	 * @功能 修改文章
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
				
				/** Hibernate在更新文章的时候把回复也更新了，但是回复
				 *  的article_id不能为空(现在换了一种方法：该从数据库设置级联了)
				 *	ReviewBean review = new ReviewBean();
			     *	review.setArticle(articleBean);
				 **/
				
				/*********************得到当前用户的实体************************/
				MasterBean masterBean = (MasterBean)session.getAttribute("master");
				
				articleBean.setMasterBean(masterBean);
				
				boolean boo = articleDao.operationArticle("modify", articleBean);

				if (boo == true)
					mark = true;

				if (mark) {
					messages = "<li>修改成功！</li>";
					href = "<a href='ArticleAction?action=adminSelectList&typeId="
							+ session.getAttribute("showTypeId")
							+ "'>[继续修改其他文章]</a>";
					forward = "/admin/success.jsp";
				} else {
					messages = "<li>修改失败！</li>";
					href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
					forward = "/admin/error.jsp";
				}
				request.setAttribute("messages", messages);
				request.setAttribute("href", href);
			} else {
				href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
				forward = "/admin/error.jsp";
				request.setAttribute("href", href);
			}
			rd = request.getRequestDispatcher(forward);
			rd.forward(request, response);
		}
	}

	/**
	 * @功能 后台-删除文章
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
			messages += "<li>删除文章成功！</li>";
			href = "<a href='ArticleAction?action=adminSelectList_admin&pageNo="+pageNo+"&master_id="+master_id+"&typeId="
					+ typeId + "'>[继续删除其他文章]</a>";
			forward = "/admin/success.jsp";

		} else {
			messages += "<li>删除文章失败！</li>";
			href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
			forward = "/admin/error.jsp";
		}
		request.setAttribute("messages", messages);
		request.setAttribute("href", href);
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}
	
	/**
	 * @功能 后台-删除用户
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
			messages += "<li>删除用户成功！</li>";
			href = "<a href='ArticleAction?pageNo="+pageNo+"&action=masterSelect'>[继续修改其他用户]</a>";
			forward = "/admin/success_admin.jsp";

		} else {
			messages += "<li>删除用户失败！</li>";
			href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
			forward = "/admin/error_admin.jsp";
		}
		request.setAttribute("messages", messages);
		request.setAttribute("href", href);
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}

	/**
	 * @功能 验证发表文章信息
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
			messages += "<li>请选择 <b>文章类别！</b></li>";
		}
		if (title == null || title.equals("")) {
			mark = false;
			messages += "<li>请输入 <b>文章标题！</b></li>";
		}
		if (create == null || create.equals("")) {
			mark = false;
			messages += "<li>请选择 <b>文章来源！</b></li>";
		}
		if (info == null || info.equals("")) {
			mark = false;
			messages += "<li>请输入 <b>文章描述！</b></li>";
		}
		if (content == null || content.equals("")) {
			mark = false;
			messages += "<li>请输入 <b>文章内容！</b></li>";
		}
		if (mark) {
			title = MyTools.toChinese(title);
			content = MyTools.toChinese(content);
			if (title.length() > 20) {
				mark = false;
				messages += "<li><b>文章标题</b> 最多允许输入20个字符！</li>";
			}
			if (content.length() > 4000) {
				mark = false;
				messages += "<li><b>文章内容</b> 最多允许输入4000个字符！</li>";
			}
		}
		request.setAttribute("messages", messages);
		return mark;
	}

	/**
	 * @功能 后台-增加文章
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
			
			/*********************指定这篇文章是哪个用户所写*********************/
			MasterBean masterBean = (MasterBean)session.getAttribute("master");
			articleBean.setMasterBean(masterBean);

			boolean boo = articleDao.operationArticle("add", articleBean);

			if (boo == true)
				mark = true;
			if (mark) {
				messages = "<li>发表文章成功！</li>";
				href = "<a href='admin/article/ArticleAdd.jsp'>[继续发表]</a>";
				forward = "/admin/success.jsp";
			} else {
				messages = "<li>发表文章失败！</li>";
				href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
				forward = "/admin/error.jsp";
			}
			request.setAttribute("messages", messages);
			request.setAttribute("href", href);

		} else {
			href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
			forward = "/admin/error.jsp";
			request.setAttribute("href", href);
		}
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}

	/**
	 * @功能 实现后台文章管理中的文章浏览功能
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
	 * @功能 从数据表中获取某类别下的所有文章
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
		
		/*****************得到当前用户的实体******************/
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
			messages += "<li>请输入 <b>类别名称！</b></li>";
		}
		if (typeInfo == null || typeInfo.equals("")) {
			mark = false;
			messages += "<li>请输入 <b>类别介绍！</b></li>";
		}
		request.setAttribute("messages", messages);
		return mark;
	}

	/**
	 * @功能 后台-增加文章类别
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
			
			/*********************指定这个类别是哪个用户所添加*********************/
			MasterBean masterBean = (MasterBean)session.getAttribute("master");
			typeBean.setMasterBean(masterBean);

			ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"applicationContext.xml");

			ArticleTypeDao articleTypeDao = (ArticleTypeDao) ctx.getBean("ArticleTypeDao");
			
			boolean mark = articleTypeDao.operationArticleType("add", typeBean);
			if (mark) {
				
				IndexAction index = new IndexAction();
				index.execute();
				
				messages += "<li>添加文章类别成功！</li>";
				href = "<a href='admin/article/ArticleTypeAdd.jsp'>[继续添加文章类别]</a>";
				forward = "/admin/success.jsp";

			} else {
				messages += "<li>添加文章类别失败！</li>";
				href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
				forward = "/admin/error.jsp";
			}
			request.setAttribute("messages", messages);
			request.setAttribute("href", href);
		} else {
			href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
			forward = "/admin/error.jsp";
			request.setAttribute("href", href);
		}
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}

	@SuppressWarnings("unchecked")
	public void selectArticleType(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		/*********************得到当前用户的实体************************/
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
	 * 查看所有用户
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
	 * @功能 后台-修改文章类别
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
				
				/*********************得到当前用户的实体************************/
				MasterBean masterBean = (MasterBean)session.getAttribute("master");
				typeBean.setMasterBean(masterBean);

				boolean mark = articleTypeDao.operationArticleType("modify", typeBean);
				if (mark) {
					messages = "<li>修改类别成功！</li>";
					href = "<a href='ArticleAction?action=typeSelect'>[继续修改其他类别]</a>";
					forward = "/admin/success.jsp";

				} else {
					messages = "<li>修改失败！</li>";
					href = "<a href='javascript:window.history.go(-1)>[返回]</a>";
					forward = "/admin/error.jsp";
				}
				request.setAttribute("messages", messages);
				request.setAttribute("href", href);
			} else {
				href = "<a href='javascript:window.history.go(-1)>[返回]</a>";
				forward = "/admin/error.jsp";
				request.setAttribute("href", href);
			}
			rd = request.getRequestDispatcher(forward);
			rd.forward(request, response);
		}
	}
	
	/**
	 * @功能 后台-修改用户权限
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
				messages = "<li>修改用户成功！</li>";
				href = "<a href='ArticleAction?pageNo="+pageNo+"&action=masterSelect'>[继续修改其他用户]</a>";
				forward = "/admin/success_admin.jsp";

			} else {
				messages = "<li>修改失败！</li>";
				href = "<a href='javascript:window.history.go(-1)>[返回]</a>";
				forward = "/admin/error_admin.jsp";
			}
			request.setAttribute("messages", messages);
			request.setAttribute("href", href);
			rd = request.getRequestDispatcher(forward);
			rd.forward(request, response);
		}
	}

	/**
	 * @功能 后台-删除文章类别
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
			messages += "<li>删除类别成功！</li>";
			href = "<a href='ArticleAction?action=typeSelect'>[继续删除其他类别]</a>";
			forward = "/admin/success.jsp";

		} else {
			messages += "<li>删除类别失败！</li>";
			href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
			forward = "/admin/error.jsp";
		}
		request.setAttribute("messages", messages);
		request.setAttribute("href", href);
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}
	
	/**
	 * @功能 后台-删除文章类别
	 */
	public void reply(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
	}

}
