<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.yxq.vo.ArticleBean"%>
<%@ page import="com.yxq.vo.ReviewBean"%>
<%@ page import="java.util.*"%>
<%
	//1���ó������޷����ݶ���
	//�޷����ն������
	//ReviewBean Review = (ReviewBean)(request.getParameter("Review_id"));
	//2����request���д�ֵ
	//ReviewBean reviewSingle=(ReviewBean)session.getAttribute("reviewBean");
	int i = Integer.parseInt(request.getParameter("reviewId"));
	List reviewlist=(ArrayList)session.getAttribute("reviewlist");
	ReviewBean reviewSingle=(ReviewBean)reviewlist.get(i);
	session.setAttribute("ReviewBean",reviewSingle);
	
	
	ArticleBean single=(ArticleBean)session.getAttribute("readSingle");//��ȡ����id�����֪���ظ�������ƪ�����µ�����
%>
<html>
	<head>
		<title>��������-�Ķ�����</title>
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
					<b>��������</b>
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
								���Ƕ�˵��
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
								�ҵĸп���
							</td>
							<td>
								<textarea name="reply" rows="10" cols="50"></textarea>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<input type="submit" value="�ύ" style="width: 50">
								<input type="reset" value="����" style="width: 50">
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