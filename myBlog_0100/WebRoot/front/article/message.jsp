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
	Iterator<AuthorReviewBean> it = st.iterator();
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
					<b>�ҵ���Ϣ</b>
				</p>
				<form action="ArticleAction" method="post">
					<input type="hidden" name="action" value="followReply">
					<input type="hidden" name="articleId" value="">
					<input type="hidden" name="reviewId" value="">
					<table width="95%" border="0" cellspacing="8" cellpadding="0"
						style="margin-top: 10">
						<tr>
							<td align="center">
							</td>
							<td>
								<hr width="95%" style="margin-top: 5" size="1">
						<% 
               				
	               			if(st==null || st.isEmpty()){
    	           				out.print("<p><li>������Ŀǰû���κ����ۣ�</li>"+st.size());
        	       			}else{
            	   				
                    	   		out.print("<table border='0' width='100%' rules='all' cellpadding='0' cellspacing='0'> ");
                      			out.print("<tr height='60'><td align='center'>���������� �� "+st.size()+" ����</td></tr>");     
                      			int i=0;
	                           	while(i<st.size()){
    	                       		AuthorReviewBean authorReviewBean=(AuthorReviewBean)it.next();   
				         %>
									
								<tr height="50">
									<td style="text-indent: 20">
										�����±��⣺<%=authorReviewBean.getReview().getArticle().getTitle()%>
									</td>
								</tr>
								<tr align="right" height="20">
									<td width="40%">�����۵����ݣ�<%=authorReviewBean.getReview().getReContent()%>&nbsp;&nbsp;
									</td>
								</tr>
								<tr height="60">
									<td style="text-indent: 20" colspan="2" valign="top">
										&nbsp;<%= authorReviewBean.getAuthorReplyContent()%>
									</td>
								<%
									i++;
										} 
								
								%>
								<%
                        	   	}
                           		out.print("</table>");
                       	
                	%>
				
							</td>
						</tr>
						
						<tr>
							<td></td>
							<td>
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