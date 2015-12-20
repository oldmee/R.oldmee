<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.yxq.vo.ArticleBean"%>
<%@ page import="com.yxq.vo.ReviewBean"%>
<%@ page import="java.util.*"%>
<%
	//1、用超链接无法传递对象
	//无法接收对象参数
	//ReviewBean Review = (ReviewBean)(request.getParameter("Review_id"));
	//2、用request进行传值
	//ReviewBean reviewSingle=(ReviewBean)session.getAttribute("reviewBean");
	int i = Integer.parseInt(request.getParameter("reviewId"));
	List reviewlist=(ArrayList)session.getAttribute("reviewlist");
	ReviewBean reviewSingle=(ReviewBean)reviewlist.get(i);
	session.setAttribute("ReviewBean",reviewSingle);
	
	
	ArticleBean single=(ArticleBean)session.getAttribute("readSingle");//获取文章id，你得知道回复的是哪篇文章下的评论
%>
<html>
	<head>
		<title>聆音博客-阅读文章</title>
	</head>
	<body>
		<center>
			<table width="778" height="600" border="0" cellspacing="0"
				cellpadding="0" bgcolor="#F0EAED">
				<tr height="281">
					<td colspan="2"><jsp:include page="../view/FrontTop.jsp" /></td>
				</tr>
				<tr>
					<td width="230" valign="top"><jsp:include page="../view/FrontLeft.jsp" /></td>
					<td width="548" align="center" valign="top">
				
				<p>
					<b>发表评论</b>
				</p>
				<form action="ArticleAction" method="post">
					<input type="hidden" name="action" value="followReply">
					<input type="hidden" name="articleId" value="<%=single.getArticle_id()%>">
					<input type="hidden" name="userComment" value="<%=reviewSingle.getReAuthor()%>">
					<input type="hidden" name="reviewId" value="<%=reviewSingle.getReview_id()%>">
					<table width="95%" border="0" cellspacing="8" cellpadding="0"
						style="margin-top: 10">
						<tr>
							<td width="25%" align="center">
								他们都说：
							</td>
							<td>
							<%
								String s = "|&nbsp;"+reviewSingle.getReContent()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;from:"+reviewSingle.getReAuthor();
							%>
							
							 <input type="hidden" name="comment" value=<%=s %>>
								
								|&nbsp;<%=reviewSingle.getReContent() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;from:<%=reviewSingle.getReAuthor()%>
							</td>
						</tr>
						<tr>
							<td align="center">
								我的感慨：
							</td>
							<td>
								<textarea name="reply" rows="10" cols="50"></textarea>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<input type="submit" value="提交" style="width: 50">
								<input type="reset" value="重置" style="width: 50">
							</td>
						</tr>
					</table>
				</form>
				
				<tr height="100">
					<td colspan="2"><%@ include file="../view/FrontEnd.jsp"%></td>
				</tr>
			</table>
		</center>
	</body>
</html>