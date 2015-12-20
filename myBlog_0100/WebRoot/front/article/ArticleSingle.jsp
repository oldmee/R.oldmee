<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.yxq.vo.ArticleBean"%>
<%@ page import="com.yxq.vo.ReviewBean"%>
<%@ page import="java.util.*"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>��������-�Ķ�����</title>
		<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css">
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
                    	ArticleBean single=(ArticleBean)session.getAttribute("readSingle");
                    	String MyName = session.getAttribute("MyName").toString();
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
						<a href="javascript:window.history.go(-1)">����</a>
						<hr width="95%" style="margin-top: 5" size="1">
						<% 
               				List reviewlist=(ArrayList)session.getAttribute("reviewlist");
	               			if(reviewlist==null||reviewlist.size()==0){
    	           				out.print("<p><li>������Ŀǰû���κ����ۣ�</li>");
        	       			}
            	   			else{
                	       		int num=reviewlist.size(); 
                    	   		out.print("<table border='0' width='100%' rules='all' cellpadding='0' cellspacing='0'> ");
                      			out.print("<tr height='60'><td align='center'>���������� �� "+num+" ����</td></tr>");                            
               					int i=0;
               					//session.setAttribute("reviewlist",reviewlist);//������review�����ݵ�replyҳ��
	                           	while(i<reviewlist.size()){
    	                       		ReviewBean reviewSingle=(ReviewBean)reviewlist.get(i);            
                    %>
					
				<tr height="50">
					<td style="text-indent: 20">
						��
						<b><%
						String AuthorName = "";
						if(reviewSingle.getReAuthor().equals("kevin_lee")){
							AuthorName = "����";
						} else {
							AuthorName = "�û���"+reviewSingle.getReAuthor();
						}
						%>
						<%=AuthorName %>
						</b>
					</td>
				</tr>
				<tr align="right" height="20">
					<td width="40%"><%=reviewSingle.getReSdTime()%>&nbsp;&nbsp;
					</td>
				</tr>
				<!-- Ϊ�������ܻظ����ۣ����������ܿ������� -->
				<tr height="60">
					<td style="text-indent: 20" colspan="2" valign="top">
						&nbsp;&nbsp;&nbsp;&nbsp;<%=reviewSingle.getReContent()%>
					</td>
				</tr>
				<%
				String name = (session.getAttribute("MyName")).toString();
				if(!name.equals("kevin_lee")){ %>
				<%}else { %>
				<tr height="5">
				<!-- ������request.setAttribute("aa",fb)����; -->
				<%
					//������������Ҫ���ò��ܴ�ֵ�ɹ�������ҳ������ת������ת�ˣ����Է����в�ͨ
					//request.setAttribute("reviewBean",reviewSingle);
					//request.getRequestDispatcher("ArticleSingle.jsp").forward(request, response);
						
				 %>
					<td colspan="2" align="right"><a href="<%=basePath %>front/article/reply.jsp?reviewId=<%=i%>" class="topA">�ظ�</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<%} %>
				<%
                    	       		i++;
                        	   	}
                           		out.print("</table>");
            	   			}
                       	}
                	%>
				
				<p>
					<b>��������</b>
				</p>
				<form action="ArticleAction" method="post">
					<input type="hidden" name="action" value="followAdd">
					<input type="hidden" name="articleId" value="<%=single.getArticle_id() %>">
					<table width="95%" border="0" cellspacing="8" cellpadding="0"
						style="margin-top: 10">
						<tr>
							<td width="25%" align="center">
								�� �� �ߣ�
							</td>
							<td>
								<%=MyName %>
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
				
				<tr height="100">
					<td colspan="2"><%@ include file="../view/FrontEnd.jsp"%></td>
				</tr>
			</table>
		</center>
	</body>
</html>