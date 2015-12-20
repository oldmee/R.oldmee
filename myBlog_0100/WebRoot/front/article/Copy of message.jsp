<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@page import="com.yxq.vo.MasterBean"%>
<%@page import="com.yxq.vo.AuthorReviewBean"%>
<%@page import="java.util.*"%>

<%
	String name = session.getAttribute("MyName").toString(); 
	MasterBean mb = (MasterBean)session.getAttribute("111");
	Set<AuthorReviewBean> st = mb.getAuthorReview();
	
	
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
					<input type="hidden" name="articleId" value="">
					<input type="hidden" name="reviewId" value="">
					<table width="95%" border="0" cellspacing="8" cellpadding="0"
						style="margin-top: 10">
						<tr>
							<td width="25%" align="center">
								他们都说：
							</td>
							<td>
							
							 <input type="hidden" name="comment" value="xxx">
								
							</td>
						</tr>
						<tr>
							<td align="center">
								我的感慨：
							</td>
							<td>
								<hr width="95%" style="margin-top: 5" size="1">
						<% 
               				Iterator<AuthorReviewBean> it = st.iterator();
               				int sum = 0;
            	   				while(it.hasNext())
            	   					sum += 1;
	               			if(it!=null||it.hasNext()){
    	           				out.print("<p><li>该文章目前没有任何评论！</li>"+st.size());
        	       			}
            	   			else{
            	   				int sum1 = 0;
            	   				while(it.hasNext())
            	   					sum1 += 1;
                	       		int num=sum1; 
                    	   		out.print("<table border='0' width='100%' rules='all' cellpadding='0' cellspacing='0'> ");
                      			out.print("<tr height='60'><td align='center'>【作者评论 共 "+""+" 条】</td></tr>");     
                      			                       
	                           	while(it.hasNext()){
    	                       		AuthorReviewBean authorReviewBean=(AuthorReviewBean)it.next();   
				         %>
									
								<tr height="50">
									<td style="text-indent: 20">
										▲
										</b>
									</td>
								</tr>
								<tr align="right" height="20">
									<td width="40%"><%=authorReviewBean.getReview().getArticle().getTitle()%>&nbsp;&nbsp;
									</td>
								</tr>
								<tr height="60">
									<td style="text-indent: 20" colspan="2" valign="top">
										&nbsp;&nbsp;&nbsp;&nbsp;<%=authorReviewBean.getReview().getReContent()%>
									</td>
								</tr><tr height="60">
									<td style="text-indent: 20" colspan="2" valign="top">
										&nbsp;&nbsp;&nbsp;&nbsp;<%= authorReviewBean.getAuthorReplyContent()%>
									</td>
								</tr>
								<%} %>
								<%
                        	   	}
                           		out.print("</table>");
                       	
                	%>
				
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