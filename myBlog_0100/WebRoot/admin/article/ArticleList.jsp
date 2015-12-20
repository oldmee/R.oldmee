<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.yxq.vo.ArticleBean"%>
<%@ page import="com.yxq.vo.ArticleTypeBean"%>

<%
final int PAGE_SIZE = 6;
int  pageNo = Integer.parseInt(request.getParameter("pageNo"));

if(pageNo <= 0) pageNo = 0;
ArrayList articlelist = articlelist = (ArrayList) request.getAttribute("articleList");
int type_id = Integer.parseInt(request.getAttribute("type_id").toString());
%>

<html>
	<head>
		<title>博客后台首页</title>
	</head>
	<body>
		<center>
			<table width="778" border="0" cellspacing="0" cellpadding="0"
				bgcolor="#FFFFFF" style="word-break: break-all">
				<tr>
					<td colspan="2"><%@ include file="../view/AdminTop.jsp"%></td>
				</tr>
				<tr>
					<td><jsp:include page="/admin/view/AdminLeft.jsp" /></td>
					<td align="center" valign="top">
						<table width="560" border="0" cellspacing="0" cellpadding="0"
							rules="none">
							<tr height="60">
								<td colspan="4">
									【浏览文章】
								</td>
							</tr>
							<tr>
								<td align="right" colspan="4">
									<form
										action="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/"%>ArticleAction?pageNo=0"
										method="post">
										<input type="hidden" name="action" value="adminSelectList">
										文章类别：
										<select name="typeId">
											<option value=""></option>
											<%
												ArrayList typelist = (ArrayList) session
														.getAttribute("artTypeList");
												if (typelist == null || typelist.size() == 0) {
											%>
											<option value="">
												没有类别可显示
											</option>
											<%
												} else {
													for (int i = 0; i < typelist.size(); i++) {
														ArticleTypeBean single = (ArticleTypeBean) typelist.get(i);
														ArticleTypeBean first = (ArticleTypeBean) typelist.get(0);
											%>
											<option value="<%=single.getArticleType_id()%>" 
											<%if(type_id-first.getArticleType_id()==i){%>
												selected="selected"
											<% }%>
											>
											<%=single.getTypeName()%>
											</option>
											
											<%
												}
												}
											%>
										</select>
										<input type="submit" value="显示" class="btn_bg">
									</form>
								</td>
							</tr>
							<tr height="30" bgcolor="#F5F4F4">
								<td width="55%" style="text-indent: 20">
									文章标题
								</td>
								<td align="center" width="30%">
									发表时间
								</td>
								<td align="center" width="15%" colspan="2">
									操作
								</td>
							</tr>
							<%
								if (articlelist == null || articlelist.size() == 0) {
							%>
							<tr height="80">
								<td colspan="4" align="center">
									<li>
										没有文章可显示！
									</li>
								</td>
							</tr>
							<%
								} else {
									if(pageNo<0)
										pageNo = 0;
									int max = pageNo+PAGE_SIZE;
									if(max>articlelist.size()) {
										max = articlelist.size();
										pageNo = max-PAGE_SIZE;
									}
									if(pageNo<0)
										pageNo = 0;
									for (int i=pageNo;i<max;i++) {
										ArticleBean single = (ArticleBean) articlelist.get(i);
							%>
							<tr height="35">
								<td style="text-indent: 20">
									<a
										href="ArticleAction?action=adminSelectSingle&id=<%=single.getArticle_id()%>"
										class="word_purple "><%=single.getTitle()%></a>
								</td>
								<td align="center"><%=single.getSdTime()%></td>
								<td align="center">
									<a
										href="ArticleAction?action=modify&id=<%=single.getArticle_id()%>&typeId=<%=single.getArticleType().getArticleType_id()%>"
										class="word_purple ">√修改</a>
								</td>
								<td align="center">
									<a
										href="ArticleAction?action=delete&id=<%=single.getArticle_id()%>&typeId=<%=single.getArticleType().getArticleType_id()%>"
										class="word_purple ">×删除</a>
								</td>
							</tr>
							<%
								}
							%>
							<tr height="40"><td></td></tr>
							<tr>
								<td align="right">
								<a href="ArticleAction?action=adminSelectList&typeId=<%=type_id %>&pageNo=0">第一页</a>&nbsp;
								<a href="ArticleAction?action=adminSelectList&typeId=<%=type_id %>&pageNo=<%=pageNo-PAGE_SIZE %>">上一页</a>&nbsp;
								</td>
								
								<td align="left">&nbsp;
								<a href="ArticleAction?action=adminSelectList&typeId=<%=type_id %>&pageNo=<%=pageNo+PAGE_SIZE %>">下一页</a>&nbsp;
								<a href="ArticleAction?action=adminSelectList&typeId=<%=type_id %>&pageNo=<%=articlelist.size()-PAGE_SIZE %>">最末页</a>&nbsp;
								</td>
							</tr>
							<%
								}
							%>

						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2"><%@ include file="../view/AdminEnd.jsp"%></td>
				</tr>
			</table>
		</center>
	</body>
</html>