<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.yxq.vo.ArticleBean"%>
<%@ page import="com.yxq.vo.ReviewBean"%>
<%@ page import="com.yxq.vo.MasterBean"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.collections.IteratorUtils"%>
<%@ page import="com.yxq.dao.ReviewDao"%>

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
					<td width="230" valign="top"><jsp:include
							page="../view/FrontLeft.jsp" /></td>
					<td width="548" align="center" valign="top">
						<%
                    	ArticleBean single=(ArticleBean)session.getAttribute("single");
                    	if(single==null)
                    		out.println("�Ķ�����ʧ�ܣ�");
                    	else{
                    %>
						<table border="0" width="100%" rules="all" cellpadding="0"
							cellspacing="0">
							<tr height="55" align="right">
								<td background="images/main_B.jpg">
									����ʱ�䣺<%=single.getSdTime() %>&nbsp;&nbsp;&nbsp;&nbsp; ���ۣ�<%=single.getReviews().size() %>
									��&nbsp;&nbsp;&nbsp;&nbsp; �Ķ���<%=single.getCount() %>
									��&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							<tr height="15">
								<td></td>
							</tr>
							<tr>
								<td align="center">
									<b><font style="font-size: 25px"><%=single.getTitle()%></font>
									</b>
								</td>
							</tr>
							<tr height="40">
								<td align="right"><%=single.getCreateFrom()%>��<%=single.getInfo() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							<tr height="15">
								<td></td>
							</tr>
							<tr height="200">
								<td valign="top" style="padding-left: 20; padding-right: 20"><%=single.getContent() %></td>
							</tr>
						</table>
						<hr width="95%" style="margin-top: 5" size="1">
						<% 
               				//List reviewlist=(ArrayList)session.getAttribute("reviewlist");
               				
               				//Set<String> set = new HashSet<String>();  
							//Iterator<String> it = set.iterator();  
							//while (it.hasNext()) {  
			  				//String str = it.next();  
			 				//	System.out.println(str);  
							//}  
               				
               				Set<ReviewBean> set = single.getReviews();
               				Iterator<ReviewBean> it = set.iterator();
               				List<ReviewBean> reviewlist=IteratorUtils.toList(it);
               				
               				
	               			if(reviewlist==null||reviewlist.size()==0){
    	           				out.print("<p><li>������Ŀǰû���κ����ۣ�</li>");
        	       			}
            	   			else{
                	       		int num=reviewlist.size(); 
                    	   		out.print("<table border='0' width='100%' rules='all' cellpadding='0' cellspacing='0'> ");
                      			out.print("<tr height='60'><td align='center'>���������� �� "+num+" ����</td></tr>");                            
               					int i=reviewlist.size()-1;
               					
	                           	while(i>=0){
    	                       		ReviewBean reviewSingle=(ReviewBean)reviewlist.get(i);  
    	                       		
    	                       	if(reviewSingle != null)
                    %>
					
				<tr height="50">
					<td style="text-indent: 20">
						��
						<b><%=reviewSingle.getReAuthor() %></b>
					</td>
				</tr>
				<tr align="right" height="20">
					<td width="40%"><%=reviewSingle.getReSdTime()%>&nbsp;&nbsp;
					</td>
				</tr>
				<tr height="60">
					<td style="text-indent: 20" colspan="2" valign="top">
						&nbsp;&nbsp;&nbsp;&nbsp;<%=reviewSingle.getReContent()%></td>
				</tr>
				<tr height="1">
					<td background="images/line.jpg" colspan="2"></td>
				</tr>
				<%
                    	       		i--;
                        	   	}
                           		out.print("</table>");
            	   			}
                       	}
                	%>
                	<%
                	if(single != null) {
                	%>
				<p>
					<b>��������</b>
				</p>
				<form action="ArticleAction" method="post">
					<input type="hidden" name="action" value="followAdd">
					<input type="hidden" name="articleId" value="<%=single.getArticle_id()%>">
					<table width="95%" border="0" cellspacing="8" cellpadding="0"
						style="margin-top: 10">
						<tr>
							<td width="25%" align="center">
								�� �� �ߣ�
							</td>
							<%MasterBean masterBean = (MasterBean)session.getAttribute("master");
							  String str_name = masterBean.getMaster_name();							
							 %>
							<td>
								<%=str_name%>
							</td>
						</tr>
						<tr>
							<td align="center">
								�������ݣ�
							</td>
							<td>
								<textarea name="reContent" rows="10" cols="50"></textarea>
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
				<%
                	}
				%>
				<tr height="100">
					<td colspan="2"><%@ include file="../view/FrontEnd.jsp"%></td>
				</tr>
			</table>
		</center>
	</body>
</html>